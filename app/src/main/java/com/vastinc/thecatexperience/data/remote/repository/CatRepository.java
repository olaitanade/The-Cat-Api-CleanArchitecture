package com.vastinc.thecatexperience.data.remote.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.vastinc.thecatexperience.data.NetworkBoundResource;
import com.vastinc.thecatexperience.data.ResponseResource;
import com.vastinc.thecatexperience.data.local.model.Breed;
import com.vastinc.thecatexperience.data.remote.ServiceGenerator;
import com.vastinc.thecatexperience.util.AbsentLiveData;

import java.util.List;

import retrofit2.Call;

public class CatRepository {
    private static final String TAG = "CatRepository";

    private static CatRepository instance;

    public static synchronized CatRepository getInstance(Application application){
        if(instance == null){
            instance = new CatRepository(application);
        }
        return instance;
    }

    private CatRepository(Application application){
    }

    public LiveData<ResponseResource<List<Breed>>> getAllBreeds() {
        return new NetworkBoundResource<List<Breed>, List<Breed>>() {
            private List<Breed> response;

            @Override
            protected void saveCallResult(@NonNull List<Breed> item) {
                response = item;
            }

            @NonNull
            @Override
            protected LiveData<List<Breed>> loadFromDb() {
                if (response == null) {
                    return AbsentLiveData.create();
                }else {
                    return new LiveData<List<Breed>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            setValue(response);
                        }
                    };
                }
            }

            @NonNull
            @Override
            protected Call<List<Breed>> createCall() {
                return ServiceGenerator.getCatApi().getBreeds();
            }
        }.getAsLiveData();
    }
}

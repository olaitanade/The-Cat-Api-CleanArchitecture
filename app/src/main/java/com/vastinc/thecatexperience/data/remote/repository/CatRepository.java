package com.vastinc.thecatexperience.data.remote.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.vastinc.thecatexperience.data.NetworkBoundResource;
import com.vastinc.thecatexperience.data.ResponseResource;
import com.vastinc.thecatexperience.data.remote.ServiceGenerator;
import com.vastinc.thecatexperience.data.remote.model.AllBreedsResponse;
import com.vastinc.thecatexperience.util.AbsentLiveData;

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

    public LiveData<ResponseResource<AllBreedsResponse>> getAllBreeds() {
        return new NetworkBoundResource<AllBreedsResponse, AllBreedsResponse>() {
            private AllBreedsResponse response;

            @Override
            protected void saveCallResult(@NonNull AllBreedsResponse item) {
                response = item;
            }

            @NonNull
            @Override
            protected LiveData<AllBreedsResponse> loadFromDb() {
                if (response == null) {
                    return AbsentLiveData.create();
                }else {
                    return new LiveData<AllBreedsResponse>() {
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
            protected Call<AllBreedsResponse> createCall() {
                return ServiceGenerator.getCatApi().getBreeds();
            }
        }.getAsLiveData();
    }
}

package com.vastinc.thecatexperience.ui.fragment.breeds;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vastinc.thecatexperience.data.ResponseResource;
import com.vastinc.thecatexperience.data.local.model.Breed;
import com.vastinc.thecatexperience.data.remote.repository.CatRepository;

import java.util.List;

public class BreedsViewModel extends AndroidViewModel {
    private CatRepository catRepository;
    private LiveData<ResponseResource<List<Breed>>> breeds;

    public BreedsViewModel(@NonNull Application application) {
        super(application);
        catRepository = CatRepository.getInstance(application);
        breeds = catRepository.getAllBreeds();
    }

    public LiveData<ResponseResource<List<Breed>>> getBreeds(){
        return breeds;
    }
}
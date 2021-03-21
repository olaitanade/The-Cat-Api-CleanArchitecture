package com.vastinc.thecatexperience.data.remote.api;

import com.vastinc.thecatexperience.config.Production;
import com.vastinc.thecatexperience.data.remote.model.AllBreedsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatApi {
    @GET(Production.BREEDS)
    Call<AllBreedsResponse> getBreeds();
}

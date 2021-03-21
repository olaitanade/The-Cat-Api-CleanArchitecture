package com.vastinc.thecatexperience.data.remote.api;

import com.vastinc.thecatexperience.config.Production;
import com.vastinc.thecatexperience.data.local.model.Breed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CatApi {
    @Headers({"x-api-key: a2b97797-8cc6-40f6-8103-a023d7ca623c"})
    @GET(Production.BREEDS)
    Call<List<Breed>> getBreeds();
}

package com.vastinc.thecatexperience.data.remote.model;

import com.vastinc.thecatexperience.data.local.model.Breed;

import java.util.List;

public class AllBreedsResponse {
    private List<Breed> breeds;

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }
}

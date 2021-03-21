package com.vastinc.thecatexperience.ui.fragment.breeds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.vastinc.thecatexperience.R;
import com.vastinc.thecatexperience.data.ResponseResource;
import com.vastinc.thecatexperience.data.local.model.Breed;

import java.util.List;

public class BreedsFragment extends Fragment {
    private static final String TAG = "BreedsFragment";
    private BreedsViewModel breedsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        breedsViewModel =
                new ViewModelProvider(requireActivity()).get(BreedsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_breeds, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        breedsViewModel.getBreeds().observe(getViewLifecycleOwner(), listResponseResource -> {
            switch (listResponseResource.status){
                case LOADING:
                    Log.d(TAG, "onStart: getBreeds => Loading");
                    break;
                case SUCCESS:
                    Log.d(TAG, "onStart: getBreeds => size = "+listResponseResource.data.size());
                    break;
                case ERROR:
                    Log.d(TAG, "onStart: getBreeds => "+listResponseResource.message);
                    break;
            }
        });
    }
}
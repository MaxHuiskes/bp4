package com.example.porgamring.ui.componentreg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.databinding.FragmentComponentRegBinding;

public class ComponentRegFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private FragmentComponentRegBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ComponentRegViewModel galleryViewModel =
                new ViewModelProvider(this).get(ComponentRegViewModel.class);

        binding = FragmentComponentRegBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
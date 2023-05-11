package com.example.porgamring.ui.aansturing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.databinding.FragmentAansturingBinding;

public class AansturingFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private FragmentAansturingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AansturingViewModel slideshowViewModel =
                new ViewModelProvider(this).get(AansturingViewModel.class);

        binding = FragmentAansturingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
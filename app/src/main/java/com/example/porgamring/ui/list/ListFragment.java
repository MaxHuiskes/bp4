package com.example.porgamring.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.APIHandler;
import com.example.porgamring.databinding.FragmentPersonenBinding;

import java.util.ArrayList;
import java.util.List;

public class ListFragment<ListArray> extends Fragment {

    private FragmentPersonenBinding binding;
    private Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListViewModel homeViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentPersonenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn = binding.btn;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), PersoonActivity.class);
                startActivity(k);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
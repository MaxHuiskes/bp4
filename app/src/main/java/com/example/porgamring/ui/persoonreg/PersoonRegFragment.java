package com.example.porgamring.ui.persoonreg;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.porgamring.DatePicker;
import com.example.porgamring.MainActivity;
import com.example.porgamring.databinding.FragmentPersoonRegBinding;

import java.util.Calendar;

public class PersoonRegFragment<ListArray> extends Fragment {

    private FragmentPersoonRegBinding binding;
    private FragmentPersoonRegBinding FragmentProductListBinding;
    private Button dateButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersoonRegViewModel homeViewModel =
                new ViewModelProvider(this).get(PersoonRegViewModel.class);

        binding = FragmentPersoonRegBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateButton = binding.datePickerButton;
        DatePicker datePicker = new DatePicker(dateButton,getActivity());
        datePicker.initDatePicker(false);

        dateButton.setText(datePicker.getTodaysDate());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.datePickerDialog.show();
            }
        });



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

package com.geek.android2_1_taskapp.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.models.Task;

public class TaskFragment extends Fragment {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.btnSave).setOnClickListener(v ->{
            save();
        });
    }

    private void save() {
        String text = editText.getText().toString();
        if (text.isEmpty())return;
        long createAt = System.currentTimeMillis();
        Task task = new Task(text, createAt);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        getParentFragmentManager().setFragmentResult("task", bundle);
        close();

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}
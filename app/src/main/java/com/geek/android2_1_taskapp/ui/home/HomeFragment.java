package com.geek.android2_1_taskapp.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.android2_1_taskapp.App;
import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.interfaces.OnItemClickListener;
import com.geek.android2_1_taskapp.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener {

    private RecyclerView rv;
    private TaskAdapter adapter;
    private AlertDialog.Builder dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(requireContext());
        dialog = new AlertDialog.Builder(requireContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.homeRecycler);

        roomInit();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v ->{
            openTaskFragment();
        });
        getParentFragmentManager().setFragmentResultListener("task", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Task task = (Task) result.getSerializable("task");
                adapter.addItem(task);

            }
        });

    }

    private void roomInit() {
        App.getAppDatabase().taskDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                initList(tasks);
            }
        });
    }

    private void initList(List<Task> list) {
        rv.setAdapter(adapter);
        adapter.addItems(list);
        adapter.setListener(this);
    }

    private void openTaskFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.taskFragment);
    }

    @Override
    public void onClick(Task position) {

    }

    @Override
    public void onLongClick(Task position) {
        dialog.show().dismiss();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort:
                sortList();
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void sortList() {
        App.getAppDatabase().taskDao().getAllByText().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.sortList((ArrayList<Task>) tasks);
            }
        });
    }
}
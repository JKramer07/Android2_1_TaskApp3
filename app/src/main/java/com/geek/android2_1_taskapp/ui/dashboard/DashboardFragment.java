package com.geek.android2_1_taskapp.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.interfaces.OnItemClickListener;
import com.geek.android2_1_taskapp.models.Task;
import com.geek.android2_1_taskapp.ui.home.TaskAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.List;

public class DashboardFragment extends Fragment implements OnItemClickListener {
    private RecyclerView rv;
    private TaskAdapter adapter;
    private ProgressBar progressBar;
    private Task task;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.recyclerDash);
        progressBar = view.findViewById(R.id.progressBar);
        initList();
//        getData();
        getDataLive();
        deleteDataLive();
    }

    private void deleteDataLive() {

    }

    private void deleteItem(int position) {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .document()
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        adapter.removeItem(position);
                    }
                });
    }

    private void getDataLive() {
        FirebaseFirestore.getInstance().collection("tasks")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null){
                            List<Task> list = value.toObjects(Task.class);
                            adapter.addItems(list);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initList() {
        adapter = new TaskAdapter();
        rv.setAdapter(adapter);

    }

    private void getData(){
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Task> list = queryDocumentSnapshots.toObjects(Task.class);
                        adapter.addItems(list);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onLongClick(int position) {
        task = adapter.getItem(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setTitle("Warning!")
                .setMessage("Delete?")
                .setPositiveButton("Yes", (dialog1, which) -> {
                    deleteItem(position);
                })
                .setNegativeButton("No", (dialog1, which) -> {
                    dialog1.cancel();
                }).show().dismiss();
    }
}
package com.geek.android2_1_taskapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.android2_1_taskapp.App;
import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.interfaces.OnItemClickListener;
import com.geek.android2_1_taskapp.models.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list = new ArrayList<>();
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        if(position %2 == 1){
            holder.itemView.setBackgroundResource(R.color.light_grey);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public Task getItem(int position){
        return list.get(position);
    }

    public void setItem(int pos, Task task){
        list.set(pos, task);
        notifyItemChanged(pos);
    }

    public void addItems(List<Task> tasks){
        App.getAppDatabase().taskDao().getAll();
        list.clear();
        list.addAll(tasks);
        notifyDataSetChanged();
    }

    public void addItem(Task task) {
        list.add(0, task);
        notifyItemInserted(list.indexOf(task));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textTitle);

            itemView.setOnLongClickListener(v -> {
                listener.onLongClick(getAdapterPosition());
                return true;
            });
            itemView.setOnClickListener(v->{
                listener.onClick(getAdapterPosition());
            });
        }

        public void onBind(Task task) {
            text.setText(task.getText());
        }
    }
}

package com.geek.android2_1_taskapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.android2_1_taskapp.App;
import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.interfaces.OnItemClickListener;
import com.geek.android2_1_taskapp.models.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> list = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(Context context) {
        this.context = context;
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

        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(list.get(position));
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("WARNING")
                    .setMessage("Do you want to delete?")
                    .setPositiveButton("Yes", (dialog1, which) -> {
                        App.getAppDatabase().taskDao().delete(list.get(position));
                        list.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Item is Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog1, which) -> {
                        dialog1.cancel();
                    }).show();
            return true;
        });

        holder.itemView.setOnClickListener(v->{
            listener.onClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void addItems(List<Task> tasks){
        list.addAll(tasks);
        notifyDataSetChanged();
    }

    public void addItem(Task task) {
        list.add(0, task);
        notifyItemInserted(list.indexOf(task));
    }

    public void sortList(ArrayList<Task> tasks) {
        list.addAll(tasks);
        notifyItemChanged(list.indexOf(tasks));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textTitle);
        }

        public void onBind(Task task) {
            text.setText(task.getText());
        }
    }
}

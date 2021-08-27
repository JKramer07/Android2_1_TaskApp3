package com.geek.android2_1_taskapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.interfaces.OnItemClickListener;
import com.geek.android2_1_taskapp.models.Task;

import java.util.ArrayList;

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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(position));
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("WARNING")
                        .setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", (dialog1, which) -> {
                            list.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Item is Deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog1, which) -> {
                            dialog1.cancel();
                        }).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Task task) {
        list.add(0, task);
        notifyItemInserted(list.indexOf(task));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private TextView createAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textTitle);
//            createAt = itemView.findViewById(R.id.textDate);
        }

        public void onBind(Task task) {
            text.setText(task.getText());
        }
    }
}

package com.geek.android2_1_taskapp.ui.onboard;

import android.animation.Animator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.geek.android2_1_taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private Finish finish;

    public void setOpenHome(Finish finish) {
        this.finish = finish;
    }

    private String[] titles = new String[]{"Hello","How are you?","Where are you?"};
    private int[] descriptions = new int[]{R.string.desc_first, R.string.desc_second, R.string.desc_third};
    private int[] images = new int[]{R.raw.onboardingaccount1, R.raw.onboardingalert, R.raw.onboardingcar};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, desc;
        private Button buttonStart;
        private LottieAnimationView animationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textPagerTitle);
            desc = itemView.findViewById(R.id.textPagerDesc);
            buttonStart = itemView.findViewById(R.id.btnStart);
            animationView = itemView.findViewById(R.id.lotti);
            buttonStart.setOnClickListener(v->{
                finish.closeOnBoard();
            });
        }

        public void bind(int position) {
            title.setText(titles[position]);
            desc.setText(descriptions[position]);
            animationView.setAnimation(images[position]);

            if (position == 2){
                buttonStart.setVisibility(View.VISIBLE);
            }else {
                buttonStart.setVisibility(View.GONE);
            }
        }
    }

    interface Finish{
        void closeOnBoard();
    }
}

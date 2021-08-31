package com.geek.android2_1_taskapp.ui.onboard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.geek.android2_1_taskapp.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class BoardFragment extends Fragment implements BoardAdapter.Finish {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DotsIndicator indicator = view.findViewById(R.id.dotsIndicator);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        BoardAdapter adapter = new BoardAdapter();
        viewPager2.setAdapter(adapter);
        indicator.setViewPager2(viewPager2);
        adapter.setOpenHome(this);

        //метод, при нажатии "назад" = выход из приложения
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });


    }

    @Override
    public void closeOnBoard() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}
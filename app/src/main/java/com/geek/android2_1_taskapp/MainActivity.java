package com.geek.android2_1_taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> fragments = new ArrayList<>();
    private NavController navController;
    private NavDestination destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Prefs prefs = new Prefs(this);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            navController.navigate(R.id.authFragment);
        }
        if (!prefs.isBoardShown())
            navController.navigate(R.id.boardFragment);


        fragments.add(R.id.navigation_home);
        fragments.add(R.id.navigation_dashboard);
        fragments.add(R.id.navigation_notifications);
        fragments.add(R.id.navigation_profile);



        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if (fragments.contains(destination.getId())){
                    navView.setVisibility(View.VISIBLE);
                } else {
                    navView.setVisibility(View.GONE);
                }

                if (destination.getId() == R.id.boardFragment){
                    getSupportActionBar().hide();
                }else{
                    getSupportActionBar().show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    //метод, при нажатии "назад" = выход из приложения
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (destination.getId() == R.id.boardFragment){
//            finish();
//        }
//    }
}
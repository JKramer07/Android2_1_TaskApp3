package com.geek.android2_1_taskapp.ui.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.geek.android2_1_taskapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.content.ContentValues.TAG;

public class AuthFragment extends Fragment {

    private GoogleSignInClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGoogle();
        Button btnGoogle = view.findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(v->{
            googleSignIn();
        });
    }

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireActivity(), gso);
    }

    private void googleSignIn() {
        Intent intent = client.getSignInIntent();
        resultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                    try {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        signIn(account);
                    } catch (ApiException e){
                        e.printStackTrace();
                    }
            });

    private void signIn(GoogleSignInAccount account) {
        //progressbar visible
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressbar gone
                if (task.isSuccessful()){
                    close();
                } else {
                    Toast.makeText(requireContext(), "Auth error"+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }


    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}
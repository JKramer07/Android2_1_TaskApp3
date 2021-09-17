package com.geek.android2_1_taskapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geek.android2_1_taskapp.App;
import com.geek.android2_1_taskapp.Prefs;
import com.geek.android2_1_taskapp.R;
import com.geek.android2_1_taskapp.models.Task;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView image;
    private EditText username;
    private Prefs prefs;
    private Button logOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        prefs = new Prefs(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = view.findViewById(R.id.imgOpen);
        username = view.findViewById(R.id.etUsername);
        logOut = view.findViewById(R.id.btnLogout);
        signOut();

        String text = username.getText().toString();
        Task utask = new Task(text);
        App.getAppDatabase().taskDao().insert(utask);
        saveNameToFireStore(utask);


//        image.setImageURI(prefs.getProfileImage());
//        username.setText(prefs.getUsername());

        //wrong
//        username.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                prefs.putString("text", s.toString());
//            }
//        });

        image.setOnClickListener(v->{
            openImage();
        });


    }

    private void saveNameToFireStore(Task utask) {
        FirebaseFirestore.getInstance()
                .collection("profileUsers")
                .add(utask)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                        if (task.isComplete()){
                            Toast.makeText(requireContext(), "The name was saved", Toast.LENGTH_SHORT).show();
                        }else{
                            task.getException().printStackTrace();
                            Toast.makeText(requireContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        //short version
//                        image.setImageURI(result.getData().getData());

                        Intent data = result.getData();
                        Uri selectImage = data.getData();
                        image.setImageURI(selectImage);
//                        prefs.setProfileImage(selectImage);
                    }
                }
            }
    );

    private void openImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityLauncher.launch(intent);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        prefs.saveName(username.getText().toString());
//    }

        @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Prefs prefs = new Prefs(requireContext());
        switch (item.getItemId()){
            case R.id.history:
//                prefs.putClearText();
                App.getAppDatabase().taskDao().deleteAll();
                username.setText("");
                image.setImageURI(null);
                App.getAppDatabase().taskDao().updateAll();

            default: return super.onOptionsItemSelected(item);
        }
    }

    public void signOut(){
        logOut.setOnClickListener(v->{
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
            googleSignInClient.signOut();
            FirebaseAuth.getInstance().signOut();
            openAuth();
        });
    }

    private void openAuth() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.authFragment);
    }
}
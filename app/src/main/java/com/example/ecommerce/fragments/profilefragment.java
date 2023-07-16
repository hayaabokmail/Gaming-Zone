package com.example.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Editprofile;
import com.example.ecommerce.Login;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */



    // TODO: Rename and change types and number of parameters
    public class profilefragment extends Fragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




       ImageView signout;
        private FirebaseAuth mAuth;
        private FirebaseFirestore firebaseFirestore;

        private TextView titleUsername, profileUserName, profileEmail, profileRole;
        private Button editButton;



        public profilefragment() {
            // Required empty public constructor
        }

        public profilefragment newInstance(String param1, String param2) {
            profilefragment fragment = new profilefragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            firebaseFirestore = FirebaseFirestore.getInstance();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile, container, false);
            fetchUserData();

            titleUsername = view.findViewById(R.id.titleUsername);
            profileUserName = view.findViewById(R.id.profileUserName);
            profileEmail = view.findViewById(R.id.profileEmail);
            profileRole = view.findViewById(R.id.profileRole);
            editButton=view.findViewById(R.id.editButton);
            signout =view.findViewById(R.id.signout);
            mAuth = FirebaseAuth.getInstance();
            editButton.setOnClickListener(v -> {
                sendUserDataToEditProfile();

            });
            signout.setOnClickListener(v -> {
                mAuth.signOut();
                Toast.makeText(getContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), Login.class));

            });
            return view;
        }
    private void fetchUserData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference userRef = firebaseFirestore.collection("users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String role = document.getString("role");
                        String email = document.getString("email");
                        String username = document.getString("username");

                        // Display the retrieved data in TextViews
                        showAllUserData(role, email, username);
                    } else {
                        Toast.makeText(getContext(), "User document not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to get user document: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        private void showAllUserData(String role, String email, String username) {
            profileUserName.setText(username);
            titleUsername.setText(username);
            profileEmail.setText(email);
            profileRole.setText(role);

        }

        private void sendUserDataToEditProfile() {
        Intent intent = new Intent(getActivity(), Editprofile.class);
        intent.putExtra("email", profileEmail.getText().toString());
        intent.putExtra("username", profileUserName.getText().toString());
        intent.putExtra("role", profileRole.getText().toString());
        startActivity(intent);
    }

}


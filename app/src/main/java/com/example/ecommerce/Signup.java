package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.User.UserHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private EditText signupEmail, signupPassword,signup_username;
    private Button signupButton;
    private TextView loginRedirectText;
    private Spinner roleSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signup_username=findViewById(R.id.signup_username);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        roleSpinner = findViewById(R.id.role_spinner);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString().trim();
                String name = signup_username.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String role = roleSpinner.getSelectedItem().toString();
                if (email.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                }
                if (name.isEmpty()){
                    signup_username.setError("username cannot be empty");
                }
                if (pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                } else{
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    String uid = user.getUid();

                                    // Create a user document in the database and set the role field
                                    DocumentReference userRef = firebaseFirestore.collection("users").document(uid);
                                    HashMap<String, Object> userData = new HashMap<>();
                                    userData.put("role", role);
                                    userData.put("email", email);
                                    userData.put("username", name);// Store the selected role in the database
                                    userRef.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                checkUserRoleFromDatabase(uid);

                                            } else {
                                                Toast.makeText(Signup.this, "Failed to create user document: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });}
                            } else {
                                Toast.makeText(Signup.this, "SignUp Failed" +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });


    }
    private void checkUserRoleFromDatabase(String userId) {
        DocumentReference userRef = firebaseFirestore.collection("users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String userRole = document.getString("role");
                        redirectBasedOnRole(userRole);
                    } else {
                        Toast.makeText(Signup.this, "User document not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signup.this, "Failed to get user document: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void redirectBasedOnRole(String userRole) {
        if (userRole != null && userRole.equals("Admin")) {

                startActivity(new Intent(Signup.this, HomeDashboard.class));
            } else {
                startActivity(new Intent(Signup.this, UserHome.class));
            }
            finish();
        }
}
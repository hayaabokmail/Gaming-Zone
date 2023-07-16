package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.fragments.HomeFragment;
import com.example.ecommerce.fragments.profilefragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Editprofile extends AppCompatActivity {
    private EditText editEmail, editUsername, editrole;
    private Button saveButton;
    private String emailUser, username, role;
    private FirebaseFirestore firebaseFirestore;
    DocumentReference userRef;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editrole = findViewById(R.id.editrole);
        saveButton = findViewById(R.id.saveButton);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userRef = firebaseFirestore.collection("users").document(userId);
         user = FirebaseAuth.getInstance().getCurrentUser();

        showData();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isroleChanged() || isEmailChanged()){
                    Toast.makeText(Editprofile.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Editprofile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNameChanged() {
        String newUsername = editUsername.getText().toString();
        if (!newUsername.equals(username)) {
            userRef.update("username", newUsername);
            username = newUsername;
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        String newEmail = editEmail.getText().toString();
        if (!newEmail.equals(emailUser)) {
            userRef.update("email", newEmail);
            emailUser = newEmail;
            user.updateEmail(newEmail);
            return true;
        } else {
            return false;
        }
    }

    private boolean isroleChanged() {
        String newRole = editrole.getText().toString();
        if (!newRole.equals(role)) {
            userRef.update("role", newRole);
            role = newRole;
            return true;
        } else {
            return false;
        }
    }
    public void showData(){
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        emailUser = intent.getStringExtra("email");
        role = intent.getStringExtra("role");

        editUsername.setText(username);
        editEmail.setText(emailUser);
        editrole.setText(role);
    }
}
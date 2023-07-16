package com.example.ecommerce.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ecommerce.R;
import com.example.ecommerce.UploadActivity;
import com.example.ecommerce.fragments.HomeFragment;
import com.example.ecommerce.fragments.profilefragment;
import com.example.ecommerce.fragmentuser.CartFragment;
import com.example.ecommerce.fragmentuser.Homeuser_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserHome extends AppCompatActivity {
    FloatingActionButton fab;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab=findViewById(R.id.fab);

        replaceFragment(new Homeuser_Fragment());


        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.homeuser:
                    replaceFragment(new Homeuser_Fragment());
                    break;
                case R.id.profile:
                    replaceFragment(new profilefragment());
                    break;
                case R.id.cart:
                    replaceFragment(new CartFragment());
                    break;

            }

            return true;
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CartFragment());
                bottomNavigationView.setSelectedItemId(R.id.cart);
            }
        });

    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
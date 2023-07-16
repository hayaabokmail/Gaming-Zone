package com.example.ecommerce;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserDetail extends AppCompatActivity {
    TextView detailDesc, detailname,detailPrice,detailTitle;
    ImageView detailImage;
    Button add_to_cart;
    String key = "";
    String imageUrl = "";
    int id;
    int quantity = 1;
    int originalPrice = 0;
    int newPrice = 0;
    FirebaseFirestore firebaseFirestore ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailname = findViewById(R.id.detailname);
        detailTitle=findViewById(R.id.detailTitle);
        TextView quantitynumber = findViewById(R.id.quantitynumber);
        ImageButton addquantity = findViewById(R.id.addquantity);
        ImageButton subquantity = findViewById(R.id.subquantity);
        detailPrice = findViewById(R.id.detailPrice);
        add_to_cart=findViewById(R.id.add_to_cart);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailTitle.setText(bundle.getString("Title"));
            detailDesc.setText(bundle.getString("Desc"));
            detailname.setText(bundle.getString("Title"));
            detailPrice.setText(bundle.getString("Price"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        addquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (originalPrice == 0) {
                    originalPrice = Integer.parseInt(detailPrice.getText().toString());
                }

                if (quantity < 5) {
                    quantity++;
                    quantitynumber.setText(String.valueOf(quantity));
                    newPrice = originalPrice * quantity;
                    String setnewPrice = String.valueOf(newPrice);
                    detailPrice.setText(setnewPrice);
                } else {
                    Toast.makeText(UserDetail.this, "Can`t increase quantity more than 5", Toast.LENGTH_SHORT).show();
                }
            }
        });

        subquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity == 1) {
                    Toast.makeText(UserDetail.this, "Can`t decrease quantity less than 1", Toast.LENGTH_SHORT).show();
                    quantity=1;

                    newPrice=1;
                } else {
                    quantity--;
                    quantitynumber.setText(String.valueOf(quantity));
                    newPrice = originalPrice * quantity;
                    String setnewPrice = String.valueOf(newPrice);
                    detailPrice.setText(setnewPrice);
                }
            }
        });

        add_to_cart.setOnClickListener(v -> {

            String title = detailTitle.getText().toString().trim();
            String price = detailPrice.getText().toString().trim();
            String desc = detailDesc.getText().toString().trim();
            String quantity = quantitynumber.getText().toString();

            Map<String, Object> cart = new HashMap<>();

            cart.put("title", title);
            cart.put("price", price);
            cart.put("desc", desc);
            cart.put("quantity", quantity);
            cart.put("image",imageUrl);

            firebaseFirestore.collection("cart")
                    .add(cart)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(UserDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  Toast.makeText(UserDetail.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                                              }
                                          }
                    );

        });
    }}
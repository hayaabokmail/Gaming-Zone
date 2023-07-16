package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.CartClass;
import com.example.ecommerce.Model.DataClass;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<CartClass> dataList;

    private FirebaseFirestore firebaseFirestore;

    public CartAdapter(Context context, List<CartClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getTitle());
        holder.recDesc.setText(dataList.get(position).getDesc());
        holder.recQuantity.setText(dataList.get(position).getQuantity());
        holder.recPrice.setText(dataList.get(position).getPrice());
        holder.delete_item.setOnClickListener(v -> {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("cart").document(dataList.get(position).getKey())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                            dataList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, dataList.size());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
                        }
                    });
        });


    }


    @Override
    public int getItemCount() {
        return dataList.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView recImage,delete_item;
        TextView recTitle, recDesc, recPrice,recQuantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage = itemView.findViewById(R.id.recImage);
            recQuantity = itemView.findViewById(R.id.recQuantity);
            recDesc = itemView.findViewById(R.id.recDesc);
            recPrice = itemView.findViewById(R.id.recPrice);
            recTitle = itemView.findViewById(R.id.recTitle);
            delete_item=itemView.findViewById(R.id.delete_item);
        }

    }}

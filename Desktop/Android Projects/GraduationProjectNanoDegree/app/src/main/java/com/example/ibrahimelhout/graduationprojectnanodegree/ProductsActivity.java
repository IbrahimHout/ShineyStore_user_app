package com.example.ibrahimelhout.graduationprojectnanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahimelhout.graduationprojectnanodegree.Adapter.ItemsAdapter;
import com.example.ibrahimelhout.graduationprojectnanodegree.Models.Product;
import com.example.ibrahimelhout.graduationprojectnanodegree.Utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class ProductsActivity extends AppCompatActivity {

    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noItemsTV)
    TextView noItemsTV;




    ArrayList<Product> products;
    ItemsAdapter itemsAdapter;
    String catId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);


        products = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this,products,catId);
        categoriesRecyclerView.setAdapter(itemsAdapter);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL,false));




        catId = getIntent().getStringExtra(Constants.EXTRA_CATEGORY_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (catId!=null)
            callItems(catId);

    }

    private void callItems(String catId) {

        FirebaseDatabase.getInstance().getReference().child(Constants.PRODUCTS).child(catId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressBar.setVisibility(View.VISIBLE);


                        products.clear();
                        for (DataSnapshot catSnap : dataSnapshot.getChildren()) {

                            Product cat = catSnap.getValue(Product.class);
                            cat.setId(catSnap.getKey());

                            Log.d(TAG, "onDataChange: ");

                            products.add(cat);
                        }


                        Collections.reverse(products);
                        updateAdapter(products);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProductsActivity.this, "Error Loading Data check your connection please,", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void updateAdapter(ArrayList<Product> categories) {
        if(categories.size()==0){
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.GONE);


            itemsAdapter.notifyDataSetChanged();
        }
    }


}

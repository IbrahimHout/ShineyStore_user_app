package com.example.ibrahimelhout.graduationprojectnanodegree;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahimelhout.graduationprojectnanodegree.Adapter.ItemsAdapter;
import com.example.ibrahimelhout.graduationprojectnanodegree.Models.Category;
import com.example.ibrahimelhout.graduationprojectnanodegree.Utils.Constants;
import com.example.ibrahimelhout.graduationprojectnanodegree.Utils.MotherActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class MainActivity extends MotherActivity {

    @BindView(R.id.signOutTest)
    Button signOutTest;
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;


    ArrayList<Category> categories;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ItemsAdapter itemsAdapter;
    @BindView(R.id.noItemsTV)
    TextView noItemsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);




        categories = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, categories);
        categoriesRecyclerView.setAdapter(itemsAdapter);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));


    }

    public void signOut(View view) {
        singOutAndUpdateUI();
    }


    @Override
    public void onResume() {
        super.onResume();


        callCategories();


    }

    private void callCategories() {

        FirebaseDatabase.getInstance().getReference().child(Constants.CATEGORIES)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressBar.setVisibility(View.VISIBLE);


                        categories.clear();
                        for (DataSnapshot catSnap : dataSnapshot.getChildren()) {

                            Category cat = catSnap.getValue(Category.class);
                            cat.setId(catSnap.getKey());

                            Log.d(TAG, "onDataChange: ");

                            categories.add(cat);
                        }


                        Collections.reverse(categories);
                        updateAdapter(categories);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Error Loading Data check your connection please,", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void updateAdapter(ArrayList<Category> categories) {
        if (categories.size() == 0) {
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.GONE);


            itemsAdapter.notifyDataSetChanged();
        }
    }

}

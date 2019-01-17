package com.example.ibrahimelhout.graduationprojectnanodegree.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibrahimelhout.graduationprojectnanodegree.Models.Category;
import com.example.ibrahimelhout.graduationprojectnanodegree.Models.Product;
import com.example.ibrahimelhout.graduationprojectnanodegree.ProductDetailsActivity;
import com.example.ibrahimelhout.graduationprojectnanodegree.ProductsActivity;
import com.example.ibrahimelhout.graduationprojectnanodegree.R;
import com.example.ibrahimelhout.graduationprojectnanodegree.Utils.Constants;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.CategoryViewHolder> {


    private Context context;
    private ArrayList items;
    private String catId;


    public ItemsAdapter(Context context, ArrayList items) {
        this.context = context;
        this.items = items;
    }

    public ItemsAdapter(Context context, ArrayList items, String catId) {
        this.context = context;
        this.items = items;
        this.catId = catId;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cell_categories, parent, false);


        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Object item = items.get(position) instanceof Category ? (Category) items.get(position) : (Product) items.get(position);

        if (item instanceof Category) {


            Object finalItem = item;
            holder.layoutItem.setOnClickListener(view -> {


                //todo
//
                Intent intent = new Intent(context, ProductsActivity.class);
                String id = ((Category) finalItem).getId();
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, id);
                Log.d(TAG, "onClick: card id is " + id);
                context.startActivity(intent);


            });


            if (!((Category) item).getName().isEmpty())
                holder.productName.setText(((Category) item).getName());

            if (!((Category) item).getDescription().isEmpty())
                holder.productDes.setText(((Category) item).getDescription());


            if (((Category) item).getImageLink() != null && !((Category) item).getImageLink().isEmpty()) {

                Picasso.get().load(((Category) item).getImageLink()).placeholder(R.drawable.ic_loading_24dp).error(R.drawable.ic_error_black_24dp).into(holder.productImage);

                Log.d(TAG, "onBindViewHolder: " + ((Category) item).getImageLink());


            }


        } else {
            // it is category
            if (!((Product) item).getName().isEmpty())
                holder.productName.setText(((Product) item).getName());

            if (!((Product) item).getDescription().isEmpty())
                holder.productDes.setText(((Product) item).getDescription());

            if (!((Product) item).getPrice().isEmpty()) {

                holder.priceTV.setText(((Product) item).getPrice() + " $");
                holder.priceTV.setVisibility(View.VISIBLE);
            }


            if (!((Product) item).getImageURL().isEmpty()) {

                Picasso.get().load(((Product) item).getImageURL()).placeholder(R.drawable.ic_loading_24dp).error(R.drawable.ic_error_black_24dp).into(holder.productImage);

            }

            holder.layoutItem.setOnClickListener(view -> {


                //todo
//
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                String id = ((Product) item).getId();
                intent.putExtra(Constants.EXTRA_PRODUCT_ID, id);
                Log.d(TAG, "onClick: card id is " + id);
                context.startActivity(intent);


            });

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productImage)
        ImageView productImage;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.productDes)
        TextView productDes;
        @BindView(R.id.priceTV)
        TextView priceTV;
        @BindView(R.id.salePriceTV)
        TextView salePriceTV;
        @BindView(R.id.layoutItem)
        LinearLayout layoutItem;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}

package com.improgrammer.retrofitapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improgrammer.retrofitapi.R;
import com.improgrammer.retrofitapi.modal.CategoryRVmodal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVadapter extends RecyclerView.Adapter<CategoryRVadapter.CatViewholder> {

    private final ArrayList<CategoryRVmodal> categoryRVadapterArrayList;
    private Context context;
    private ArrayList<CategoryRVmodal> categoryRVmodals;
    private CategoryClickedInterface categoryClickedInterface;

    public CategoryRVadapter(Context context, ArrayList<CategoryRVmodal> categoryRVadapterArrayList, CategoryClickedInterface categoryClickedInterface) {
        this.context = context;
        this.categoryRVadapterArrayList = categoryRVadapterArrayList;
        this.categoryClickedInterface = categoryClickedInterface;
    }

    @NonNull
    @Override
    public CatViewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_cata,parent,false);
        return new CatViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVadapter.CatViewholder holder, int position) {
       holder.news_cata_tv.setText(categoryRVadapterArrayList.get(position).getCategory());

        Picasso.get().load(categoryRVadapterArrayList.get(position).getCategoryImgUrl()).into(holder.news_cata_iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickedInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVadapterArrayList.size();
    }


    public interface CategoryClickedInterface{
        void onCategoryClick(int postion);
    }

    public class CatViewholder extends RecyclerView.ViewHolder{

        TextView news_cata_tv;
        ImageView news_cata_iv;

        public CatViewholder(@NonNull View itemView) {
            super(itemView);
            news_cata_iv = itemView.findViewById(R.id.news_cata_iv);
            news_cata_tv = itemView.findViewById(R.id.news_cata_tv);
        }
    }
}

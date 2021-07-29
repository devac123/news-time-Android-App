package com.improgrammer.retrofitapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.improgrammer.retrofitapi.Detail_news;
import com.improgrammer.retrofitapi.R;
import com.improgrammer.retrofitapi.modal.Articles;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.NewsViewHolder> implements Filterable {

    Context context;
    ArrayList<Articles> articles;
    ArrayList<Articles> articlesall;


    public NewsRvAdapter(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
        this.articlesall = new ArrayList<>(articles);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRvAdapter.NewsViewHolder holder, int position) {
       holder.newsItem_tv.setText(articles.get(position).getTitle());

        Picasso.get().load(articles.get(position).getUrlToimg()).into(holder.newsItem_iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getDetail = new Intent(context,Detail_news.class);
                getDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getDetail.putExtra("title",articles.get(position).getTitle());
                getDetail.putExtra("desc",articles.get(position).getDescription());
                getDetail.putExtra("imgUrl",articles.get(position).getUrlToimg());
                getDetail.putExtra("url",articles.get(position).getUrl());
                getDetail.putExtra("content",articles.get(position).getContent());
                context.startActivity(getDetail);

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Articles> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(articlesall);
            }
            else {
                for (Articles  charSeq : articlesall ){
                    if(charSeq.getTitle().contains(constraint.toString().toLowerCase()) ||
                            charSeq.getDescription().contains(constraint.toString().toLowerCase()) ||
                            charSeq.getContent().contains(constraint.toString().toLowerCase())  ){
                        filteredList.add(charSeq);

                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
             articles.clear();
             articles.addAll((Collection<? extends Articles>) results.values);
        }
    };

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView newsItem_iv;
        TextView newsItem_tv;
        public NewsViewHolder(@NonNull  View itemView) {
            super(itemView);
            newsItem_iv = itemView.findViewById(R.id.news_item_iv);
            newsItem_tv = itemView.findViewById(R.id.news_item_tv);
        }
    }
}

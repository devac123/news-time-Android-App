package com.improgrammer.retrofitapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.improgrammer.retrofitapi.adapters.CategoryRVadapter;
import com.improgrammer.retrofitapi.adapters.NewsRvAdapter;
import com.improgrammer.retrofitapi.interfaces.RetrofitApi;
import com.improgrammer.retrofitapi.modal.Articles;
import com.improgrammer.retrofitapi.modal.CategoryRVmodal;
import com.improgrammer.retrofitapi.modal.NewsModal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVadapter.CategoryClickedInterface {
     RecyclerView newsCata_list,newsitem_list;
     CategoryRVadapter categoryRVadapter;
     NewsRvAdapter newsRvAdapter;
     ProgressBar progressBar;
     SwipeRefreshLayout swipeRefreshLayout;
     ArrayList<Articles> articles_items;
     ArrayList<CategoryRVmodal> categoryRVmodalArrayList;
     ArrayList<NewsModal> newsModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsCata_list = findViewById(R.id.news_cata_list);
        newsitem_list = findViewById(R.id.news_item_list);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.latest_update);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews("All");
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        categoryRVmodalArrayList = new ArrayList<>();
        newsModalArrayList = new ArrayList<>();
        articles_items = new ArrayList<>();

        getCategories();

        categoryRVadapter = new CategoryRVadapter(getApplicationContext(),categoryRVmodalArrayList,this::onCategoryClick);
        newsCata_list.setAdapter(categoryRVadapter);
        getNews("All");

    }

    private void getCategories()
    {
        categoryRVmodalArrayList.add(new CategoryRVmodal("All","https://images.unsplash.com/photo-1606787366850-de6330128bfc?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Technology","https://images.unsplash.com/photo-1531297484001-80022131f5a1?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Science","https://images.unsplash.com/photo-1518152006812-edab29b069ac?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fHNjaWVuY2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Sports","https://images.unsplash.com/photo-1541534741688-6078c6bfb5c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c3BvcnRzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("General","https://images.unsplash.com/photo-1513151233558-d860c5398176?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGdlbmVyYWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Buisness","https://images.unsplash.com/photo-1576267443888-219e7358f757?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NHx5S0hTV09IQXlOUXx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Entertainment","https://images.unsplash.com/photo-1603739903239-8b6e64c3b185?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVmodalArrayList.add(new CategoryRVmodal("Health","https://images.unsplash.com/photo-1505944270255-72b8c68c6a70?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGhlYWx0aHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));

    }

    private void getNews(String category){
        progressBar.setVisibility(View.VISIBLE);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String BaseUrl = "https://newsapi.org";
        String url = "\n" +
                "https://newsapi.org/v2/everything?q=all&from=" + dateFormat.format(date) +"&sortBy=publishedAt&apiKey=1c4c18c5c53042d79e55c586d3e80af8";

        String categoryUrl = "\n" +
                "https://newsapi.org/v2/everything?q=" + category +"&from=" + dateFormat.format(date) + "&sortBy=publishedAt&apiKey=1c4c18c5c53042d79e55c586d3e80af8";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitApi.getAllnewsitem(url);
        }
        else {
            call = retrofitApi.getAllnewsitem(categoryUrl);
        }


        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                progressBar.setVisibility(View.GONE);
                NewsModal newsModal = response.body();
                Log.d("newsModel", "onResponse: "+newsModal);
                ArrayList<Articles> articlesArr = newsModal.getArticles();
                for (int i = 0; i<articlesArr.size(); i++){
                    articles_items.add(new Articles(articlesArr.get(i).getTitle(),
                            articlesArr.get(i).getDescription(),
                            articlesArr.get(i).getUrlToimg(),
                            articlesArr.get(i).getUrl(),
                            articlesArr.get(i).getContent()));

                }

              newsRvAdapter = new NewsRvAdapter(getApplicationContext(),articlesArr);
                newsitem_list.setAdapter(newsRvAdapter);
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
               Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int postion) {
        String Category  = categoryRVmodalArrayList.get(postion).getCategory();
        articles_items.clear();
         getNews(Category);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.newsSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newsRvAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
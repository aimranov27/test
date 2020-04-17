package com.cis350.groupproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import serverbackend.data.NewsCategory;
import serverbackend.processor.NewsProcessor;

public class NewsCategoriesPage extends AppCompatActivity {
    // this is the processor for the news, responsible for getting the categories
    private NewsProcessor ns;

    GridView gridView;

    // this will contain all the NewsCategory objects
    List<NewsCategory> newsCategories = new ArrayList<NewsCategory>();
    List<Bitmap> catImages = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_categories_page);

        gridView = (GridView)findViewById(R.id.category_grid);

        GridAdapter gridAdapter = new GridAdapter();

        ns = new NewsProcessor();
        Runnable getCategoriesRunnable = () -> {
            try {
                Log.e("hey", "about to category");
                newsCategories = ns.getAllCategories();
                Log.e("hey", "got past getting categories");
                catImages = new ArrayList<Bitmap>();
                Log.e("hey", "got past connections");
                for (int i = 0; i < newsCategories.size(); i++) {
                    NewsCategory cat = newsCategories.get(i);
                    Bitmap og = null;
                    try {
                        URL url = new URL(cat.getImgURL());
                        og = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    } catch (Exception e) {
                        // if something goes wrong with just the image
                        og =  BitmapFactory.decodeResource(getResources(),R.drawable.business);
                    }
                    int newLength = Math.min(og.getWidth(), og.getHeight());
                    Bitmap bitEdited = Bitmap.createBitmap(og, 0, 0, newLength, newLength);
                    bitEdited = Bitmap.createScaledBitmap(bitEdited, 500, 500, false);
                    catImages.add(bitEdited);


                }
                Log.e("cool", "imma boutaa do it");


            } catch (Exception e) {
                Log.e("hey", e.toString());
                e.printStackTrace();
//                Log.e("error", e.stack);
            }
        };
        Thread getCategoriesThread = new Thread(getCategoriesRunnable);
        getCategoriesThread.start();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                gridView.setAdapter(gridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), NewsFeed.class);
                        intent.putExtra("category", newsCategories.get(position).getName());
                        startActivity(intent);
                    }
                });
            }
        }, 4500);   //5 seconds
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
//            return (newsCategories == null) ? 0 : (int)Math.ceil((double)newsCategories.size() / 2.0);
            return (newsCategories == null) ? 0 : newsCategories.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.category_box, viewGroup, false);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.category_text);
            ImageView image = view1.findViewById(R.id.category_image);

            name.setText(" " + newsCategories.get(i).getName());
            image.setImageBitmap(catImages.get(i));

            return view1;
        }
    }

}

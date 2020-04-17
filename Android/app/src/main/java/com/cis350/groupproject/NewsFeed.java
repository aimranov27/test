package com.cis350.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import serverbackend.data.NewsArticle;

public class NewsFeed extends AppCompatActivity {


    private String catName;
    protected GridView gridView;
    protected List<NewsArticle> newsArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        getIntent().getStringExtra("category");

        newsArticles = new ArrayList<NewsArticle>();

        gridView = (GridView)findViewById(R.id.news_feed_grid);
        GridAdapter gridAdapter = new GridAdapter();

    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
//            return (newsCategories == null) ? 0 : (int)Math.ceil((double)newsCategories.size() / 2.0);
            return (newsArticles == null) ? 0 : newsArticles.size();
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
            View view1 = getLayoutInflater().inflate(R.layout.news_card, viewGroup, false);
            //getting view in row_data
            TextView title = view1.findViewById(R.id.news_card_title);
            TextView desc = view1.findViewById(R.id.news_card_desc);
            ImageView image = view1.findViewById(R.id.news_card_image);

            title.setText(" " + newsArticles.get(i).getTitle());
            desc.setText(" " + newsArticles.get(i).getDescription());
//            image.setImageBitmap(catImages.get(i)); set via async function

            return view1;
        }
    }
}

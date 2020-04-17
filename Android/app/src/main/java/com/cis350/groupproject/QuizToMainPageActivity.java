package com.cis350.groupproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class QuizToMainPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_to_main);
        Intent intent = new Intent(getApplicationContext(), NewsCategoriesPage.class);
        startActivity(intent);
    }
}

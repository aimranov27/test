package com.cis350.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import com.cis350.groupproject.models.QuizQuestion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final Button submit = findViewById(R.id.btnSubmit);
        recyclerView = (RecyclerView) findViewById(R.id.rvQuiz);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        // specify an adapter (see also next example)
        final QuizAdapter qAdapter = new QuizAdapter(quizQuestions());
        recyclerView.setAdapter(qAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (QuizAdapter.numAnswered == quizQuestions().size()) {
                    Toast.makeText(getApplicationContext(), "Social Score: " + qAdapter.totals()[0] +
                            "\nEconomic Score: " + qAdapter.totals()[1],
                            Toast.LENGTH_LONG).show();
                    final Intent i = new Intent(QuizActivity.this, QuizToMainPageActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "please answer all the questions",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public List<QuizQuestion> quizQuestions() {
        List<QuizQuestion> questions = new ArrayList<QuizQuestion>();

        String q1 = "Adult possession and use of drugs should not be prohibited.";
        questions.add(new QuizQuestion(q1, true));
        String q2 = "Abortion should be legal while the fetus is not viable";
        questions.add(new QuizQuestion(q2, true));
        String q3 = "Military service should be voluntary and there should be no draft";
        questions.add(new QuizQuestion(q3, true));
        String q4 = "Illegal immigrants should not be deported";
        questions.add(new QuizQuestion(q4, true));
        String q5 = "There should be no laws or regulations concerning sex between consenting adults";
        questions.add(new QuizQuestion(q5, true));
        String q6 = "The government should not give handouts to businesses (corporate welfare).";
        questions.add(new QuizQuestion(q6, false));
        String q7 = "Taxes should be cut for people of all incomes, and the government should spend less.";
        questions.add(new QuizQuestion(q7, false));
        String q8 = "End government barriers to international free trade.";
        questions.add(new QuizQuestion(q8, false));
        String q9 = "Things like healthcare and Social Security should be privatized.";
        questions.add(new QuizQuestion(q9, false));
        String q10 = "Charity should have a larger role in providng welfare than the government.";
        questions.add(new QuizQuestion(q10, false));

        return questions;
    }

}

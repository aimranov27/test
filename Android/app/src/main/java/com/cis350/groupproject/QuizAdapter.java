package com.cis350.groupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cis350.groupproject.models.QuizQuestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private static final Integer NUM_QUESTIONS = 10;
    private List<QuizQuestion> quizQuestions;
    private int socialTotal;
    private int economicTotal;
    public static int numAnswered;

    public QuizAdapter(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
        numAnswered = 0;
        socialTotal = 0;
        economicTotal = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder h = holder;
        final QuizQuestion currentQuestion = quizQuestions.get(position);
        final String title = currentQuestion.getQuestion();
        holder.tvTitle.setText(title);
        holder.tvChoice1.setText("Agree");
        holder.tvChoice2.setText("Partially Agree");
        holder.tvChoice3.setText("Disagree");
        boolean isClicked = false;

        if (!currentQuestion.isAnswered()) {
            h.ivChoice1.setImageResource(R.drawable.filled_check_bground);
        }

        holder.ivChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quizQuestions.get(getAdapterPosition())
                if (!currentQuestion.isAnswered()) {
                    h.ivChoice1.setImageResource(R.drawable.filled_check_fground);
                    numAnswered++;
                    if (currentQuestion.isPersonalOrEconomic()) {
                        socialTotal += 20;
                    } else {
                        economicTotal += 20;
                    }
                    currentQuestion.setAnswered(true);
                } else{
                    h.ivChoice1.setImageResource(R.drawable.filled_check_bground);
                    numAnswered--;
                    if (currentQuestion.isPersonalOrEconomic()) {
                        socialTotal -= 20;
                    } else {
                        economicTotal -= 20;
                    }
                    currentQuestion.setAnswered(false);
                }
            }
        });

        holder.ivChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quizQuestions.get(getAdapterPosition())
                if (!currentQuestion.isAnswered()) {
                    h.ivChoice3.setImageResource(R.drawable.filled_check_fground);
                    numAnswered++;
                    currentQuestion.setAnswered(true);
                } else{
                    h.ivChoice3.setImageResource(R.drawable.filled_check_bground);
                    numAnswered--;
                    currentQuestion.setAnswered(false);
                }
            }
        });

        holder.ivChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quizQuestions.get(getAdapterPosition())
                if (!currentQuestion.isAnswered()) {
                    h.ivChoice2.setImageResource(R.drawable.filled_check_fground);
                    numAnswered++;
                    if (currentQuestion.isPersonalOrEconomic()) {
                        socialTotal += 10;
                    } else {
                        economicTotal += 10;
                    }
                    currentQuestion.setAnswered(true);
                } else{
                    h.ivChoice2.setImageResource(R.drawable.filled_check_bground);
                    numAnswered--;
                    if (currentQuestion.isPersonalOrEconomic()) {
                        socialTotal -= 10;
                    } else {
                        economicTotal -= 10;
                    }
                    currentQuestion.setAnswered(false);
                }
            }
        });

    }

    public int[] totals() {
        int [] t = {socialTotal, economicTotal};
        return t;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ivChoice1)
        ImageView ivChoice1;
        @BindView(R.id.ivChoice2)
        ImageView ivChoice2;
        @BindView(R.id.ivChoice3)
        ImageView ivChoice3;
        @BindView(R.id.tvChoice1)
        TextView tvChoice1;
        @BindView(R.id.tvChoice2)
        TextView tvChoice2;
        @BindView(R.id.tvChoice3)
        TextView tvChoice3;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivChoice1.setImageResource(R.drawable.filled_check_bground);
            ivChoice2.setImageResource(R.drawable.filled_check_bground);
            ivChoice3.setImageResource(R.drawable.filled_check_bground);
        }
    }

    @Override
    public int getItemCount() {
        return quizQuestions.size();
    }
}

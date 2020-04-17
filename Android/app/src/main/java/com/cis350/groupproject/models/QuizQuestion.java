package com.cis350.groupproject.models;

public class QuizQuestion {
    private String question;
    // true for personal, false for economic
    private boolean isPersonalOrEconomic;
    private boolean isAnswered;

    public QuizQuestion(String question, boolean isPersonalOrEconomic) {
        this.question = question;
        this.isPersonalOrEconomic = isPersonalOrEconomic;
        isAnswered = false;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isPersonalOrEconomic() {
        return isPersonalOrEconomic;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}

package com.example.mathfastgame.Model;

public class Game {
    int id;
    String ques;
    String answer;

    public Game(int id, String ques, String answer) {
        this.id = id;
        this.ques = ques;
        this.answer = answer;
    }

    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

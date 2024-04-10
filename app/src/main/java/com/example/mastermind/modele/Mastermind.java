package com.example.mastermind.modele;

import java.util.ArrayList;

public class Mastermind {

    private Code code;
    private ArrayList<ArrayList<String>> tentatives;
    private ArrayList<Feedback> feedbacks;
    private int nbTentatives;

    public Mastermind(){ // constructeur par default
        this.code = new Code();
        this.tentatives = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
        this.nbTentatives = 0;
    }

    public Mastermind(Code code) {

        this.code = code;
        this.tentatives = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
        this.nbTentatives = 0;
    }

    public void ajouterTentative(ArrayList<String> tentative, Feedback feedback) {
        this.tentatives.add(tentative);
        this.feedbacks.add(feedback);
        this.nbTentatives++;
    }

    public Code getCode() {
        return this.code;
    }

    public ArrayList<ArrayList<String>> getTentatives() {
        return tentatives;
    }
}

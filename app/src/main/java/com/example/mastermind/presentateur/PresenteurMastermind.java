package com.example.mastermind.presentateur;


import android.app.Activity;

import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.Feedback;
import com.example.mastermind.modele.Mastermind;
import com.example.mastermind.modele.ModeleManager;
import com.example.mastermind.modele.RecordCode;

public class PresenteurMastermind {
    private Activity activites;
    private Code code;
    private Feedback feedback;
    private Mastermind mastermind;
    private RecordCode recordCode;
    public PresenteurMastermind(Activity activites){
        this.activites = activites;
        this.code = ModeleManager.getCode();
        this.feedback = ModeleManager.getFeedback();
        this.mastermind = ModeleManager.getMastermind();
        this.recordCode = ModeleManager.getRecordCode();
    }
}

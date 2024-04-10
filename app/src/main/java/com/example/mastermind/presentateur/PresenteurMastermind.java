package com.example.mastermind.presentateur;


import android.app.Activity;

import com.example.mastermind.activites.Jouer;
import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.Feedback;
import com.example.mastermind.modele.Mastermind;
import com.example.mastermind.modele.ModeleManager;
import com.example.mastermind.modele.RecordCode;

public class PresenteurMastermind {
    private Jouer jouer;
    private Activity activites;
    private Code code;
    private Feedback feedback;
    private Mastermind mastermind;
    private RecordCode recordCode;

  /*  public PresenteurMastermind(Activity activites){
        this.activites = activites;
        this.code = ModeleManager.getCode();
        this.feedback = ModeleManager.getFeedback();
        this.mastermind = ModeleManager.getMastermind();
        this.recordCode = ModeleManager.getRecordCode();
    }

   */

    public PresenteurMastermind(Jouer pJouer, Code pCode, Feedback pFeedback, Mastermind pMastermind, RecordCode pRecordCode){
        //jouer = pJouer;
        code = pCode;
        feedback = pFeedback;
        mastermind = pMastermind;
        recordCode = pRecordCode;
    }

    public PresenteurMastermind(Activity jouer){
        this.jouer = new Jouer();
    }
    public void initializer(){
        try{
            jouer.afficherCouleursDisponible();
            jouer.afficherCodeSecret();
            jouer.afficherGrille();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

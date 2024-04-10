package com.example.mastermind.modele;

public class ModeleManager {
    private static Code code = null;
    private static Feedback feedback = null;
    private static Mastermind mastermind = null;
    private static RecordCode recordCode = null;


    public static Code getCode(){
        if(code == null){
            code = new Code();
        }
        return code;
    }

    public static boolean detruireCode() {
        boolean detruit = true;

        if (code != null)
            code = null;
        else
            detruit = false;

        return detruit;
    }

    public static Feedback getFeedback(){
        if(feedback == null){
            feedback = new Feedback();
        }
        return feedback;
    }

    public static boolean detruireFeedback() {
        boolean detruit = true;

        if (feedback != null)
            feedback = null;
        else
            detruit = false;

        return detruit;
    }

    public static Mastermind getMastermind(){
        if(mastermind == null){
            mastermind = new Mastermind();
        }
        return mastermind;
    }

    public static boolean detruireMastermind() {
        boolean detruit = true;

        if (mastermind != null)
            mastermind = null;
        else
            detruit = false;

        return detruit;
    }

    public static RecordCode getRecordCode(){
        if(recordCode == null){
            recordCode = new RecordCode();
        }
        return recordCode;
    }

    public static boolean detruireRecordCode() {
        boolean detruit = true;

        if (recordCode != null)
            recordCode = null;
        else
            detruit = false;

        return detruit;
    }

}

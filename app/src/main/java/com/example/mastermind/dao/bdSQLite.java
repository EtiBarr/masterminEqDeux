package com.example.mastermind.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class bdSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOM = "partie.db";
    private static final String TABLE_PARTIE = "partie";

    private static final String COLONNE_ID = "_id";
    private static final String COLONNE_EMAIL = "email";
    private static final String COLONNE_CODE_SECRET = "code_secret";
    private static final String COLONNE_NOMBRE_DE_COULEURS = "nombre_de_couleurs";
    private static final String COLONNE_RESULTAT = "resultat";
    private static final String COLONNE_NOMBRE_DE_TENTATIVES = "nombre_de_tentatives";

    public bdSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, bdSQLite.DATABASE_NOM, null, bdSQLite.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la base de données "partie.db"
        String query = "CREATE TABLE " + TABLE_PARTIE + "("
                        + COLONNE_ID + " INTEGER,"
                        + COLONNE_EMAIL + " TEXT,"
                        + COLONNE_CODE_SECRET + " TEXT,"
                        + COLONNE_NOMBRE_DE_COULEURS + " INTEGER,"
                        + COLONNE_RESULTAT + " TEXT,"
                        + COLONNE_NOMBRE_DE_TENTATIVES + " INTEGER"
                        + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // détruire la BD si elle existe
        String query = "DROP TABLE IF EXISTS " + DATABASE_NOM;
        db.execSQL(query);
        // recréer la BD
        onCreate(db);
    }

    public void ajouterPartie(String email, String codeSecret, int nombreDeCouleurs, String resultat, int nombreDeTentatives, int id) {

        ContentValues valeur = new ContentValues();
        valeur.put(bdSQLite.COLONNE_ID, id);
        valeur.put(bdSQLite.COLONNE_EMAIL, email);
        valeur.put(bdSQLite.COLONNE_CODE_SECRET, codeSecret);
        valeur.put(bdSQLite.COLONNE_NOMBRE_DE_COULEURS, nombreDeCouleurs);
        valeur.put(bdSQLite.COLONNE_RESULTAT, resultat);
        valeur.put(bdSQLite.COLONNE_NOMBRE_DE_TENTATIVES, nombreDeTentatives);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_PARTIE, null, valeur);
        db.close();

    }

    //retourner la liste des partie
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> retournerListePartie(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> partieList = new ArrayList<>();

        String query = "SELECT _id, email, code_secret, nombre_de_couleurs, resultat, nombre_de_tentatives " +
                        "FROM "+ TABLE_PARTIE;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(COLONNE_ID)));
            user.put("Email",cursor.getString(cursor.getColumnIndex(COLONNE_EMAIL)));
            user.put("Code secret",cursor.getString(cursor.getColumnIndex(COLONNE_CODE_SECRET)));
            user.put("nombre de couleurs",cursor.getString(cursor.getColumnIndex(COLONNE_NOMBRE_DE_COULEURS)));
            user.put("Resultat",cursor.getString(cursor.getColumnIndex(COLONNE_RESULTAT)));
            user.put("Nombre de tentatives",cursor.getString(cursor.getColumnIndex(COLONNE_NOMBRE_DE_TENTATIVES)));
            partieList.add(user);
        }
        cursor.close();
        return  partieList;
    }

    //retourner liste de partie par email
    @SuppressLint("Range")
    public HashMap<String, String> getPartieAvecEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> partieDetails = new HashMap<>();

        String query = "SELECT * " +
                        "FROM " + TABLE_PARTIE +
                        " WHERE " + COLONNE_EMAIL;
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            partieDetails.put("id", cursor.getString(cursor.getColumnIndex(COLONNE_ID)));
            partieDetails.put("Email", cursor.getString(cursor.getColumnIndex(COLONNE_EMAIL)));
            partieDetails.put("Code secret", cursor.getString(cursor.getColumnIndex(COLONNE_CODE_SECRET)));
            partieDetails.put("nombre de couleurs", cursor.getString(cursor.getColumnIndex(COLONNE_NOMBRE_DE_COULEURS)));
            partieDetails.put("Resultat", cursor.getString(cursor.getColumnIndex(COLONNE_RESULTAT)));
            partieDetails.put("Nombre de tentatives", cursor.getString(cursor.getColumnIndex(COLONNE_NOMBRE_DE_TENTATIVES)));
        }

        cursor.close();
        return partieDetails;
    }
}

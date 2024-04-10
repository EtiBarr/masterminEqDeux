package com.example.mastermind.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public bdSQLite(Context context){
        super(context, bdSQLite.DATABASE_NOM, null, bdSQLite.DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la base de données "partie.db"
        String query = "CREATE TABLE " + TABLE_PARTIE + "("
                        + COLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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

    public void ajouterPartie(String email, String codeSecret, int nombreDeCouleurs, String resultat, int nombreDeTentatives) {

        ContentValues valeur = new ContentValues();
        valeur.put(bdSQLite.COLONNE_EMAIL, email);
        valeur.put(bdSQLite.COLONNE_CODE_SECRET, codeSecret);
        valeur.put(bdSQLite.COLONNE_NOMBRE_DE_COULEURS, nombreDeCouleurs);
        valeur.put(bdSQLite.COLONNE_RESULTAT, resultat);
        valeur.put(bdSQLite.COLONNE_NOMBRE_DE_TENTATIVES, nombreDeTentatives);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_PARTIE, null, valeur);
        db.close();

    }

}

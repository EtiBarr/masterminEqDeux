package com.example.mastermind.dao;

import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.RecordCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpJsonService {

    private static String URL_POINT_ENTREE = "http://10.0.2.2:3000";

    public static ArrayList<Code> obtenirCodeSecret(int nbCouleurs) throws IOException, JSONException {

        ArrayList<Code> codesSecrets = new ArrayList<>();
        Code codeSecret;

        OkHttpClient okHttpClient = new OkHttpClient();

        String requete = "/codesSecrets?nbCouleurs=" + nbCouleurs;

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + requete)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();

        JSONArray jsonArray = new JSONArray(responseBody.string());

        // Prendre le code de chaque objet JSON
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            JSONArray codeCouleurs=  jsonObject.getJSONArray("code");
            int codeId = jsonObject.getInt("id");
            int nombreCouleurs = jsonObject.getInt("nbCouleurs");

            ArrayList<String> code = new ArrayList<>();
            for (int j = 0; j < codeCouleurs.length(); j++) {
                code.add(codeCouleurs.getString(j));
            }

            codeSecret = new Code(nombreCouleurs, codeId, code);
            codesSecrets.add(codeSecret);
        }

        return codesSecrets;
    }

    public static RecordCode obtenirRecord(int id) throws IOException, JSONException {

        RecordCode record;

        OkHttpClient okHttpClient = new OkHttpClient();

        String requete = "/stats?idCode=" + id;

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + requete)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();

        JSONArray jsonArray = new JSONArray(responseBody.string());

        if (jsonArray.length() == 0) {
            return null;
        }

        else if (jsonArray.length() > 1) {
            throw new IOException("Plus d'un record existe dans le serveur");
        }

        else  {

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String courriel = jsonObject.getString("courriel");
            int nbTentatives = jsonObject.getInt("record");

            record = new RecordCode(nbTentatives, courriel, id);
            return record;
        }
    }

    public static ArrayList<String> obtenirCouleurs() throws IOException, JSONException {

        ArrayList<String> listeCouleurs = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/couleursDisponibles")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();

        JSONObject jsonObject = new JSONObject(responseBody.string());
        JSONArray couleurs = jsonObject.getJSONArray("liste");

        for (int j = 0; j < couleurs.length(); j++) {
            listeCouleurs.add(couleurs.getString(j));
        }

        return listeCouleurs;
    }




}

package com.example.mastermind.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.RecordCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

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

            codeSecret = new Code(codeCouleurs.length(), codeId, code);
            codesSecrets.add(codeSecret);
        }

        return codesSecrets;
    }

    public static RecordCode obtenirRecord(int id) throws IOException, JSONException {

        RecordCode record;

        OkHttpClient okHttpClient = new OkHttpClient();

        String requete = "/stats?idCode=" + String.valueOf(id);;

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + requete)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        JSONArray jsonArray = new JSONArray(responseBody.string());
        System.out.println(jsonArray);

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

    public static int obtenirDernierId() throws IOException, JSONException {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/stats")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();

        JSONArray jsonArray = new JSONArray(responseBody.string());

        return jsonArray.length();
    }

    public static void creerRecord(int id, int nbTentatives, String courriel, int idStat) throws JSONException, IOException {

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Données POST
        JSONObject obj = new JSONObject();
        obj.put("idCode", String.valueOf(id));
        obj.put("record", String.valueOf(nbTentatives));
        obj.put("courriel", courriel);
        obj.put("id", String.valueOf(idStat));

        RequestBody corpsRequete = RequestBody.create(obj.toString(), JSON);

        // Executer la requête
        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/stats")
                .post(corpsRequete)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
    }

    public static void changerRecord(int id, int nbTentatives, String courriel) throws JSONException, IOException {

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Données POST
        JSONObject obj = new JSONObject();
        obj.put("record", String.valueOf(nbTentatives));
        obj.put("courriel", courriel);

        RequestBody corpsRequete = RequestBody.create(obj.toString(), JSON);

        // Executer la requête
        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/stats" + id)
                .put(corpsRequete)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
    }

}

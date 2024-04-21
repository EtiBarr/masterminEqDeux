package com.example.mastermind.adaptateur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.mastermind.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PartieAdaptateur extends SimpleAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private int resource;
    private String[] from;
    private int[] to;

    // Constructeur
    public PartieAdaptateur(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.context = context;
        this.data = data;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }

    // Créer une vue pour ListeView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        // Initialiser la vue
        if (view == null) {
            // Obtient le système de création de vues
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Convertit la ressource de mise en page en vue
            view = layoutInflater.inflate(this.resource, parent,false);
        }

        // Obtenir les données pour la position actuelle
        HashMap<String, String> item = data.get(position);

        // Pour les données existantes
        if (item != null) {
            // Initialiser les champs
            TextView tvCourriel = view.findViewById(R.id.tvInputCourrielH);
            TextView tvCode = view.findViewById(R.id.tvInputCodeH);
            TextView tvCouleurs = view.findViewById(R.id.tvInputCouleursH);
            TextView tvResultat = view.findViewById(R.id.tvInputResultatH);
            TextView tvTentatives = view.findViewById(R.id.tvInputTentativesH);

            // Définir le texte dans les champs
            tvCourriel.setText(item.get(from[0]));
            tvCode.setText(item.get(from[1]));
            tvCouleurs.setText(item.get(from[2]));
            tvResultat.setText(item.get(from[3]));
            tvTentatives.setText(item.get(from[4]));
        }

        return view;
    }
}

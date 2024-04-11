package com.example.mastermind.adaptateur;

import android.content.Context;
import android.view.View;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class PartieAdaptateur extends SimpleAdapter {
    public PartieAdaptateur(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    /*
    @Override
    public View getView(int position, View con)

    return view;

     */
}

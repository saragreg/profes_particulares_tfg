package com.example.tfg_profes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorProfesLista extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<String> nombres;
    private ArrayList<String> precios;
    private ArrayList<String> puntuaciones;
    public AdaptadorProfesLista(Context pcontext, ArrayList<String> pnombres, ArrayList<String> pprecio, ArrayList<String> ppuntuaciones)
    {
        contexto = pcontext;
        nombres = pnombres;
        precios=pprecio;
        puntuaciones=ppuntuaciones;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombres.size();
    }

    @Override
    public Object getItem(int i) {
        return nombres.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.adaptador_profes,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombre);
        TextView precio=(TextView) view.findViewById(R.id.precio);
        RatingBar barra= (RatingBar) view.findViewById(R.id.ratingBar);
        nombre.setText(nombres.get(i));
        precio.setText(precios.get(i)+"€");
        barra.setRating(Float.parseFloat(puntuaciones.get(i)));
        return view;
    }
}


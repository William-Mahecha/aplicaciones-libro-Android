package com.example.geralt.libroandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab);
        TabHost tabHost = getTabHost(); //TabHost de la actividad
        TabHost.TabSpec spec; //Para las propiedades de la pestaña
        Intent intent; // Intent que se utilizara en cada pestaña
        Resources res = getResources(); //Para la obtencion de los recursos

        //Se crea un Intent para lanzar la activity, se reutilizara mas adelante
        intent = new Intent().setClass(this, Absolute.class);
        //Configuramos el TabSpec para cada pestaña y se añado al TabHost
        spec = tabHost.newTabSpec("Absolute").setIndicator("Absolute", res.getDrawable(R.drawable.tab_absolute)).setContent(intent);
        tabHost.addTab(spec);
        //Seguimos haciendo lo mismo con el resto de las pestañas
        intent = new Intent().setClass(this, List.class);
        spec = tabHost.newTabSpec("List").setIndicator("List", res.getDrawable(R.drawable.tab_list)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, LinearLayout.class);
        spec = tabHost.newTabSpec("LinearLayout").setIndicator("LinearLayout", res.getDrawable(R.drawable.tab_linear)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Grid.class);
        spec = tabHost.newTabSpec("Grid").setIndicator("Grid", res.getDrawable(R.drawable.tab_absolute)).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(1);
    }
}
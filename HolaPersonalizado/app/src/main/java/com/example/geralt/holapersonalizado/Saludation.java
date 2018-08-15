package com.example.geralt.holapersonalizado;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Saludation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saludation);
        String saludo = getIntent().getExtras().getString("saludo");
        TextView salida = findViewById(R.id.salida);
        salida.setText(saludo);
    }
}

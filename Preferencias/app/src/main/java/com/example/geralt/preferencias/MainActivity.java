package com.example.geralt.preferencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText block = null;
    EditText key = null;
    EditText value = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        block = findViewById(R.id.edBlock);
        key = findViewById(R.id.edKey);
        value = findViewById(R.id.edValue);
        
        //Asignamos codigo al boton de salvar
        Button btnSalvar = this.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        //Asignamos codigo al boton de recuperar
        Button btnRecuperar = this.findViewById(R.id.btnReq);
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieve();
            }
        });
    }

    private void save() {
        String blockValue = block.getText().toString().trim();
        String keyValue = key.getText().toString().trim();
        String valueValue = value.getText().toString().trim();
        //Comprobar si ha informado la clave, de lo contrario se retorna
        if ("".equals(keyValue)){
            showMessage(R.string.no_key);
            return;
        }
        //Comprobar si ha informado el valor, de lo contrario se retorna
        if ("".equals(valueValue)){
            showMessage(R.string.no_value);
            return;
        }
        //Salvar preferencias
        SharedPreferences preferences = null;

        if (!"".equals(blockValue)){
            preferences = getSharedPreferences(blockValue, 0);
        }
        else{
            preferences = getPreferences(0);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(keyValue, valueValue);

        //Guardar el trabajo
        editor.commit();
    }

    private void retrieve() {
        String blockValue = block.getText().toString().trim();
        String keyValue = key.getText().toString().trim();

        //Comprobar si ha informado la clave, de lo contrario se retorna
        if ("".equals(keyValue)){
            showMessage(R.string.no_key);
            return;
        }
        SharedPreferences preferences = null;

        if (!"".equals(blockValue)){
            preferences = getSharedPreferences(blockValue, 0);
        }
        else {
            preferences = getPreferences(0);
        }

        //Obtenemos el valor, y si no existe mostramos el valor por defecto
        String valueValue = preferences.getString(keyValue, getResources().getString(R.string.default_value));
        //Lo asignamos a la casa de texto
        value.setText(valueValue);
    }

    private void showMessage(int message) {
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(message);
        int duracion = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duracion);
        toast.show();
    }


}
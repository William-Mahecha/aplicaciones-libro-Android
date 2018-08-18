package com.example.geralt.ficheros;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ExternalFile extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file);
        Button btn = findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        btn = findViewById(R.id.load);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
    }

    protected void save() {
        EditText editor = findViewById(R.id.editor);
        EditText filename = findViewById(R.id.filename);
        OutputStreamWriter out;
        try {
            out = new OutputStreamWriter(openFileOutput(filename.getText().toString(), 0));
            out.write(editor.getText().toString());
            out.flush();
            out.close();
            showMessage("Se ha grabado el documento");
        }catch (Throwable t){
            showMessage("Error: " + t.getLocalizedMessage());
        }

        /*Codigo para guardar los archivos en la memoria SD del celular

        protected void save() {
        EditText editor = findViewById(R.id.editor);
        EditText filename = findViewById(R.id.filename);
        OutputStreamWriter out;

        try {
            File f = Environment.getExternalStorageDirectory();
            FileOutputStream fos = new FileOutputStream(new File(f, filename.getText().toString()));
            fos.write(editor.getText().toString().getBytes());
            fos.flush();
            fos.close();
            showMessage("Se ha grabado el documento");
        }catch (Throwable t){
            showMessage("Error: " + t.getLocalizedMessage());
        }

        */
    }

    protected void load() {
        EditText editor = findViewById(R.id.editor);
        EditText filename = findViewById(R.id.filename);
        InputStreamReader in;
        try {
            in = new InputStreamReader(openFileInput(filename.getText().toString()));
            BufferedReader buff = new BufferedReader(in);
            String strTemp = null;
            StringBuffer strBuff = new StringBuffer();
            while ((strTemp = buff.readLine()) !=null){
                strBuff.append(strTemp + "\n");
            }
            in.close();
            editor.setText(strBuff.toString());
            showMessage("Se ha leido el documento");
        }
        catch (Throwable t){
            showMessage("Error: " + t.getLocalizedMessage());
        }
    }

    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}

package com.example.geralt.holapersonalizado;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton = findViewById(R.id.btnHola);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Codigo a ejecutar cuando sea pulsado
                EditText editText = findViewById(R.id.editNombre);
                if ("".equals(editText.getText().toString().trim())){
                    //Mostrar el dialogo
                    //showAlert();
                    //Mostrar toast
                    showToast();
                    return;
                }

                String nombre = editText.getText().toString();
                String saludo = null;

                //Referencia al radioButton
                RadioGroup radioGroup = findViewById(R.id.radiogroup);
                if (R.id.radioSaludoSr == radioGroup.getCheckedRadioButtonId()){
                    //Para el señor
                    saludo = getResources().getString(R.string.saludoSr).toLowerCase();
                }else{
                    saludo = getResources().getString(R.string.saludoSra).toLowerCase();
                }

                saludo = getResources().getString(R.string.hello) + " " + saludo + " " + nombre;

                //Obtenemos la fecha y hora
                CheckBox timeCheckBox = findViewById(R.id.checkBox);
                if (timeCheckBox.isChecked()){
                    //Obtenemos la fecha actual y lo guardamos en date
                    DatePicker date = findViewById(R.id.datePicker);
                    String dateToShow = date.getDayOfMonth() + "/" + (date.getMonth()+1) + "/" + date.getYear();

                    //Obtenemos la hora actual y la guardamos en time
                    TimePicker time = findViewById(R.id.timePicker);
                    dateToShow +=" " + time.getCurrentHour() + ":" + time.getCurrentMinute();
                    saludo += " " + dateToShow;
                }
                //Salida del saludo
                /* TextView salida = findViewById(R.id.salida);
                salida.setText(saludo);
                */

                Intent intent = new Intent(MainActivity.this, Saludation.class);
                intent.putExtra("saludo", saludo);
                startActivity(intent);
            }

        });

        //Mostrar o no las fechas
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int visible = b?View.VISIBLE:View.GONE;
                View view = findViewById(R.id.timePicker);
                view.setVisibility(visible);
                view = findViewById(R.id.datePicker);
                view.setVisibility(visible);
            }
        });
    }

    protected void showToast() {
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(R.string.noNameMsg);
        int duracion = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duracion);
        toast.show();

        //Para que el codigo quede mas compacto
        // Toast.makeText(getApplicationContext(),getResources().getString(R.string.noNameMsg), Toast.LENGTH_SHORT).show();
    }

    protected void showAlert() {
        CharSequence text = getResources().getString(R.string.noNameMsg);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(text);
        alert.setPositiveButton(android.R.string.ok, null);
        alert.show();

        //Para que el codigo quede mas compacto
        // new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.noNameMsg)).setPositiveButton(android.R.string.ok, null).show();
    }


}

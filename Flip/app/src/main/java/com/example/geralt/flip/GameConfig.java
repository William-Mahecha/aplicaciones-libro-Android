package com.example.geralt.flip;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class GameConfig extends AppCompatActivity {

    private static final int ACTION_PLAY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //boton de inicio de partida
        Button button = findViewById(R.id.startBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay();
            }
        });

        //Control de numero de celdas horizontales
        final SeekBar xCeldas = findViewById(R.id.celdasX);
        xCeldas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                actualizarXCeldas(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        actualizarXCeldas(xCeldas.getProgress());


        //Control de numero de celdas verticales
        SeekBar yCeldas = findViewById(R.id.celdasY);
        yCeldas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                actualizarYCeldas(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        actualizarYCeldas(yCeldas.getProgress());


        //Control de tramas color
        SeekBar colores = findViewById(R.id.celdasColor);
        colores.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                actualizarColores(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        actualizarColores(colores.getProgress());
    }

    private void actualizarColores(int progress) {
        TextView tv = findViewById(R.id.txtCeldasColor);
        tv.setText(getString(R.string.num_colores) + (progress+2));
    }

    private void actualizarYCeldas(int progress) {
        TextView tv = findViewById(R.id.txtCeldasY);
        tv.setText(getString(R.string.num_elem_y) + (progress+3));
    }

    private void actualizarXCeldas(int progress) {
        TextView tv = findViewById(R.id.txtCeldasX);
        tv.setText(getString(R.string.num_elem_x) + (progress+3));
    }

    private void startPlay() {
        Intent intent = new Intent(this, GameField.class);
        //Configurar la partida
        SeekBar sb = findViewById(R.id.celdasX);
        intent.putExtra("xtiles", sb.getProgress());
        sb = findViewById(R.id.celdasY);
        intent.putExtra("ytiles", sb.getProgress());

        sb = findViewById(R.id.celdasColor);
        intent.putExtra("numColores", sb.getProgress());

        RadioButton r = findViewById(R.id.radioButtonC);
        intent.putExtra("title", r.isChecked()?"C":"N");

        //Control de sonido
        CheckBox chSound = findViewById(R.id.checkBoxSound);
        intent.putExtra("hasSound", chSound.isChecked());
        //Control de la vibracion
        CheckBox chVib = findViewById(R.id.checkBoxVibrate);
        intent.putExtra("hasVibration", chVib.isChecked());
        //Comenzar la activity
        startActivityForResult(intent, ACTION_PLAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ACTION_PLAY:
                    new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.fin_juego_pulsaciones) + data.getIntExtra("clicks", 0) + getResources().getString(R.string.fin_juego_tiempo)).setPositiveButton(android.R.string.ok, null).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_player:
                showPlayer();
                return true;

            case R.id.m_howto:
                showHowTo();
                return true;

            case R.id.m_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }

    private void showHowTo() {
        Intent i = new Intent(this, HowTo.class);
        startActivity(i);
    }

    private void showPlayer() {
    }

}

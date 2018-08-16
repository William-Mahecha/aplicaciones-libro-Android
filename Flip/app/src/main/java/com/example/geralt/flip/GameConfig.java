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

    private static final int ACTION_PLAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //boton de inicio de partida
        Button btn = findViewById(R.id.startBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay();
            }
        });

        //Control de numero de celdas horizontales
        final SeekBar xTiles = findViewById(R.id.seekBarX);
        xTiles.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateXTiles(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        updateXTiles(xTiles.getProgress());

        //Control de numero de celdas verticales
        SeekBar yTiles = findViewById(R.id.seekBarY);
        yTiles.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateYTiles(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        updateYTiles(yTiles.getProgress());

        //Control de tramas color
        SeekBar colors = findViewById(R.id.seekBarColors);
        colors.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColors(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        updateColors(colors.getProgress());
    }

    private void startPlay() {
        Intent intent = new Intent(this, GameField.class);
        //Configurar la partida
        SeekBar sb = findViewById(R.id.seekBarX);
        intent.putExtra("xtiles", sb.getProgress());
        sb = findViewById(R.id.seekBarY);
        intent.putExtra("ytiles", sb.getProgress());

        sb = findViewById(R.id.seekBarColors);
        intent.putExtra("numcolors", sb.getProgress());

        RadioButton r = findViewById(R.id.radioButtonC);
        intent.putExtra("tile", r.isChecked()?"C":"N");

        //Control de sonido
        CheckBox chSound = findViewById(R.id.checkBoxSound);
        intent.putExtra("hasSound", chSound.isChecked());
        //Control de la vibracion
        CheckBox chVib = findViewById(R.id.checkBoxVibrate);
        intent.putExtra("hasVibration", chVib.isChecked());
        //Comenzar la activity
        startActivityForResult(intent, ACTION_PLAY);
    }

    private void updateXTiles(int progress) {
        TextView tv = findViewById(R.id.seekBarXtxt);
        tv.setText(getString(R.string.num_elem_x) + (progress+3));
    }

    private void updateYTiles(int progress) {
        TextView tv = findViewById(R.id.seekBarYtxt);
        tv.setText(getString(R.string.num_elem_y) + (progress+3));
    }

    private void updateColors(int progress) {
        TextView tv = findViewById(R.id.seekBarColorstxt);
        tv.setText(getString(R.string.num_colores) + (progress+2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_player:
                showPlayer();
                return true;
            case R.id.m_about:
                showAbout();
                return true;
            case R.id.m_howto:
                showHowTo();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ACTION_PLAY:
                    new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.game_end_1)
                            + data.getIntExtra("clicks", 0) + getResources().getString(R.string.game_end_2)).
                            setPositiveButton(android.R.string.ok, null).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

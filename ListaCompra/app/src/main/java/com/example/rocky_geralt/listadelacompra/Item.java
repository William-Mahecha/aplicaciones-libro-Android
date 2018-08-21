package com.example.rocky_geralt.listadelacompra;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class Item extends Activity {

    //Referencias a elementos de pantalla
    TextView item = null;
    TextView place = null;
    TextView price = null;
    TextView importance = null;

    //Identificador de entrada
    Long rowId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        //Obtencion de extras, identificador y accion
        Bundle extras = getIntent().getExtras();
        rowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(DataBaseHelper.SL_ID);
        if (rowId == null){
            rowId = extras != null ? extras.getLong(DataBaseHelper.SL_ID) : null;
        }
        //Es solo para visualizar?
        if (extras != null && extras.getInt("accion" ) == ItemList.SHOW_ITEM){
            setContentView(R.layout.detail_item);
        }
        else{
            setContentView(R.layout.new_item);
            //Boton salvar
            Button saveBtn = findViewById(R.id.add);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_OK);
                    saveData();
                    finish();
                }
            });
        }

        //Identificador visible o no
        TableRow tr = findViewById(R.id.idRow);
        if (rowId != null){
            tr.setVisibility(View.VISIBLE);
            populateFieldsFromDB();
        }else{
            tr.setVisibility(View.GONE);
        }
    }

    private void populateFieldsFromDB() {
        Cursor c = ItemList.mDbHelper.getItem(rowId.intValue());
        startManagingCursor(c);
        c.moveToFirst();
        //Diferentes maneras de obtener los datos del cursor
        //Mediante nombre de columna y lanza excepcion si no existe
        item.setText(c.getString(c.getColumnIndexOrThrow(DataBaseHelper.SL_ITEM)));
        //Mediante nombre de columna y devuelve -1 si no existe
        place.setText(c.getString(c.getColumnIndex(DataBaseHelper.SL_PLACE)));
        //Mediante posicion del campo en el cursor
        price.setText(Float.toString(c.getInt(2)));
        importance.setText(Integer.toString(c.getInt(3)));
        TextView id = findViewById(R.id.identificador);
        id.setText(Integer.toString(c.getInt(4)));
    }

    protected void saveData() {
        //Obtener datos
        String itemText = item.getText().toString();
        String placeText = place.getText().toString();
        String priceText = price.getText().toString();
        String importanceText = importance.getText().toString();
        if (rowId == null){
            //Insertar
            ItemList.mDbHelper.insertItem(itemText, placeText, Float.parseFloat(priceText), Integer.parseInt(importanceText));
        }else{
            //Actualizar
            TextView tv = findViewById(R.id.identificador);
            String ident = tv.getText().toString();
            ItemList.mDbHelper.updateItem(Integer.parseInt(ident), itemText, placeText, Float.parseFloat(priceText), Integer.parseInt(importanceText));
        }
    }
}
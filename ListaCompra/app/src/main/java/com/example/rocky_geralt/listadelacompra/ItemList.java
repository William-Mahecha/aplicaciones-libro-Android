package com.example.rocky_geralt.listadelacompra;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ItemList extends ListActivity{

    //Acciones
    public static final int NEW_ITEM = 1;
    public static final int EDIT_ITEM = 2;
    public static final int SHOW_ITEM = 3;

    //Elemento seleccionado
    private long lastRowSelected = 0;
    public static DataBaseHelper mDbHelper = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  Abrir la base de datos
        mDbHelper = new DataBaseHelper(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
    }

    private void fillData() {
        Cursor itemCursor = null;
        itemCursor = mDbHelper.getItems();
        startManagingCursor(itemCursor);
        //Array de campos del cursor
        String[] from = new String[]{DataBaseHelper.SL_PLACE, DataBaseHelper.SL_ITEM, DataBaseHelper.SL_IMPORTANCE, DataBaseHelper.SL_ID};
        //Array para indicar los elementos que mostraran la informacion
        int[] to = new int[]{R.id.row_item, R.id.row_place, R.id.row_importance};
        // Creacion del adaptador para mostrar la informacion
        ShopAdapter items = new ShopAdapter(this, R.layout.row_list, itemCursor, from, to);
        //Asignar adaptador a la lista
        setListAdapter(items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_item:
                Intent intent = new Intent(this, Item.class);
                startActivityForResult(intent, NEW_ITEM);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ShopAdapter extends SimpleCursorAdapter{
        private LayoutInflater mInflater;
        private Cursor cursor;

        public ShopAdapter(Context context, int layout, Cursor c, String[] from, int[] to){
            super(context, layout, c, from, to);
            cursor = c;
            cursor.moveToFirst();
            mInflater = LayoutInflater.from(context);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Comprobamos si hay que iniciar el cursor o no
            if (cursor.getPosition()<0){
                cursor.moveToFirst();
            }else {
                cursor.moveToPosition(position);
            }
            //Obtencion de la vista de la linea de la tabla
            View row = mInflater.inflate(R.layout.row_list, null);
            //Rellenamos datos
            TextView place = row.findViewById(R.id.row_place);
            TextView item = row.findViewById(R.id.row_item);
            place.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.SL_PLACE)));
            int importance = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.SL_IMPORTANCE));
            int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.SL_ID));
            item.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.SL_ITEM)));

            //Dependiendo de la importancia, se muestran distintos iconos
            ImageView icon = row.findViewById(R.id.row_importance);
            icon.setTag(new Integer(rowId));
            switch (importance){
                case 1:
                    icon.setImageResource(R.drawable.icon1);
                    break;
                case 2:
                    icon.setImageResource(R.drawable.icon2);
                    break;
                default:
                    icon.setImageResource(R.drawable.icon3);
                    break;
            }
            return row;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo delW = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Salvar identificador del elemento pulsado
        lastRowSelected = delW.id;
        //Comprobar el elemento seleccionado
        switch (item.getItemId()){
            case R.id.delete_item:
                //Preguntar si esta seguro de borrarlo
                new AlertDialog.Builder(this).setTitle(this.getString(R.string.alrtDelete)).setMessage(R.string.alrtDeleteEntry).setPositiveButton(android.R.string.ok,
                        new  AlertDialog.OnClickListener(){
                    public void onClick(DialogInterface dlg, int i){
                            deleteEntry();
                        }
                }).setNegativeButton(android.R.string.cancel, null).show();
                return true;
            case R.id.edit_item:
                //Nueva actividad con el identificador como parametro
                Intent i = new Intent(this, Item.class);
                i.putExtra(DataBaseHelper.SL_ID, lastRowSelected);
                startActivityForResult(i, EDIT_ITEM);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteEntry() {
        mDbHelper.delete(lastRowSelected);
        fillData();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(this, Item.class);
        i.putExtra(DataBaseHelper.SL_ID, id);
        i.putExtra("accion", SHOW_ITEM);
        startActivityForResult(i, SHOW_ITEM);
    }
}

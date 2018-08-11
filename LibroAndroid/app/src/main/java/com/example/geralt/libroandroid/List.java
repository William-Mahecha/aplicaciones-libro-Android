package com.example.geralt.libroandroid;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] godos = getResources().getStringArray(R.array.REYES_GODOS);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.row, godos));
        ListView lv =getListView();
        lv.setTextFilterEnabled(true);
    }
}

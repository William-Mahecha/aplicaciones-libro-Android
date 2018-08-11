package com.example.geralt.libroandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Grid extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
    }

    private class ImageAdapter extends BaseAdapter{

        private Context mContext;

        public ImageAdapter(Context c){
            mContext = c;
        }
        public int getCount(){
            return mThumbIds.length;
        }
        public Object getItem(int position){
            return null;
        }
        public long getItemId(int position){
            return 0;
        }

        //Nueva imagen por cada referencia del adaptador
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null){ //Se intenta reciclar
                imageView = new ImageView(Grid.this);
                imageView.setLayoutParams(new GridView.LayoutParams(85,85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            }else{
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        //imagenes
        private Integer[] mThumbIds = {
                R.drawable.tab_absolute_not_selected,
                R.drawable.tab_absolute_selected,
                R.drawable.tab_linear_selected,
                R.drawable.tab_list_not_selected,
                R.drawable.tab_list_selected
        };
    }
}

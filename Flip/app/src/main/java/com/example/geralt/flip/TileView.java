package com.example.geralt.flip;

import android.content.Context;
import android.widget.Button;

public class TileView extends android.support.v7.widget.AppCompatButton {
    //Coordenas
    public int x = 0;
    public int y = 0;
    //trama a mostrar
    private int index = 0;
    //Maximo trama
    private int topElements = 0;

    public TileView(Context context, int x, int y, int topElements, int index, int background){
        super(context);
        this.x = x; //Coordenada X
        this.y = y; //Coordenada Y
        this.topElements = topElements; //Maximo de tramas
        this.index = index; //Indice de trama
        this.setBackgroundResource(background);
    }

    public int getNewIndex(){
        index++;
        //Controlar si necesitamos volver a comenzar el ciclo de tramas
        if (index == topElements)index = 0;
        return index;
    }
}

package com.domination.game.entities;

/**
 * Created by marcin on 27.12.15.
 */
public class Cell {

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
    private float x;
    private float y;

    float getX(){
        return x;
    }
    float getY(){
        return y;
    }
}

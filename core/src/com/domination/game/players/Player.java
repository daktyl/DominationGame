package com.domination.game.players;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Mrugi on 2015-12-28.
 */
public abstract class Player extends Thread{
    protected Color color;
    Player(Color color){
        this.color =color;
    }

    public Color getColor() {
        return color;
    }
}

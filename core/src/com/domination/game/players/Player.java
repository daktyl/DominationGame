package com.domination.game.players;

import com.badlogic.gdx.graphics.Color;

public abstract class Player extends Thread {
    protected Color color;
    protected String name;

    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getPlayerName() {
        return name;
    }
}

package com.domination.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    public SpriteBatch batch;
    protected boolean isBroken;

    public Entity(SpriteBatch batch) {
        isBroken = false;
        this.batch = batch;
    }

    public abstract void draw();

    public abstract void update();

    public boolean isBroken() {
        return isBroken;
    }

}

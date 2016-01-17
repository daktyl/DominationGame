package com.domination.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicalEntity extends Entity {
    public Sprite sprite;


    public GraphicalEntity(Texture texture, SpriteBatch batch) {
        super(batch);
        sprite = new Sprite(texture);
    }

    public GraphicalEntity(Texture texture, int startX, int startY, int width, int height, SpriteBatch batch) {
        super(batch);
        sprite = new Sprite(texture, startX, startY, width, height);
    }

    @Override
    public void draw() {
        sprite.draw(batch);
    }

    @Override
    public void update() {
    } // Override this method in children classes if needed

    public float getScaledWidth() {
        return sprite.getWidth() * sprite.getScaleX();
    }

    public float getScaledHeight() {
        return sprite.getHeight() * sprite.getScaleY();
    }

    public float getCenterX() {
        return sprite.getX() + getScaledWidth() / 2;
    }

    public float getCenterY() {
        return sprite.getY() + getScaledHeight() / 2;
    }

    public void setPositionCenter(float positionX, float positionY) {
        sprite.setPosition(positionX - getScaledWidth() / 2, positionY - getScaledHeight() / 2);
    }
}

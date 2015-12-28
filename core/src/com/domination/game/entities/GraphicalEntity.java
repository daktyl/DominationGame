package com.domination.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicalEntity extends Entity {
    protected Sprite sprite;


    public GraphicalEntity(Texture texture, SpriteBatch batch) {
        super(batch);
        sprite = new Sprite(texture);
    }

    public GraphicalEntity(Texture texture, int startX, int startY, int width, int height, SpriteBatch batch) {
        super(batch);
        sprite = new Sprite(texture,startX,startY,width,height);
    }

    @Override
    public void draw() {
        sprite.draw(batch);
    }

    @Override
    public void update() {} // Override this method in children classes if needed

    public float getX() { return sprite.getX(); }

    public float getY() { return sprite.getY(); }

    public float getWidth() { return sprite.getWidth(); }

    public float getHeight() { return sprite.getHeight(); }

    public float getCenterX() { return (sprite.getX() + sprite.getWidth()) / 2; }

    public float getCenterY() { return (sprite.getY() + sprite.getHeight()) / 2; }
}

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
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void update() {} // Override this method in children classes if needed
}

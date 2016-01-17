package com.domination.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextEntity extends Entity {
    public final Label label;

    public TextEntity(String text, SpriteBatch batch) {
        super(batch);
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        label = new Label(text, labelStyle);
    }

    public TextEntity(String text, SpriteBatch batch, int posX, int posY) {
        super(batch);
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        label = new Label(text, labelStyle);
        label.setPosition(posX, posY);
    }

    public TextEntity(String text, BitmapFont bitmapFont, SpriteBatch batch) {
        super(batch);
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.BLACK);
        label = new Label(text, labelStyle);
    }

    public TextEntity(String text, BitmapFont bitmapFont, SpriteBatch batch, int posX, int posY) {
        super(batch);
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.BLACK);
        label = new Label(text, labelStyle);
        label.setPosition(posX, posY);
    }

    @Override
    public void draw() {
        label.draw(batch, 255);
    }

    @Override
    public void update() {
    } // Override this method in children classes if needed
}

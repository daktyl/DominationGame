package com.domination.game.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonEntity extends Entity {

    public TextButton button;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    public ButtonEntity(String label, SpriteBatch batch) {
        super(batch);
        textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.up =
    }

    @Override
    public void draw() {
        button.draw(batch,255);
    }

    @Override
    public void update() {} // Override this method in children classes if needed
}

package com.domination.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonEntity extends Entity {
    public Button button;

    public ButtonEntity(Drawable up, Drawable down, Drawable checked, SpriteBatch batch) {
        super(batch);
        button = new Button(up, down, checked);
    }

    @Override
    public void draw() {
        button.draw(batch,255);
    }

    @Override
    public void update() {} // Override this method in children classes if needed
}

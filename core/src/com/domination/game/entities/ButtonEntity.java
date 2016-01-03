package com.domination.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ButtonEntity extends Entity {
    public TextButton button;

    public ButtonEntity(String text,SpriteBatch batch) {
        super(batch);
        Skin skin = new Skin();
        button = new TextButton(text,skin);
    }

    @Override
    public void draw() {
        button.draw(batch,255);
    }

    @Override
    public void update() {} // Override this method in children classes if needed

    public void addListener(ClickListener listener){
        button.addListener(listener);
    }
}

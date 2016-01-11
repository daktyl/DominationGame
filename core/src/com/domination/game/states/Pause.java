package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.domination.game.Game;
import com.domination.game.entities.ButtonEntity;
import com.domination.game.entities.Cell;

/**
 * Created by Mrugi on 2016-01-07.
 */
public class Pause extends GameState {
    public Pause(Game game, SpriteBatch batch) {
        super(game, batch);
    }
    @Override
    public void init() {
        setDefaultBackground();
        for(int i=0;i<5;i++)
            entityManager.add(new Cell(null,Gdx.graphics.getWidth()*(float)Math.random(),Gdx.graphics.getHeight()*(float)Math.random(),batch));

        ButtonEntity buttonEntity;
        buttonEntity = new ButtonEntity("Resume",80,Gdx.graphics.getHeight()-200, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popGameState();
            }
        });
        entityManager.add(buttonEntity);

        buttonEntity = new ButtonEntity("Exit",buttonEntity.getX(),buttonEntity.getY()-buttonEntity.getHeight()-20, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popGameState();
                game.popGameState();
            }
        });
        entityManager.add(buttonEntity);


    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.ESCAPE)
            game.popGameState();
        return true;
    }
}

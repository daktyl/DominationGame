package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.domination.game.Game;
import com.domination.game.entities.ButtonEntity;
import com.domination.game.entities.Cell;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Mrugi on 2016-01-07.
 */
public class Pause extends GameState {
    GameState gameState;
    public Pause(Game game, GameState gameState, SpriteBatch batch) {
        super(game, batch);
        Timer.instance().stop();
        this.gameState = gameState;
    }
    @Override
    public void init() {
        ButtonEntity buttonEntity;
        buttonEntity = new ButtonEntity("Resume",80,Gdx.graphics.getHeight()-200, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Resume();
            }
        });
        entityManager.add(buttonEntity);

        buttonEntity = new ButtonEntity("Exit",buttonEntity.getX(),buttonEntity.getY()-buttonEntity.getHeight()-20, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Resume();
                game.popGameState();
            }
        });
        entityManager.add(buttonEntity);


    }

    @Override
    public void draw() {
        gameState.draw();
        super.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.ESCAPE)
            Resume();
        return true;
    }
    private void Resume(){
        Timer.instance().start();
        game.popGameState();
    }
}

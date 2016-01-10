package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.domination.game.Game;
import com.domination.game.entities.ButtonEntity;

/**
 * Created by Mrugi on 2016-01-07.
 */
public class Pause extends GameState {
    public Pause(Game game, SpriteBatch batch) {
        super(game, batch);
    }
    ButtonEntity buttonEntity;
    @Override
    public void init() {
        setDefaultBackground();

        buttonEntity = new ButtonEntity("przycisk",500,400, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button","clicked");
            }
        });
        entityManager.add(buttonEntity);

        buttonEntity = new ButtonEntity("przycisk2",500,150, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button","clicked2");
            }
        });
        entityManager.add(buttonEntity);
    }

}

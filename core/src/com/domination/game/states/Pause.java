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
    @Override
    public void init() {
        setDefaultBackground();

        ButtonEntity buttonEntity,buttonEntity1,buttonEntity2;
        buttonEntity = new ButtonEntity("przycisk",500,400, batch);
        buttonEntity.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button","clicked");
            }
        });
        entityManager.add(buttonEntity);

        buttonEntity1 = new ButtonEntity("przycisk2",500,200, batch);
        buttonEntity1.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button","clicked2");
            }
        });
        entityManager.add(buttonEntity1);

        buttonEntity2 = new ButtonEntity("przycisk2",500,100, batch);
        buttonEntity2.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("button","clicked3");
            }
        });
        entityManager.add(buttonEntity2);

        buttonEntity.setNext(buttonEntity1);
        buttonEntity.setPrev(buttonEntity2);

        buttonEntity1.setNext(buttonEntity2);
        buttonEntity1.setPrev(buttonEntity);

        buttonEntity2.setNext(buttonEntity);
        buttonEntity2.setPrev(buttonEntity1);

        buttonEntity.setActive();
    }

}

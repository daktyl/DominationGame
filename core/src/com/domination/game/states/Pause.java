package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.domination.game.Game;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.ButtonEntity;

/**
 * Created by Mrugi on 2015-12-28.
 */
public class Pause extends GameState {
    public Pause(Game game, SpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void init() {
        Texture texture = ResourceManager.getInstance().get("TestTexture");
        Drawable drawable =  new Image(texture).getDrawable();
        ButtonEntity buttonEntity =new ButtonEntity("Resume",batch);
        entityManager.add(buttonEntity);

        buttonEntity.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.popGameState();
                Gdx.app.log("clicked","Yes");
            }
        });
        entityManager.add(buttonEntity);
    }
}

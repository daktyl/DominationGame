package com.domination.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.domination.game.Game;
import com.domination.game.entities.ButtonEntity;
import com.domination.game.entities.TextEntity;

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
        buttonEntity = new ButtonEntity("przycisk",batch);
        buttonEntity.button.setPosition(500,500);
        entityManager.add(buttonEntity);
    }
}

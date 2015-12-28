package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.AI.AI;
import com.domination.game.Game;
import com.domination.game.entities.Cell;

public class GameplayState extends GameState{

    public GameplayState(Game game, SpriteBatch spriteBatch) {
        super(game, spriteBatch);
    }

    @Override
    public void init() {
        Texture texture = new Texture("badlogic.jpg");
        entityManager.add(new Cell(new AI(),250,Gdx.graphics.getHeight()-250,texture,this.spriteBatch));
        entityManager.add(new Cell(new AI(),450,Gdx.graphics.getHeight()-123,texture,this.spriteBatch));
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.debug("KeyDown", Integer.valueOf(keycode).toString());
        switch (keycode) {
            case Input.Keys.ESCAPE:
                game.popGameState();
                Gdx.app.debug("KeyDown", "Esc");
                return true;
        }
        return false;
    }
}

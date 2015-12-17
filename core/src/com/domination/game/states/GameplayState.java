package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.domination.game.Game;

public class GameplayState extends GameState{

    public GameplayState(Game game) {
        super(game);
    }

    @Override
    public void init() {}

    @Override
    public void draw() {
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.debug("KeyDown", Integer.valueOf(keycode).toString());
        switch (keycode)
        {
            case Input.Keys.ESCAPE:
                game.popGameState();
                Gdx.app.debug("KeyDown", "Esc");
                return true;

        }
        return false;

    }
}

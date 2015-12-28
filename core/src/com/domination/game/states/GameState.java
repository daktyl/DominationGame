package com.domination.game.states;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.EntityManager;

public abstract class GameState implements InputProcessor {
    protected Game game;
    protected SpriteBatch batch;
    protected EntityManager entityManager = new EntityManager();

    public GameState(Game game, SpriteBatch batch){
        this.game = game;
        this.batch = batch;
    }
    public abstract void init();

    public void update() { entityManager.updateAll(); }

    public void cleanUp() { entityManager.removeAll(); }

    public void draw() { entityManager.drawAll(); }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

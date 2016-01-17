package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.EntityManager;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.GraphicalEntity;

public abstract class GameState implements InputProcessor {
    final Game game;
    final SpriteBatch batch;
    final EntityManager entityManager = new EntityManager();

    GameState(Game game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    public abstract void init();

    public void update() {
        entityManager.updateAll();
    }

    public void cleanUp() {
        entityManager.removeAll();
    }

    public void draw() {
        batch.begin();
        entityManager.drawAll();
        batch.end();
    }

    void setDefaultBackground() {
        GraphicalEntity background = new GraphicalEntity((Texture) ResourceManager.getInstance().get("Background"), batch);
        background.sprite.setScale(Gdx.graphics.getWidth() / background.sprite.getWidth(), Gdx.graphics.getHeight() / background.sprite.getHeight());
        background.sprite.setX(-background.sprite.getWidth() / 2 + Gdx.graphics.getWidth() / 2);
        background.sprite.setY(-background.sprite.getHeight() / 2 + Gdx.graphics.getHeight() / 2);
        entityManager.add(background);
    }

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

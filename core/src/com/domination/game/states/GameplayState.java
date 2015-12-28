package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.Player;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;

public class GameplayState extends GameState{

    Cell cell1;
    Cell cell2;

    public GameplayState(Game game, SpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void init() {
        ResourceManager.getInstance().add("TestTexture",new Texture("badlogic.jpg"));
        cell1 = new Cell(new Player(),250,Gdx.graphics.getHeight()-250,this.batch);
        cell2 = new Cell(new Player(),450,Gdx.graphics.getHeight()-123,this.batch);
        Bacteria bacteria = new Bacteria(new Player(), cell1, cell2, 15, batch);
        entityManager.add(cell1);
        entityManager.add(cell2);
        entityManager.add(bacteria);
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.debug("KeyDown", Integer.valueOf(keycode).toString());
        switch (keycode) {
            case Input.Keys.ESCAPE:
                game.popGameState();
                Gdx.app.debug("KeyDown", "Esc");
                return true;
            case Input.Keys.SPACE:
                Bacteria bacteria = new Bacteria(new Player(), cell1, cell2, 15, batch);
                entityManager.add(bacteria);
        }
        return false;
    }
}

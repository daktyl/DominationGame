package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.AI.AI;
import com.domination.game.Game;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;

public class GameplayState extends GameState{

    public GameplayState(Game game, SpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void init() {
        ResourceManager.getInstance().add("TestTexture",new Texture("badlogic.jpg"));
        Cell cell1 = new Cell(new AI(),250,Gdx.graphics.getHeight()-250,this.batch);
        Cell cell2 = new Cell(new AI(),450,Gdx.graphics.getHeight()-123,this.batch);
        entityManager.add(cell1);
        entityManager.add(cell2);
        entityManager.add(new Bacteria(null, batch, cell1, cell2, 100));
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

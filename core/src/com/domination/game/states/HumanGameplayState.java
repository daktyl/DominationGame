package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.entities.Cell;
import com.domination.game.players.HumanPlayer;


public class HumanGameplayState extends GameplayState {

    private Cell currentCell = null;
    private HumanPlayer human;
    public HumanGameplayState(Game game, SpriteBatch batch, int AISetRight) {
        super(game, batch, -1, AISetRight);
    }

    @Override
    protected void setFirstPlayer() {
        human = new HumanPlayer(new Color(0.2f, 0.8f, 0.8f, 1.f));
        playerList.add(human);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            for (Cell cell : cellList) {
                if (currentCell == null && cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY) && cell.getPlayer() == human) {
                    cell.glow();
                    currentCell = cell;
                    break;
                } else if (currentCell == cell && cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY)) {
                    cell.dim();
                    currentCell = null;
                    break;
                } else if (cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY) && currentCell != null) {
                    sendBacteria(currentCell, cell, human);
                    currentCell.dim();
                    cell.dim();
                    currentCell = null;
                    break;
                }
            }
            return true;
        }
        return true;
    }
}

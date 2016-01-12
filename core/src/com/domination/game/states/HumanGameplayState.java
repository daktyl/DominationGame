package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.entities.Cell;
import com.domination.game.players.HumanPlayer;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.players.defaultAI;


public class HumanGameplayState extends GameplayState {

    public HumanGameplayState(Game game, SpriteBatch batch, int AISetRight) {
        super(game, batch, -1, AISetRight);
    }

    private Cell currentCell = null;
    private HumanPlayer human;

    @Override
    protected void setPlayers() {
        human = new HumanPlayer(new Color(0.2f, 0.8f, 0.8f, 1.f));
        playerList.add(human);
        switch(AISetRight){
            case(0):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case(1):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case(2):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case(3):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case(4):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case(5):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case(6):
                playerList.add(new defaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(screenX);
        System.out.println(Gdx.graphics.getHeight() - screenY);
        System.out.println();
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
                } else if (cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY) && currentCell !=null) {
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

package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.players.AI;
import com.domination.game.players.HumanPlayer;
import com.domination.game.players.Player;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.players.defaultAI;


/**
 * Created by macbook on 07.01.2016.
 */
public class HumanGameplayState extends GameplayState {

    public HumanGameplayState(Game game, SpriteBatch batch) {
        super(game, batch);
    }

    private Cell current_cell = null;
    private HumanPlayer human;

    @Override
    protected void setPlayers() {
        human=new HumanPlayer(Color.FIREBRICK);
        playerList.add(human);
        playerList.add(new HumanPlayer(Color.GREEN));
       // playerList.add(new defaultAI(new GameplayWrapper(this),Color.GREEN));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(screenX);
        System.out.println(Gdx.graphics.getHeight() - screenY);
        System.out.println();
        if (button == Input.Buttons.LEFT) {
            for (Cell cell : cellList) {
                if (current_cell == null && cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY) && cell.getPlayer() == human) {
                    cell.glow();
                    current_cell = cell;
                    break;
                } else if (current_cell == cell && cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY)) {
                    cell.dim();
                    current_cell = null;
                    break;
                } else if (cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY) && current_cell!=null) {
                    sendBacteria(current_cell, cell, human);
                    current_cell.dim();
                    cell.dim();
                    current_cell= null;
                    break;

                }
            }
            return true;
        }
            return true;
    }

//    @Override
//    public boolean mouseMoved(int screenX, int screenY) {
//        for(Cell c : cellList)
//            if(c.isOnCell(screenX,Gdx.graphics.getHeight()-screenY))
//                c.glow();
//            else
//                c.dim();
//        return true;
//    }

}

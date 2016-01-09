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
import com.domination.game.players.HumanPlayer;
import com.domination.game.players.Player;
import com.badlogic.gdx.graphics.Color;


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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(screenX);
        System.out.println(Gdx.graphics.getHeight() - screenY);
        System.out.println();
        if (button == Input.Buttons.LEFT) {
            for (Cell cell : cellList) {
                if (current_cell == null && cell.isOnCell(screenX, Gdx.graphics.getHeight() - screenY)) {
                    current_cell = cell;
                    cell.glow();
                    break;
                } else if (current_cell == cell) {
                    current_cell = null;
                    cell.dim();
                    break;
                } else {
                    sendBacteria(current_cell, cell, human);
                    current_cell = null;
                    cell.dim();
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

package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

public class FilipAI extends AI {


    public FilipAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color, "Filip AI");
    }

    @Override
    protected void implementation() {
        int  to = 0, from = 0, n, i, j;
        while (true) {
            try{
            Situation situation = gameplayWrapper.getCurrentSituation();
            cellList = situation.cellList;
            bacteriaList = situation.bacteriaList;
            n = situation.cellList.size();
            for (i = 0; i < n; i++) {
                if ((cellList.get(i).player != null) && (cellList.get(i).player != this)) {
                    to = i;
                    break;
                }
                }
            for (j = 0; j < n; j++ ) {
                if (cellList.get(j).player == this) {
                    if (cellList.get(j).bacteriaAmount > 10){
                        from = j;
                    break;
                }
                }
            }
            gameplayWrapper.sendBacteria(cellList.get(from), cellList.get(to), this);
            synchronized (this) {
                wait(1);
            }
        } catch (InterruptedException e) {
            Gdx.app.log(getPlayerName(), "Interrupted");
                }
        }
    }

    @Override
    public String getPlayerName() {
        return "FilipAI";
    }
}
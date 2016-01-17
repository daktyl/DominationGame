package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.Random;

public class FilipAI extends AI {

    Random random = new Random();

    public FilipAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color);
    }

    @Override
    protected void implementation() {
        int n,i;
        do {
            Situation situation = gameplayWrapper.getCurrentSituation();
            cellList = situation.cellList;
            bacteriaList = situation.bacteriaList;
            n = situation.cellList.size();
            for (i = 0; i <=n ; i++){
                if ((cellList.get(i).player != null) && (cellList.get(i).player != this)) {

                }
            }
        }
        while (true) {
            try {

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
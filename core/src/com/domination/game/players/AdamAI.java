package com.domination.game.players;

import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

public class AdamAI extends AI {
    public AdamAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color, "Adam");
    }

    @Override
    protected void implementation() {
        int max = 0;
        Situation situation = gameplayWrapper.getCurrentSituation();
        cellList = situation.cellList;
        bacteriaList = situation.bacteriaList;
        for (int k = 0; k < cellList.size(); k++) {
            if (cellList.get(k).player == this && cellList.get(k).bacteriaAmount >= 10) {
                for (int t = 0; t < cellList.size(); t++) {
                    if (cellList.get(t).player != this && cellList.get(t).bacteriaAmount < 5) {
                        gameplayWrapper.sendBacteria(cellList.get(k), cellList.get(t), this);
                    }
                }
            }

        }

        for (int i = 0; i < cellList.size(); i++) {
            if (cellList.get(i).player == this) {
                for (int j = 0; j < cellList.size(); j++) {
                    if (cellList.get(j).player == null && cellList.get(j).bacteriaAmount > max) {
                        max = i;
                    } else {
                        continue;
                    }
                }
                gameplayWrapper.sendBacteria(cellList.get(i), cellList.get(max), this);
            }
        }
    }
}

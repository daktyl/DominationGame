package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.Random;

public class ADAMAI extends AI {
    public ADAMAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color, "Adam");
    }

    @Override
    protected void implementation() {
        int from, to;
        int max = 0;
            Situation situation = gameplayWrapper.getCurrentSituation();
            cellList = situation.cellList;
            bacteriaList = situation.bacteriaList;
            for (int k = 0; k<cellList.size(); k++) {
                if(cellList.get(k).player==this && cellList.get(k).bacteriaAmount>=10)
                {
                    for (int t = 0; t<cellList.size(); t++) {
                        if(cellList.get(t).player!=this && cellList.get(t).bacteriaAmount<5)
                        {
                            gameplayWrapper.sendBacteria(cellList.get(k), cellList.get(t),this);
                        }
                    }
                }

            }

            for (int i = 0; i<cellList.size(); i++) {
                if(cellList.get(i).player == this) {
                    for (int j = 0; j<cellList.size(); j++)
                    {
                        if(cellList.get(j).player == null && cellList.get(j).bacteriaAmount > max)
                        {
                            max = i;
                        }
                        else
                        {
                            continue;
                        }
                    }
                    gameplayWrapper.sendBacteria(cellList.get(i), cellList.get(max), this);
                }
            }
    }

//    protected void implementation() {
//        int from, to, n = 0;
//        Situation situation = gameplayWrapper.getCurrentSituation();
//        cellList = situation.cellList;
//        bacteriaList = situation.bacteriaList;
//        n = bacteriaList.size();
//        int max = 0;
//                for (int c = 0; c < bacteriaList.size();c++)
//                {
//                    if(cellList.get(c).player == this)
//                    {
//                        gameplayWrapper.sendBacteria(cellList.get(c),cellList.get(1),this);
//                    }
//                }
//                for (int i = 0; i < n; i++) {
//                    if (bacteriaList.get(i).player == null) {
//                        for (int j = 0; j < n; j++) {
//                            if (cellList.get(j).player == this && cellList.get(j).bacteriaAmount > max) {
//                                max = j;
//                            } else {
//                                continue;
//                            }
//                            gameplayWrapper.sendBacteria(cellList.get(max), cellList.get(i), this);
//                        }
//                    }
//                }
//
//            }
        }






//        while (true) {
//            for (int c = 0; c < bacteriaList.size();c++)
//            {
//                if(cellList.get(c).player==this)
//                {
//                    gameplayWrapper.sendBacteria(cellList.get(c),cellList.get(3),this);
//                }
//            }
//            for (int i = 0; i < n; i++) {
//                if (bacteriaList.get(i).player == null) {
//                    for (int j = 0; j < n; j++) {
//                        if (cellList.get(j).player == this && cellList.get(j).bacteriaAmount > max) {
//                            max = j;
//                        } else {
//                            continue;
//                        }
//                        gameplayWrapper.sendBacteria(cellList.get(max), cellList.get(i), this);
//                    }
//                }
//            }

//        }

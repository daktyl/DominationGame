package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.Random;

public class defaultAI extends AI {

    Random random = new Random();

    public defaultAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color);
    }

    @Override
    protected void implementation() {
        int from,to;
        do {
            Situation situation = gameplayWrapper.getCurrentSituation();
            cellList = situation.cellList;
            bacteriaList = situation.bacteriaList;
            from = random.nextInt(cellList.size());
            to = random.nextInt(cellList.size());
        }while (!gameplayWrapper.sendBacteria(cellList.get(from),cellList.get(to),this));
        try  {
            synchronized (this) { wait(random.nextInt(5000)); }
        }
        catch (InterruptedException e){
            Gdx.app.log("Player","Interrupted");
        }
    }

    @Override
    public String getPlayerName() {
        return "defaultAI";
    }
}

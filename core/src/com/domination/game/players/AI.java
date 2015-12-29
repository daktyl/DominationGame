package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.ai_types.FakeCell;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.List;
import java.util.Random;

public class AI extends Player {
    GameplayWrapper gameplayWrapper;
    public AI(GameplayWrapper gameplayWrapper, Color color) {
        super(color);
        this.gameplayWrapper = gameplayWrapper;
    }

    List<FakeBacteria> bacteriaList;
    List<FakeCell> cellList;
    Random random = new Random();

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            Situation situation = gameplayWrapper.getCurrentSituation();
            cellList = situation.cellList;
            bacteriaList = situation.bacteriaList;
            int from = random.nextInt(cellList.size());
            int to = random.nextInt(cellList.size());
            gameplayWrapper.sendBacteria(cellList.get(from),cellList.get(to),this);
            try  {
                synchronized (this) { wait(random.nextInt(5000)); }
            }
            catch (InterruptedException e){
                Gdx.app.log("Player","Finished");
            }
        }
    }
}

package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.engine.GameWrapper;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;

import java.util.List;
import java.util.Random;

public class AI extends Player {
    GameWrapper gameWrapper;
    public AI(GameWrapper gameWrapper, Color color) {
        super(color);
        this.gameWrapper = gameWrapper;
    }

    List<Bacteria> bacteriaList;
    List<Cell> cellList; //TODO zmienić na AIBacteria  i AICell
    Random random = new Random();

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            this.bacteriaList = gameWrapper.getCurrentBacterias();
            this.cellList = gameWrapper.getCurrentCells();
            int from = random.nextInt(cellList.size());
            int to = random.nextInt(cellList.size());
            gameWrapper.sendBacteria(cellList.get(from),cellList.get(to),this);
            try  {
                synchronized (this) { wait(random.nextInt(5000)); }
            }
            catch (InterruptedException e){
                Gdx.app.log("Player","Finished");
            }
        }
    }
}

package com.domination.game.engine;

import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.players.Player;
import com.domination.game.states.GameplayState;

import java.util.List;

public class GameWrapper {
    private GameplayState gameplayState;
    private List<Cell> cellList;
    private List<Bacteria> bacteriaList;

    public GameWrapper(GameplayState gameplayState, List<Cell> cellList, List<Bacteria> bacteriaList) {
        this.gameplayState = gameplayState;
        this.bacteriaList = bacteriaList;
        this.cellList = cellList;
    }

    public Boolean sendBacteria(Cell from, Cell to, Player player) {
        return gameplayState.sendBacterias(from, to, player);
    }

    public List<Cell> getCurrentCells() {
        //TODO przekopiować do AIcells i AI bacterias i zwrócić
        return cellList;
    }

    public List<Bacteria> getCurrentBacterias(){
        return bacteriaList;
    }
}

package com.domination.game.engine;

import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.ai_types.FakeCell;
import com.domination.game.ai_types.Situation;
import com.domination.game.containers.BidiHashMap;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.players.Player;
import com.domination.game.states.GameplayState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameplayWrapper {
    private final GameplayState gameplayState;
    private final BidiHashMap<FakeCell, Cell> cellMap = new BidiHashMap<FakeCell, Cell>();
    private final BidiHashMap<FakeBacteria, Bacteria> bacteriaMap = new BidiHashMap<FakeBacteria, Bacteria>();

    private List<FakeCell> getFakeCellsList() {
        List<FakeCell> fakeCellList = new ArrayList<FakeCell>();
        for (Cell cell : gameplayState.cellList) {
            FakeCell fakeCell = new FakeCell(cell);
            fakeCellList.add(fakeCell);
            cellMap.put(fakeCell, cell);
        }
        return fakeCellList;
    }

    private List<FakeBacteria> getFakeBacteriaList() {
        List<FakeBacteria> fakeBacteriaList = new ArrayList<FakeBacteria>();
        List<Bacteria> copiedList=new ArrayList<Bacteria>(gameplayState.bacteriaList);
        Collections.copy(copiedList,gameplayState.bacteriaList);
        for (Bacteria bacteria : copiedList) {
            FakeBacteria fakeBacteria = new FakeBacteria(bacteria);
            // Find source and destination fakeBacteria
            FakeCell fakeSource = cellMap.getKey(bacteria.getSource());
            FakeCell fakeDestination = cellMap.getKey(bacteria.getDestination());
            // Add these to current fakeCell fields
            fakeBacteria.source = fakeSource;
            fakeBacteria.destination = fakeDestination;
            // Add fakeBacteria to the list and to the map
            fakeBacteriaList.add(fakeBacteria);
            bacteriaMap.put(fakeBacteria, bacteria);
        }
        return fakeBacteriaList;
    }

    private Cell resolve(FakeCell fakeCell) {
        return cellMap.get(fakeCell);
    }

    private Bacteria resolve(FakeBacteria fakeBacteria) {
        return bacteriaMap.get(fakeBacteria);
    }

    public GameplayWrapper(GameplayState gameplayState) {
        this.gameplayState = gameplayState;
    }

    public Boolean sendBacteria(FakeCell fakeSource, FakeCell fakeDestination, Player player) {
        Cell source = resolve(fakeSource);
        Cell destination = resolve(fakeDestination);
        if (source != destination) {
            return gameplayState.sendBacteria(source, destination, player);
        }
        else {
            return false;
        }
    }

    public Situation getCurrentSituation() {
        cellMap.clear();
        bacteriaMap.clear();
        List<FakeCell> fakeCellList = getFakeCellsList();
        List<FakeBacteria> fakeBacteriaList = getFakeBacteriaList();
        return new Situation(fakeCellList, fakeBacteriaList);
    }
}

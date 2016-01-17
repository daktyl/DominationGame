package com.domination.game.players.MarcinP_AI;

import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.ai_types.FakeCell;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.players.AI;
import com.domination.game.players.MarcinP_AI.entities.*;
import com.domination.game.players.MarcinP_AI.states.AIState;
import com.domination.game.players.MarcinP_AI.states.NormalState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MarcinP_AI extends AI {
    private AIState currentState;

    public List<Cell> friendlyCellList = new ArrayList<Cell>();
    public List<Cell> hostileCellList = new ArrayList<Cell>();
    public List<Cell> neutralCellList = new ArrayList<Cell>();
    public List<Cell> oppositeCellList = new ArrayList<Cell>();
    public List<Bacteria> friendlyBacteriaList = new ArrayList<Bacteria>();
    public List<Bacteria> hostileBacteriaList = new ArrayList<Bacteria>();

    public int friendlyPower;
    public float friendlyCenterX;
    public float friendlyCenterY;
    public int hostilePower;
    public float hostileCenterX;
    public float hostileCenterY;


    public MarcinP_AI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, Color.BLUE, "Marcin Pi");
        this.currentState = NormalState.getInstance();
    }

    @Override
    protected void implementation() {
        updateSituation();
        if (currentState != null){
            currentState.execute(this);
        }
    }

    private void updateSituation(){
        Situation situation = this.gameplayWrapper.getCurrentSituation();
        List<FakeCell> fakeCellList = situation.cellList;
        List<FakeBacteria> fakeBacteriaList = situation.bacteriaList;

        clearLists();
        for (FakeBacteria fakeBacteria : fakeBacteriaList){
            if (fakeBacteria.player == this)
                friendlyBacteriaList.add(new Bacteria(fakeBacteria, this));
            else
                hostileBacteriaList.add(new Bacteria(fakeBacteria, this));
        }

        // Useful game factors calculations
        friendlyPower=0; hostilePower=0;
        friendlyCenterX=0; friendlyCenterY=0;
        hostileCenterX=0; hostileCenterY=0;

        for (FakeCell fakeCell : fakeCellList) {
            if (fakeCell.player == this) {
                friendlyCellList.add(new FriendlyCell(fakeCell, this));
                friendlyPower += fakeCell.bacteriaAmount;
                friendlyCenterX += fakeCell.bacteriaAmount*fakeCell.centerX;
                friendlyCenterY += fakeCell.bacteriaAmount*fakeCell.centerY;
            } else if (fakeCell.player == null) {
                neutralCellList.add(new NeutralCell(fakeCell, this));
            } else {
                hostileCellList.add(new HostileCell(fakeCell, this));
                hostilePower += fakeCell.bacteriaAmount;
                hostileCenterX += fakeCell.bacteriaAmount*fakeCell.centerX;
                hostileCenterY += fakeCell.bacteriaAmount*fakeCell.centerY;
            }
        }
        friendlyCenterX /= friendlyPower;
        friendlyCenterY /= friendlyPower;
        hostileCenterX /= hostilePower;
        hostileCenterY /= hostilePower;

        for (Cell c : friendlyCellList) c.init();
        for (Cell c : hostileCellList) c.init();
        for (Cell c : neutralCellList) c.init();

        oppositeCellList.addAll(hostileCellList);
        oppositeCellList.addAll(neutralCellList);

        sortLists();

    }

    private void clearLists() {
        friendlyCellList.clear();
        hostileCellList.clear();
        neutralCellList.clear();
        oppositeCellList.clear();
        friendlyBacteriaList.clear();
        hostileBacteriaList.clear();
    }

    private void sortLists() {
        friendlyCellList.sort(new Comparator<Cell>() {
            @Override
            public int compare(Cell cell, Cell t1) {
                float distance1 = distanceBetweenPointAndCell(friendlyCenterX, friendlyCenterY,cell);
                float distance2 = distanceBetweenPointAndCell(friendlyCenterX, friendlyCenterY, t1);
                if (distance1<distance2) return -1;
                else if (distance1>distance2) return 1;
                else return 0;
            }
        });
        hostileCellList.sort(new Comparator<Cell>() {
            @Override
            public int compare(Cell cell, Cell t1) {
                if (cell.fakeCell.bacteriaAmount < t1.fakeCell.bacteriaAmount ) return -1;
                else if (cell.fakeCell.bacteriaAmount > t1.fakeCell.bacteriaAmount ) return 1;
                else return 0;
            }
        });
        oppositeCellList.sort(new Comparator<Cell>() {
            @Override
            public int compare(Cell cell, Cell t1) {
                float cell1Factor = (float) (Math.pow(cell.fakeCell.bacteriaAmount,2) + expectedDeltaTimeBeetweenPointAndCell(friendlyCenterX,friendlyCenterY,cell)/15);
                float cell2Factor = (float) (Math.pow(t1.fakeCell.bacteriaAmount,2) + expectedDeltaTimeBeetweenPointAndCell(friendlyCenterX,friendlyCenterY,t1)/15);
                if (cell.fakeCell.player != null) cell1Factor /= 1.2;
                if (t1.fakeCell.player != null) cell1Factor /= 1.2;

                if (cell1Factor < cell2Factor) return -1;
                else if (cell1Factor > cell2Factor) return 1;
                else return 0;
            }
        });
    }


    public float distanceBetweenCells(Cell cell1, Cell cell2){
        float distance = (float) Math.sqrt(Math.pow(cell1.fakeCell.centerX - cell2.fakeCell.centerX,2) + Math.pow(cell1.fakeCell.centerY - cell2.fakeCell.centerY ,2));
        return distance;
    }
    public float distanceBetweenPointAndCell(float x, float y, Cell cell2){
        float distance = (float) Math.sqrt(Math.pow(x - cell2.fakeCell.centerX,2) + Math.pow(y - cell2.fakeCell.centerY ,2));
        return distance;
    }

    public double expectedDeltaTimeBeetweenCells(Cell cell1, Cell cell2){
        double deltaTime = (distanceBetweenCells(cell1,cell2) / 100 * 1000); // seconds to miliseconds
        return deltaTime;
    }

    public double expectedDeltaTimeBeetweenPointAndCell(float x, float y, Cell cell2){
        double deltaTime = (distanceBetweenPointAndCell(x,y,cell2) / 100 * 1000); // seconds to miliseconds
        return deltaTime;
    }

    public boolean sendBacteria(Cell cell1, Cell cell2){
        return gameplayWrapper.sendBacteria(cell1.fakeCell,cell2.fakeCell,this);
    }

    public void changeState(AIState newState){
        if (currentState != null){
            currentState.enter(this);
        }
        currentState = newState;
        currentState.exit(this);
    }
}

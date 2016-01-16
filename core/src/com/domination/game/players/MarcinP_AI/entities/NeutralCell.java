package com.domination.game.players.MarcinP_AI.entities;

import com.domination.game.ai_types.FakeCell;
import com.domination.game.players.MarcinP_AI.MarcinP_AI;

public class NeutralCell extends Cell {


    public NeutralCell(FakeCell fakeCell, MarcinP_AI aiEngine) {
        super(fakeCell, aiEngine);
    }

    @Override
    protected void addBacteriasToLists() {
        for (Bacteria bacteria : aiEngine.hostileBacteriaList){
            if (bacteria.fakeBacteria.destination == this.fakeCell){
                closestIncomingFriendlyBacteriaList.add(bacteria); // keeps all my enemies as his friends
            }
        }
        for (Bacteria bacteria : aiEngine.friendlyBacteriaList){
            if (bacteria.fakeBacteria.destination == this.fakeCell){
                closestIncomingHostileBacteriaList.add(bacteria); // keeps me as his enemy
            }
        }
    }

    @Override
    protected void addCellsToLists() {
        closestHostileCellList.addAll(aiEngine.friendlyCellList);
        closestHostileCellList.remove(this);
        closestFriendlyCellList.addAll(aiEngine.hostileCellList);
        closestFriendlyCellList.remove(this);
        closestNeutralCellList.addAll(aiEngine.neutralCellList);
        closestNeutralCellList.remove(this);
    }

    @Override
    public int expectedBacteriaAmountAfterTime (double time) {
        int amount = this.fakeCell.bacteriaAmount;

        long currTime = System.currentTimeMillis();
        for (Bacteria bacteria : closestIncomingHostileBacteriaList){
            if (bacteria.fakeBacteria.endTime - currTime <= time ){
                amount -= bacteria.fakeBacteria.amount;
            }
        }
        for (Bacteria bacteria : closestIncomingFriendlyBacteriaList){
            if (bacteria.fakeBacteria.endTime - currTime <= time ){
                amount -= bacteria.fakeBacteria.amount;
            }
        }
        //not reproducing
        return amount;
    }

    @Override
    public int expectedFinalBacteriaAmount () {
        int amount = this.fakeCell.bacteriaAmount;
        for (Bacteria bacteria : closestIncomingHostileBacteriaList){
            amount -= bacteria.fakeBacteria.amount;
        }
        for (Bacteria bacteria : closestIncomingFriendlyBacteriaList){
            amount -= bacteria.fakeBacteria.amount;
        }
        return amount;
    }




}

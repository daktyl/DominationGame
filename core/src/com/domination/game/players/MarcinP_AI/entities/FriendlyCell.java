package com.domination.game.players.MarcinP_AI.entities;

import com.domination.game.ai_types.FakeCell;
import com.domination.game.players.MarcinP_AI.MarcinP_AI;

public class FriendlyCell extends Cell {


    public FriendlyCell(FakeCell fakeCell, MarcinP_AI aiEngine) {
        super(fakeCell, aiEngine);
    }

    @Override
    protected void addBacteriasToLists() {
        for (Bacteria bacteria : aiEngine.hostileBacteriaList) {
            if (bacteria.fakeBacteria.destination == this.fakeCell) {
                closestIncomingHostileBacteriaList.add(bacteria);
            }
        }
        for (Bacteria bacteria : aiEngine.friendlyBacteriaList) {
            if (bacteria.fakeBacteria.destination == this.fakeCell) {
                closestIncomingFriendlyBacteriaList.add(bacteria);
            }
        }
    }

    @Override
    protected void addCellsToLists() {
        closestFriendlyCellList.addAll(aiEngine.friendlyCellList);
        closestFriendlyCellList.remove(this);
        closestHostileCellList.addAll(aiEngine.hostileCellList);
        closestHostileCellList.remove(this);
        closestNeutralCellList.addAll(aiEngine.neutralCellList);
        closestNeutralCellList.remove(this);
    }


}

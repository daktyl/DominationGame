package com.domination.game.players.MarcinP_AI.states;


import com.badlogic.gdx.Gdx;
import com.domination.game.players.MarcinP_AI.MarcinP_AI;
import com.domination.game.players.MarcinP_AI.entities.Cell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NormalState extends AIState {

    //Singleton
    private static final NormalState instance = new NormalState();

    private NormalState() {
    }

    public static AIState getInstance() {
        return instance;
    }

    @Override
    public void enter(MarcinP_AI aiEngine) {
    }

    @Override
    public void execute(final MarcinP_AI aiEngine) {
        this.aiEngine = aiEngine;

        // Defense
        for (final Cell friendlyCell : aiEngine.friendlyCellList) {
            if (friendlyCell.closestFriendlyCellList.size() > 0) {
                friendlyCell.closestFriendlyCellList.sort(new Comparator<Cell>() {  // sortuję żeby mieć najlepszą opcję pomocy
                    @Override
                    public int compare(Cell cell, Cell t1) {
                        float cell1Factor = (float) (Math.pow(cell.fakeCell.bacteriaAmount, 2) - aiEngine.expectedDeltaTimeBeetweenCells(friendlyCell, cell) / 10);
                        float cell2Factor = (float) (Math.pow(t1.fakeCell.bacteriaAmount, 2) - aiEngine.expectedDeltaTimeBeetweenCells(friendlyCell, t1) / 10);
                        if (cell1Factor > cell2Factor) return -1;
                        else if (cell1Factor < cell2Factor) return 1;
                        else return 0;
                    }
                });

                List<Cell> groupAttackList = new ArrayList<Cell>();
                int groupAmount = 0;
                double groupTime = 0;

                for (Cell rescueCell : friendlyCell.closestFriendlyCellList) {
                    if (aiEngine.distanceBetweenCells(friendlyCell, rescueCell) > Gdx.graphics.getWidth()) break;
                    double expectedDeltaTime = aiEngine.expectedDeltaTimeBeetweenCells(friendlyCell, rescueCell);
                    int expectedFriendlyCellBacteriaAmount = friendlyCell.expectedBacteriaAmountAfterTime(expectedDeltaTime);
                    if (expectedFriendlyCellBacteriaAmount <= 0) {
                        if (rescueCell.fakeCell.bacteriaAmount / 2 > (-1) * expectedFriendlyCellBacteriaAmount) {
                            aiEngine.sendBacteria(rescueCell, friendlyCell);
                            return;
                        } else if (rescueCell.fakeCell.bacteriaAmount * 3f / 4f > (-1) * expectedFriendlyCellBacteriaAmount) {
                            aiEngine.sendBacteria(rescueCell, friendlyCell);
                            aiEngine.sendBacteria(rescueCell, friendlyCell);
                            return;
                        } else {
                            groupAttackList.add(rescueCell);
                            if (groupAttackList.size() > 3) break;
                            groupAmount += rescueCell.fakeCell.bacteriaAmount / 2;
                            if (expectedDeltaTime > groupTime) {
                                groupTime = expectedDeltaTime;
                                expectedFriendlyCellBacteriaAmount = friendlyCell.expectedBacteriaAmountAfterTime(groupTime);
                            }
                            if (groupAmount > (-1) * expectedFriendlyCellBacteriaAmount) {
                                for (Cell cell : groupAttackList) {
                                    aiEngine.sendBacteria(cell, friendlyCell);
                                }
                                return;
                            }

                        }
                    }
                }
            }
        }

        //ATTACK
        for (final Cell oppositeCell : aiEngine.oppositeCellList) {
            if (oppositeCell.closestHostileCellList.size() > 0) {
                oppositeCell.closestHostileCellList.sort(new Comparator<Cell>() { // sortuję sobie jej przeciwników (czyli swoje jednostki), tak, że Moje silne jednostki które są blisko tego wroga mają pierwszeństwo
                    @Override
                    public int compare(Cell cell, Cell t1) {
                        float cell1Factor = (float) (Math.pow(cell.fakeCell.bacteriaAmount, 2) - aiEngine.expectedDeltaTimeBeetweenCells(oppositeCell, cell) / 10);
                        float cell2Factor = (float) (Math.pow(t1.fakeCell.bacteriaAmount, 2) - aiEngine.expectedDeltaTimeBeetweenCells(oppositeCell, t1) / 10);

                        if (cell1Factor > cell2Factor) return -1;
                        else if (cell1Factor < cell2Factor) return 1;
                        else return 0;
                    }
                });

                List<Cell> groupAttackList = new ArrayList<Cell>();
                int groupAmount = 0;
                double groupTime = 0;

                for (Cell friendlyCell : oppositeCell.closestHostileCellList) {
                    if (aiEngine.distanceBetweenCells(oppositeCell, friendlyCell) > Gdx.graphics.getWidth()) break;
                    double expectedDeltaTime = aiEngine.expectedDeltaTimeBeetweenCells(oppositeCell, friendlyCell);
                    int expectedOppositeCellBacteriaAmount = oppositeCell.expectedBacteriaAmountAfterTime(expectedDeltaTime);
                    if (expectedOppositeCellBacteriaAmount < 0) break;
                    if (friendlyCell.fakeCell.bacteriaAmount / 2 > expectedOppositeCellBacteriaAmount) {
                        aiEngine.sendBacteria(friendlyCell, oppositeCell);
                        return;
                    } else if (friendlyCell.fakeCell.bacteriaAmount * 3f / 4f > expectedOppositeCellBacteriaAmount) {
                        aiEngine.sendBacteria(friendlyCell, oppositeCell);
                        aiEngine.sendBacteria(friendlyCell, oppositeCell);
                        return;
                    } else {
                        groupAttackList.add(friendlyCell);
                        if (groupAttackList.size() > 3) break;
                        groupAmount += friendlyCell.fakeCell.bacteriaAmount / 2;
                        if (expectedDeltaTime > groupTime) {
                            groupTime = expectedDeltaTime;
                            expectedOppositeCellBacteriaAmount = oppositeCell.expectedBacteriaAmountAfterTime(groupTime);
                        }
                        if (expectedOppositeCellBacteriaAmount < 0) break;
                        if (groupAmount > expectedOppositeCellBacteriaAmount) {
                            for (Cell cell : groupAttackList) {
                                aiEngine.sendBacteria(cell, oppositeCell);
                            }
                            return;
                        }

                    }
                }
            }
        }
    }


    @Override
    public void exit(MarcinP_AI aiEngine) {

    }
}



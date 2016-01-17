package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.ai_types.FakeCell;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.*;

class EnemyCellRating implements Comparable<EnemyCellRating> {
    private FakeCell fakeCell;
    private float rating;

    public EnemyCellRating(FakeCell fakeCell) {
        this.fakeCell = fakeCell;
        rating = fakeCell.bacteriaAmount;
    }

    @Override
    public int compareTo(EnemyCellRating o) {
        if (this.rating < o.rating)
            return -1;
        else if (this.rating == o.rating)
            return 0;
        else
            return 1;
    }

    public FakeCell getFakeCell() {
        return fakeCell;
    }
}

class VirtualCell extends FakeCell {
    public int virtualAmount;
    public double virtualTimeToReact = 999999d;
    public FakeCell fakeCellReference;
    public List<FakeBacteria> approachingBacteria;

    public VirtualCell(FakeCell fakeCell) {
        fakeCellReference = fakeCell;
        bacteriaAmount = fakeCell.bacteriaAmount;
        virtualAmount = bacteriaAmount;
        radius = fakeCell.radius;
        player = fakeCell.player;
        centerX = fakeCell.centerX;
        centerY = fakeCell.centerY;
        approachingBacteria = new ArrayList<FakeBacteria>();
    }
}

public class DaktylAI extends AI {
    boolean start = true;
    private Situation situation;
    private List<FakeCell> friendlyCells = new ArrayList<FakeCell>();
    private List<FakeCell> enemyCells = new ArrayList<FakeCell>();
    private List<FakeCell> neutralCells = new ArrayList<FakeCell>();
    private List<FakeBacteria> friendlyBacteria = new ArrayList<FakeBacteria>();
    private List<FakeBacteria> enemyBacteria = new ArrayList<FakeBacteria>();

    public DaktylAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color, "Daktyl AI");
    }

    private void updateFields() {
        friendlyCells.clear();
        enemyCells.clear();
        neutralCells.clear();
        friendlyBacteria.clear();
        enemyBacteria.clear();
        situation = gameplayWrapper.getCurrentSituation();
        for (FakeCell cell : situation.cellList) {
            if (cell.player == this)
                friendlyCells.add(cell);
            else {
                if (cell.player == null)
                    neutralCells.add(cell);
                else
                    enemyCells.add(cell);
            }
        }
        for (FakeBacteria bacteria : situation.bacteriaList) {
            if (bacteria.player == this)
                friendlyBacteria.add(bacteria);
            else
                enemyBacteria.add(bacteria);
        }
    }

    private double calculateBacteriaReachTime(FakeBacteria bacteria) {
        return bacteria.endTime - System.currentTimeMillis();
    }

    private double calculateTimeTravel(FakeCell source, FakeCell destination) {
        double distanceX = destination.centerX - source.centerX;
        double distanceY = destination.centerY - source.centerY;
        double cellsDistance = Math.sqrt(Math.pow((distanceX), 2) + Math.pow((distanceY), 2));
        return cellsDistance / 100 * 1000;
    }

    private FakeCell getCellProtector(VirtualCell attackedCell) {
        for (FakeCell protector : friendlyCells) {
            if (Math.floorDiv(protector.bacteriaAmount, 2) > Math.abs(attackedCell.virtualAmount) &&
                    Double.compare(calculateTimeTravel(protector, attackedCell) + (attackedCell.virtualTimeToReact / 1000), attackedCell.virtualTimeToReact) == -1)
                return protector;
        }
        return null;
    }

    private List<VirtualCell> prepareAttackedCellList() {
        List<VirtualCell> virtualCellList = new ArrayList<VirtualCell>();
        for (FakeBacteria bacteria : situation.bacteriaList) {
            int duplicateIndex = -1;
            for (int i = 0; i < virtualCellList.size(); ++i) {
                if (virtualCellList.get(i).fakeCellReference == bacteria.destination) {
                    duplicateIndex = i;
                    break;
                }
            }
            if (duplicateIndex == -1) {
                VirtualCell newVirtualCell = new VirtualCell(bacteria.destination);
                newVirtualCell.approachingBacteria.add(bacteria);
                virtualCellList.add(newVirtualCell);
            } else
                virtualCellList.get(duplicateIndex).approachingBacteria.add(bacteria);
        }

        for (VirtualCell virtualCell : virtualCellList) {
            for (FakeBacteria approachingBacteria : virtualCell.approachingBacteria) {
                if (approachingBacteria.player == this && (virtualCell.player == this || virtualCell.player == null))
                    virtualCell.virtualAmount += approachingBacteria.amount;
                else if (approachingBacteria.player == this && !(virtualCell.player == this || virtualCell.player == null))
                    virtualCell.virtualAmount -= approachingBacteria.amount;
                else if (approachingBacteria.player != this && (virtualCell.player == this || virtualCell.player == null))
                    virtualCell.virtualAmount -= approachingBacteria.amount;
                else
                    virtualCell.virtualAmount += approachingBacteria.amount;
//                virtualCell.virtualAmount -= approachingBacteria.amount;
            }
        }

        return virtualCellList;
    }

    private Set<VirtualCell> getEndangeredCells() {
        Set<VirtualCell> endangeredCells = new HashSet<VirtualCell>();
        List<VirtualCell> virtualCellList = prepareAttackedCellList();

        for (VirtualCell attackedCell : virtualCellList) {
            if (attackedCell.player != this && attackedCell.player != null)
                continue;

            for (FakeBacteria bacteria : attackedCell.approachingBacteria) {
                if (attackedCell.virtualAmount <= 0) {                                      // If the cell will be taken over
                    double attackTime = calculateBacteriaReachTime(bacteria);
                    if (Double.compare(attackedCell.virtualTimeToReact, attackTime) > 0)    // Update reaction time required to defend the cell
                        attackedCell.virtualTimeToReact = attackTime;
                    endangeredCells.add(attackedCell);                                      // Add it to endangered cells set
                }
            }
        }
        return endangeredCells;
    }

    private void protectCells() {
        Set<VirtualCell> endangeredCells = getEndangeredCells();
        for (VirtualCell attackedCell : endangeredCells) {
            FakeCell protector = getCellProtector(attackedCell);
            if (protector != null) {
                gameplayWrapper.sendBacteria(protector, attackedCell.fakeCellReference, this);
                attackedCell.virtualAmount += Math.floorDiv(protector.bacteriaAmount, 2);
            }
        }
    }

    private void attackEnemy() {
        List<EnemyCellRating> enemyCellRatingList = new ArrayList<EnemyCellRating>();
        List<EnemyCellRating> neutralCellRatingList = new ArrayList<EnemyCellRating>();
        FakeCell targetCell = null;

        if (start) {
            for (FakeCell neutralCell : neutralCells) {
                gameplayWrapper.sendBacteria(friendlyCells.get(0), neutralCell, this);
            }
            start = false;
        }

        for (FakeCell neutralCell : neutralCells) {
            neutralCellRatingList.add(new EnemyCellRating(neutralCell));
        }
        for (FakeCell enemyCell : enemyCells) {
            enemyCellRatingList.add(new EnemyCellRating(enemyCell));
        }

        if (!neutralCellRatingList.isEmpty()) {
            Collections.sort(neutralCellRatingList);
            targetCell = neutralCellRatingList.get(0).getFakeCell();
        }
        if (!enemyCellRatingList.isEmpty()) {
            if (targetCell == null) {
                Collections.sort(enemyCellRatingList);
                targetCell = enemyCellRatingList.get(0).getFakeCell();
            }
        } else
            return;

        for (FakeCell ownCell : friendlyCells) {
            if (ownCell.bacteriaAmount == 100) {
                gameplayWrapper.sendBacteria(ownCell, targetCell, this);
                return;
            }
        }

        for (FakeCell ownCell : friendlyCells) {
            if (Math.floorDiv(ownCell.bacteriaAmount, 2) > targetCell.bacteriaAmount + (calculateTimeTravel(ownCell, targetCell) / 1000)) {
                gameplayWrapper.sendBacteria(ownCell, targetCell, this);
                //break;
            }
        }
    }

//    private void attackEnemy() {
//        List<EnemyCellRating> enemyCellRatingList = new ArrayList<EnemyCellRating>();
//        List<EnemyCellRating> neutralCellRatingList = new ArrayList<EnemyCellRating>();
//        List<FakeCell> potentialTargets = new ArrayList<FakeCell>();
//        potentialTargets.addAll(enemyCells);
//        potentialTargets.addAll(neutralCells);
//        for (FakeCell enemyCell : potentialTargets) {
//            enemyCellRatingList.add(new EnemyCellRating(enemyCell));
//        }
//
//        if (enemyCellRatingList.isEmpty())
//            return;
//        Collections.sort(enemyCellRatingList);
//        FakeCell enemyCell = enemyCellRatingList.get(0).getFakeCell();
//
//        for (FakeCell ownCell : friendlyCells) {
//            if (ownCell.bacteriaAmount == 100) {
//                gameplayWrapper.sendBacteria(ownCell, enemyCell, this);
//                return;
//            }
//        }
//
//        for (FakeCell ownCell : friendlyCells) {
//            if (Math.floorDiv(ownCell.bacteriaAmount, 2) > enemyCell.bacteriaAmount + (calculateTimeTravel(ownCell, enemyCell) / 1000)) {
//                gameplayWrapper.sendBacteria(ownCell, enemyCell, this);
//                break;
//            }
//        }
//    }

    @Override
    protected void implementation() {
        while (true) {
            try {
                updateFields();
                protectCells();
                attackEnemy();

                synchronized (this) {
                    wait(1);
                }
            } catch (InterruptedException e) {
                Gdx.app.log(getPlayerName(), "Interrupted");
            }
        }
    }
}

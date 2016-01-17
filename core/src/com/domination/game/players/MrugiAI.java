package com.domination.game.players;

import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.ai_types.FakeCell;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MrugiAI extends AI {
    private final float velocity = 100;
    private final List<FakeCell> safeToSend = new ArrayList<FakeCell>();
    private final HashMap<FakeCell, Boolean> mine = new HashMap<FakeCell, Boolean>();

    public MrugiAI(GameplayWrapper gameplayWrapper, Color color) {
        super(gameplayWrapper, color, "MrugiAI");
    }

    private void getSituation() {
        Situation situation = gameplayWrapper.getCurrentSituation();
        cellList = situation.cellList;
        bacteriaList = situation.bacteriaList;
    }

    @Override
    public String getPlayerName() {
        return "MrugiAI";
    }

    @Override
    protected void implementation() {

        safeToSend.clear();
        getSituation();
        getSafeCells();
        markMine();
        attack();
        for (FakeCell cell : cellList) {
            if (!safeToSend.contains(cell) && cell.player == this)
                if (!isSafe(cell)) {
                    FakeCell helper = findClosestWhoCanHelp(cell, safeToSend);
                    if (helper != null) {
                        if (!gameplayWrapper.sendBacteria(helper, cell, this))
                            return;
                        else
                            safeToSend.remove(helper);
                    }
                }
        }

        for (FakeCell cell : cellList) {
            if (cell.player == null)
                return;
        }
        List<FakeCell> opponents = new ArrayList<FakeCell>();
        int maxInOpponents = 0;
        int myMax = 0;
        for (FakeCell cell : cellList)
            if (cell.player != this && cell.player != null) {
                maxInOpponents = Math.max(cell.bacteriaAmount, maxInOpponents);
                opponents.add(cell);
            } else if (cell.player == this && cell.bacteriaAmount > myMax) {
                myMax = cell.bacteriaAmount;
            }
        for (FakeCell cell : cellList)
            if (cell.bacteriaAmount == 100 && cell.player == this) {
                FakeCell target = findWithMinBacteria(opponents);
                if (coutIncomingMineBacteria(target) > 50)
                    return;
                if (!gameplayWrapper.sendBacteria(cell, target, this))
                    break;

            }

    }

    private void getSafeCells() {
        for (FakeCell cell : cellList) {
            if (cell.player == this && isSafeToSend(cell)) {
                safeToSend.add(cell);
            }
        }
    }

    private void attack() {
        List<FakeCell> toRemove = new ArrayList<FakeCell>();
        for (FakeCell cell : safeToSend) {
            FakeCell destination = findDestination(cell);
            if (destination != null) {
                if (!gameplayWrapper.sendBacteria(cell, destination, this)) {
                    return;
                } else {
                    mine.put(destination, true);
                    toRemove.add(cell);
                }
            }
        }
        for (FakeCell cell : toRemove)
            safeToSend.remove(cell);
    }

    private FakeCell findClosest(FakeCell cell, List<FakeCell> list) {
        if (list.isEmpty()) return null;
        float dist, closestDist = distanceSquered(cell, list.get(0));
        FakeCell closest = list.get(0);
        for (FakeCell helper : list)
            if (helper != cell) {
                dist = distanceSquered(cell, helper);
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = helper;
                }
            }
        return closest;
    }

    private FakeCell findClosestWhoCanHelp(FakeCell cell, List<FakeCell> list) {
        int incoming = countIncomingBacteria(cell);
        if (list.isEmpty()) return null;
        float dist, closestDist = distanceSquered(cell, list.get(0));
        FakeCell closest = list.get(0);
        for (FakeCell helper : list) {
            if (helper.bacteriaAmount / 2 >= incoming) {
                dist = distanceSquered(cell, helper);
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = helper;
                }
            }
        }
        return closest;
    }

    private FakeCell findWithMinBacteria(List<FakeCell> list) {
        int min = 101;
        FakeCell minCell = null;
        for (FakeCell cell : list) {
            if (min > cell.bacteriaAmount && cell.player != null && cell.player != this) {
                min = cell.bacteriaAmount;
                minCell = cell;
            }
        }
        return minCell;
    }

    FakeCell findClosestOpponent(FakeCell cell) {
        float dist = -1, newDist;
        FakeCell target = null;
        for (FakeCell other : cellList) {
            if (other != cell && other.player != this && other.player != null) {
                newDist = distanceSquered(cell, other);
                if (dist == -1 || dist < newDist) {
                    dist = newDist;
                    target = other;
                }

            }
        }
        return target;
    }

    private boolean isSafe(FakeCell cell) {
        int incomingOpp = countIncomingOpponentBacteria(cell);
        int incomingMine = coutIncomingMineBacteria(cell);
        if (incomingOpp >= cell.bacteriaAmount + incomingMine + countShortestIncomingTime(cell))
            return false;

        double fastestOpp = -1;
        double fastestMine = -1;
        for (FakeBacteria bacteria : bacteriaList) {
            if (bacteria.destination == cell) {

                if (bacteria.player != this && (fastestOpp < bacteria.endTime || fastestOpp == -1)) {
                    fastestOpp = bacteria.endTime;
                } else if (bacteria.player == this && (fastestMine < bacteria.endTime || fastestMine == -1)) {
                    fastestMine = bacteria.endTime;
                }

            }
        }
        if (fastestMine < fastestOpp && Math.floor(fastestOpp - System.currentTimeMillis()) + cell.bacteriaAmount + incomingMine > incomingOpp)
            return true;

        for (FakeCell other : cellList)
            if (other.player != this && other != cell && other.player != null) {
                if (cell.bacteriaAmount - incomingOpp + incomingMine - 1 < other.bacteriaAmount / 2) {
                    return false;
                }
            }
        for (FakeBacteria bacteria : bacteriaList)
            if (bacteria.amount / 2 > cell.bacteriaAmount)
                return false;
        return true;
    }

    private boolean isSafeToSend(FakeCell cell) {
        int inclommingMine = countIncomingBacteria(cell);
        int inclommingOpp = countIncomingOpponentBacteria(cell);
        if (inclommingOpp >= cell.bacteriaAmount + inclommingMine)
            return false;
        for (FakeCell other : cellList)
            if (other.player != this && other != cell && other.player != null) {
                if (cell.bacteriaAmount / 2 + Math.floor(countShortestIncomingTime(cell)) - inclommingMine < other.bacteriaAmount / 2 + inclommingOpp) {
                    return false;
                }
            }
        for (FakeBacteria bacteria : bacteriaList) {
            if (bacteria.player != this && bacteria.amount / 2 >= cell.bacteriaAmount)
                return false;
        }
        return true;
    }

    private int countShortestIncomingTime(FakeCell cell) {
        double shortest = 3600;
        for (FakeBacteria bacteria : bacteriaList) {
            if (bacteria.destination == cell && bacteria.player != this) {
                double time = bacteria.endTime - System.currentTimeMillis();
                if (shortest < time) {
                    shortest = time;
                }
            }
        }
        return (int) Math.floor(shortest / 1000);
    }

    private int countIncomingBacteria(FakeCell cell) {
        float other = 0;
        float mine = 0;
        for (FakeBacteria bacteria : bacteriaList)
            if (bacteria.destination == cell) {
                if (bacteria.player == this)
                    mine += bacteria.amount; //TODO spr
                else if (bacteria.player != null)
                    other += bacteria.amount;
            }
        return (int) Math.ceil(other - mine);
    }

    private int countIncomingOpponentBacteria(FakeCell cell) {
        float other = 0;
        for (FakeBacteria bacteria : bacteriaList)
            if (bacteria.destination == cell) {
                if (bacteria.player != this)
                    other += bacteria.amount;
            }
        return (int) Math.ceil(other);
    }

    private int coutIncomingMineBacteria(FakeCell cell) {
        float mine = 0;
        for (FakeBacteria bacteria : bacteriaList)
            if (bacteria.destination == cell) {
                if (bacteria.player == this)
                    mine += bacteria.amount;
            }
        return (int) Math.floor(mine);
    }

    private double calculateTime(FakeCell source, FakeCell destination) {
        double cellsDistance = Math.sqrt(
                distanceSquered(source, destination)
        );

        return (cellsDistance / velocity) * 1000; // seconds
    }

    private boolean willBeMine(FakeCell cell) {
        int incomingOpp = countIncomingOpponentBacteria(cell);
        int incomingMine = coutIncomingMineBacteria(cell);
        if (cell.player == null)
            return cell.bacteriaAmount < incomingMine && incomingMine > incomingOpp && incomingMine > 0;
        if (cell.player == this) {
            double fastestOpp = -1;
            double fastestMine = -1;
            for (FakeBacteria bacteria : bacteriaList) {
                if (bacteria.destination == cell) {

                    if (bacteria.player != this && (fastestOpp < bacteria.endTime || fastestOpp == -1)) {
                        fastestOpp = bacteria.endTime;
                    } else if (bacteria.player != this && (fastestMine < bacteria.endTime || fastestMine == -1)) {
                        fastestMine = bacteria.endTime;
                    }

                }
            }
            if (fastestMine < fastestOpp && Math.floor(fastestOpp - System.currentTimeMillis()) + cell.bacteriaAmount + incomingMine > incomingOpp)
                return true;
            return false;
        }
        if (cell.bacteriaAmount < incomingMine)
            return true;
        return false;
    }

    int calculateAmountAfterTime(FakeCell cell, double time) {
        return (int) (cell.bacteriaAmount + time);
    }

    private FakeCell findDestination(FakeCell cell) {
        List<FakeCell> canBeAttacked = new ArrayList<FakeCell>();
        for (FakeCell other : cellList) {
            if (!mine.get(other)) {

                if (other.player == null) {
                    if (canSteal(cell, other) || isWorthAttacking(cell, other))
                        canBeAttacked.add(other);
                } else if (other.player != this && cell.bacteriaAmount / 2 > other.bacteriaAmount + calculateTime(cell, other))
                    canBeAttacked.add(other);
            }
        }
        if (canBeAttacked.isEmpty()) return null;
        return findClosest(cell, canBeAttacked);
    }

    private boolean isWorthAttacking(FakeCell from, FakeCell to) {
        int incomming = countIncomingOpponentBacteria(to);
        if (incomming >= from.bacteriaAmount / 2)
            return false;
        if (countShortestIncomingTime(to) > calculateTime(from, to))
            return false;
        if (from.bacteriaAmount / 2 <= to.bacteriaAmount) {
            return false;
        }
        int amount = from.bacteriaAmount / 2;
        amount -= to.bacteriaAmount;
        for (FakeCell cell : cellList)
            if (cell.player != null && cell.player != this && cell != to) {
                if (cell.bacteriaAmount + calculateTime(from, to) - calculateTime(cell, to) >= amount)
                    return false;
            }

        return true;
    }

    private boolean canSteal(FakeCell cell, FakeCell target) {
        FakeBacteria fastestAttacker = null;
        double fastest = -1;
        for (FakeBacteria bacteria : bacteriaList) {
            if (bacteria.destination == target && (fastest > bacteria.endTime || fastest == -1)) {
                if (fastestAttacker == null) {
                    fastestAttacker = bacteria;
                    fastest = fastestAttacker.endTime;
                } else return false;
            }
        }
        if (fastestAttacker == null)
            return false;
        if (fastestAttacker.amount - target.bacteriaAmount > cell.bacteriaAmount / 2 - 1)
            return false;
        Long time = System.currentTimeMillis();
        if (fastestAttacker.endTime + 100 > System.currentTimeMillis() + calculateTime(cell, target))
            return false;
        return true;
    }

    private void markMine() {
        mine.clear();
        for (FakeCell cell : cellList) {
            if (willBeMine(cell))
                mine.put(cell, true);
            else
                mine.put(cell, false);
        }

    }

    private float distanceSquered(FakeCell destination, FakeCell source) {
        return (destination.centerX - source.centerX) * (destination.centerX - source.centerX) + (destination.centerY - source.centerY) * (destination.centerY - source.centerY);
    }

    private int countAllBacteria() {
        int sum = 0;
        for (FakeCell cell : cellList) {
            if (cell.player != null) {
                if (cell.player == this)
                    sum += cell.bacteriaAmount;
                else
                    sum -= cell.bacteriaAmount;
            }
        }

        for (FakeBacteria bacteria : bacteriaList) {
            if (bacteria.player != null) {
                if (bacteria.player == this)
                    sum += bacteria.amount;
                else
                    sum -= bacteria.amount;
            }
        }
        return sum;
    }
}

package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.players.*;
import com.domination.game.players.MarcinP_AI.MarcinP_AI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameplayState extends GameState {
    public List<Cell> cellList = new ArrayList<Cell>();
    public List<Bacteria> bacteriaList = new CopyOnWriteArrayList<Bacteria>();
    protected int AISet, AISetRight;
    List<Player> playerList = new ArrayList<Player>();
    private long lastCheckWinerTime;

    public GameplayState(Game game, SpriteBatch batch, int AISet, int AISetRight) {
        super(game, batch);
        this.AISet = AISet;
        this.AISetRight = AISetRight;
    }

    @Override
    public void init() {
        setDefaultBackground();
        setFirstPlayer();
        setSecondPlayer();
        generateMap(10);
        addCellsAndBacteriaToEntityManager();
        for (Player player : playerList) {
            player.start();
        }
    }

    protected void setFirstPlayer() {
        switch (AISet) {
            case (0):
                playerList.add(new DefaultAI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
            case (1):
                playerList.add(new DefaultAI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
            case (2):
                playerList.add(new FilipAI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
            case (3):
                playerList.add(new MrugiAI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
            case (4):
                playerList.add(new MarcinP_AI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
            case (5):
                playerList.add(new DaktylAI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
            case (6):
                playerList.add(new DefaultAI(new GameplayWrapper(this), new Color(0.2f, 0.8f, 0.8f, 1.f)));
                break;
        }
    }

    protected void setSecondPlayer() {
        switch (AISetRight) {
            case (0):
                playerList.add(new DefaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case (1):
                playerList.add(new DefaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case (2):
                playerList.add(new FilipAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case (3):
                playerList.add(new MrugiAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case (4):
                playerList.add(new MarcinP_AI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case (5):
                playerList.add(new DaktylAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
            case (6):
                playerList.add(new DefaultAI(new GameplayWrapper(this), new Color(0.8f, 0.2f, 0.1f, 1f)));
                break;
        }
    }

    @Override
    public void update() {
        super.update();
        for (Iterator<Bacteria> it = bacteriaList.iterator(); it.hasNext(); ) {
            Bacteria next = it.next();
            if (next.isBroken()) {
                bacteriaList.remove(next);
            }
        }
        for (Cell cell : cellList) {
            Cell colision = checkCollisionWithOtherCells(cell);
            if (colision != null) {
                cell.handleBouncing(colision);
            }
        }
        checkEndGameConditions();
    }

    private void checkEndGameConditions() {
        Player winner = getWinner();

        if (winner == null) {
            lastCheckWinerTime = System.currentTimeMillis();
            return;
        }
        if (lastCheckWinerTime + 3000 > System.currentTimeMillis())
            return;
        if (playerList.get(0) == winner)
            game.pushGameState(new ResultState(playerList.get(0), playerList.get(1), game, batch));
        else
            game.pushGameState(new ResultState(playerList.get(1), playerList.get(0), game, batch));
    }

    private Player getWinner() {
        Player winner = null;
        for (Bacteria bacteria : bacteriaList) {
            if (bacteria.getPlayer() != null) {
                if (winner == null)
                    winner = bacteria.getPlayer();
                else if (bacteria.getPlayer() != winner) {
                    return null;
                }
            }
        }
        for (Cell cell : cellList) {
            if (cell.getPlayer() != null) {
                if (winner == null)
                    winner = cell.getPlayer();
                else if (cell.getPlayer() != winner)
                    return null;

            }
        }
        return winner;
    }

    protected void addCellsAndBacteriaToEntityManager() {
        for (Bacteria bacteria : bacteriaList)
            entityManager.add(bacteria);
        for (Cell cell : cellList)
            entityManager.add(cell);
    }

    protected void generateMap(int cellNumber) {
        cellNumber /= 2;
        Random random = new Random();
        if (playerList.size() == 2) {
            int middleX, middleY;
            float mapCenterX = Gdx.graphics.getWidth() / 2.f;

            middleX = random.nextInt(Gdx.graphics.getWidth() / 2 - Cell.radius * 2) + Cell.radius;
            middleY = random.nextInt(Gdx.graphics.getHeight() - Cell.radius * 2) + Cell.radius;
            cellList.add(new Cell(playerList.get(0), mapCenterX + middleX, middleY, this.batch));
            cellList.add(new Cell(playerList.get(1), mapCenterX - middleX, middleY, this.batch));
            int i = 0;
            while (i < cellNumber - 1) {
                middleX = random.nextInt(Gdx.graphics.getWidth() / 2 - Cell.radius * 2) + Cell.radius;
                middleY = random.nextInt(Gdx.graphics.getHeight() - Cell.radius * 2) + Cell.radius;
                if (!checkCollisionWithAllCells(mapCenterX + middleX, middleY)) {
                    cellList.add(new Cell(null, mapCenterX + middleX, middleY, this.batch));
                    cellList.add(new Cell(null, mapCenterX - middleX, middleY, this.batch));
                    i++;
                }
            }
        }
    }

    private Boolean checkCollisionWithAllCells(float x, float y) {
        for (Cell cell : cellList) {
            float distance = (float) Math.sqrt(Math.pow(cell.getCenterX() - x, 2) + Math.pow(cell.getCenterY() - y, 2));
            if (distance <= 2 * cell.getRadius())
                return true;
        }
        return false;
    }

    private Cell checkCollisionWithOtherCells(Cell cell) {
        float x = cell.getCenterX();
        float y = cell.getCenterY();
        for (Cell secondCell : cellList) {
            if (cell == secondCell) continue;
            float distance = (float) Math.sqrt(Math.pow(secondCell.getCenterX() - x, 2) + Math.pow(secondCell.getCenterY() - y, 2));
            if (distance <= 2 * secondCell.getRadius())
                return secondCell;
        }
        return null;
    }

    public synchronized Boolean sendBacteria(Cell source, Cell destination, Player player) {
        if (source != null && destination != null && source.getPlayer() == player && source.getAmount() > 1 && !source.isBroken() && !destination.isBroken()) {
            Bacteria bacteria = new Bacteria(player, source, destination, source.getBacteriaAmount(), batch);
            source.handleOutgoingBacteria(bacteria);
            entityManager.add(bacteria);
            bacteriaList.add(bacteria);
            return true;
        } else {
            return false;
        }
    }
}

package com.domination.game.ai_types;

import com.domination.game.entities.Cell;
import com.domination.game.players.Player;

public class FakeCell {
    public int bacteriaAmount;
    public int radius;
    public Player player;
    public float centerX;
    public float centerY;

    public FakeCell() {}
    public FakeCell(Cell cell) {
        this.bacteriaAmount = cell.getAmount();
        this.radius = cell.getRadius();
        this.player = cell.getPlayer();
        this.centerX = cell.getCenterX();
        this.centerY = cell.getCenterY();
    }
}
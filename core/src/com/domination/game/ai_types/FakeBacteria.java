package com.domination.game.ai_types;

import com.domination.game.entities.Bacteria;
import com.domination.game.players.Player;

public class FakeBacteria {
    public int amount;
    public Player player;
    public FakeCell source;
    public FakeCell destination;
    public double startTime;
    public double endTime;
    public double distanceX;
    public double distanceY;
    public double velocity;

    public FakeBacteria(Bacteria bacteria) {
        this.amount = bacteria.getAmount();
        this.player = bacteria.getPlayer();
        this.startTime = bacteria.getStartTime();
        this.endTime = bacteria.getEndTime();
        this.distanceX = bacteria.getDistanceX();
        this.distanceY = bacteria.getDistanceY();
        this.velocity = bacteria.getVelocity();
    }
}
package com.domination.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Player;

public class Bacteria extends GraphicalEntity {

    private Player player;

    private int amount;

    private Cell source;
    private Cell destination;

    private double startTime;
    private double endTime;

    private double distanceX;
    private double distanceY;
    private double velocity;

    public Bacteria(Player player, SpriteBatch batch, Cell from, Cell to, int amount) {
        super(new Texture("badlogic.jpg"), 30, 30, 30, 30, batch);
        this.player = player;
        this.amount = amount;
        source = from;
        destination = to;
        velocity = 100;

        distanceX = destination.getX() - source.getX();
        distanceY = destination.getY() - source.getY();

        startTime = System.currentTimeMillis();
        endTime = calculateEndTime();
        sprite.setPosition(source.getX() - sprite.getWidth() / 2, source.getY() - sprite.getHeight() / 2);
    }

    double calculateEndTime() {
        double cellsDistance = (double) Math.sqrt(
                Math.pow((distanceX), 2) + Math.pow((distanceY), 2)
        );
        double deltaTime = (cellsDistance / velocity * 1000); // seconds to miliseconds
        return (startTime + deltaTime);
    }

    @Override
    public void update() {
        long currTime = System.currentTimeMillis();
        if (currTime >= endTime) {
            isBroken = true;
            return;
        }
        double finishedPart = (currTime - startTime) / (endTime - startTime);
        double newX = source.getX() + distanceX * finishedPart - sprite.getWidth() / 2;
        double newY = source.getY() + distanceY * finishedPart - sprite.getHeight() / 2;
        sprite.setPosition((float) newX, (float) newY);
    }

    public int getAmount() {
        return amount;
    }

    public Cell getSource() {
        return source;
    }

    public Cell getDestination() {
        return destination;
    }

}

package com.domination.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.engine.ResourceManager;
import com.domination.game.players.Player;

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
    private static final Integer radius = 50;

    private TextEntity amountText;

    public Bacteria(Player player, Cell source, Cell destination, int amount, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("BacteriaTexture"), batch);
        this.player = player;
        this.amount = amount;
        amountText = new TextEntity(Integer.toString(amount),(BitmapFont) ResourceManager.getInstance().get("Font"), this.batch);
        this.source = source;
        this.destination = destination;
        velocity = 100;

        distanceX = destination.getX() - source.getX();
        distanceY = destination.getY() - source.getY();

        startTime = System.currentTimeMillis();
        endTime = calculateEndTime();
        sprite.setScale(radius/sprite.getWidth());
    }

    double calculateEndTime() {
        double cellsDistance = Math.sqrt(
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
            // REMOVE LATER:
            destination.handleIncomingBacteria(this);
            return;
        }
        double finishedPart = (currTime - startTime) / (endTime - startTime);
        double newX = source.getX()+source.getWidth()/2-getWidth()/2 + distanceX * finishedPart;
        double newY = source.getY()+source.getHeight()/2-getHeight()/2 + distanceY * finishedPart;
        sprite.setPosition((float) newX, (float) newY);
        amountText.label.setPosition(getX() + sprite.getWidth()/2.f - amountText.label.getWidth()/2.f,sprite.getY()+sprite.getHeight()/2- amountText.label.getHeight()/2);
    }

    @Override
    public void draw() {
        super.draw();
        amountText.draw();
    }

    public int getAmount() {
        return amount;
    }

    public Cell getSource() {
        return source;
    }

    public Player getPlayer() {
        return player;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getDistanceX() {
        return distanceX;
    }

    public double getDistanceY() {
        return distanceY;
    }

    public double getVelocity() {
        return velocity;
    }

    public Cell getDestination() {
        return destination;
    }

}

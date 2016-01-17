package com.domination.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.engine.ResourceManager;
import com.domination.game.players.Player;

public class Bacteria extends GraphicalEntity {

    private static final Integer radius = 30;
    private final BitmapFont font;
    private final Player player;
    private final int amount;
    private final Cell source;
    private final Cell destination;
    private final double startTime;
    private final double endTime;
    private final double velocity; // pixels per second
    private final float startCenterX;
    private final float startCenterY;
    private final float xPosition;
    private double distanceX;
    private double distanceY;

    public Bacteria(Player player, Cell source, Cell destination, int amount, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("BacteriaTexture"), batch);
        this.player = player;
        this.amount = amount;
        xPosition = (amount > 9) ? 13 : 7;
        font = ResourceManager.getInstance().get("BacteriaFont");
        this.source = source;
        this.destination = destination;
        velocity = 100;
        Color color = new Color(player.getColor());
        color.a = 0.95f;
        sprite.setColor(color);
        distanceX = destination.getCenterX() - source.getCenterX();
        distanceY = destination.getCenterY() - source.getCenterY();
        startCenterX = source.getCenterX();
        startCenterY = source.getCenterY();
        startTime = System.currentTimeMillis();
        endTime = calculateEndTime();
        sprite.setOrigin(0, 0);
        sprite.setScale(radius * 2 / sprite.getWidth());
    }

    private double calculateEndTime() {
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
            destination.handleIncomingBacteria(this);
            return;
        }
        distanceX = destination.getCenterX() - startCenterX;
        distanceY = destination.getCenterY() - startCenterY;
        double finishedPart = (currTime - startTime) / (endTime - startTime);
        double newX = startCenterX + distanceX * finishedPart;
        double newY = startCenterY + distanceY * finishedPart;
        setPositionCenter((float) newX, (float) newY);
    }

    @Override
    public void draw() {
        super.draw();
        font.draw(batch, String.valueOf(amount), getCenterX() - xPosition, getCenterY() + 5.f);
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

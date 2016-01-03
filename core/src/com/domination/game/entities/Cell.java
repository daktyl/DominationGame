package com.domination.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.players.Player;
import com.domination.game.engine.ResourceManager;

public class Cell extends GraphicalEntity{
    private Integer bacteriaAmount;
    private TextEntity bacteriaAmountText;
    private Integer radius = 35;
    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private Long lastUpdateTime = System.currentTimeMillis();

    public Cell(Player player, float x, float y, SpriteBatch batch) {
        super((Texture)ResourceManager.getInstance().get("TestTexture"),100,100,50,50, batch);
        this.player = player;
        if (player != null) {
            bacteriaAmount = 50;
        }
        else
            bacteriaAmount = 10;
        checkColor();
        bacteriaAmountText = new TextEntity(Integer.toString(bacteriaAmount), (BitmapFont)ResourceManager.getInstance().get("Font"), this.batch);
        sprite.setX(x);
        sprite.setY(y);
        bacteriaAmountText.label.setPosition(sprite.getX() + sprite.getWidth()/2 - bacteriaAmountText.label.getHeight()/2,
                sprite.getY() +sprite.getHeight() /2-bacteriaAmountText.label.getHeight()/2);
        bitmapFont = ResourceManager.getInstance().get("Font");
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public void update() {
        if(System.currentTimeMillis()>lastUpdateTime+1000) {
            lastUpdateTime += 1000;
            if (bacteriaAmount < 100 && player != null) {
                bacteriaAmount++;
            }
        }
        bacteriaAmountText.label.setText(bacteriaAmount.toString());
    }

    @Override
    public void draw() {
        super.draw();
        bacteriaAmountText.draw();
    }

    public void handleIncomingBacteria(Bacteria bacteria) {
        Integer amount = bacteria.getAmount();
        Player owner = bacteria.getSource().player;
        if (player == bacteria.getSource().player)
            bacteriaAmount +=amount;
        else {
            if (bacteriaAmount > amount)
                bacteriaAmount -= amount;
            else if (bacteriaAmount < amount){
                bacteriaAmount = amount - bacteriaAmount;
                player = owner;
            }
            else {
                bacteriaAmount = 0;
                player = null;
            }

        }
        checkColor();
    }

    public Integer handleOutgiongBacteria() {
        int outgoingAmount = Math.floorDiv(bacteriaAmount,2);
        bacteriaAmount = (int) Math.ceil((double)bacteriaAmount/2.f);
        return outgoingAmount;
    }

    public Integer getBacteriaAmount() {
        return bacteriaAmount;
    }

    public Integer getRadius() {
        return radius;
    }

    public TextEntity getBacteriaAmountText() {
        return bacteriaAmountText;
    }

    public Player getPlayer() {
        return player;
    }

    private void checkColor() {
        if (player != null)
            sprite.setColor(player.getColor());
        else
            sprite.setColor(Color.CLEAR);
    }
}

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
    public static final Integer radius = 75;
    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private Long lastUpdateTime = System.currentTimeMillis();

    public Cell(Player player, float x, float y, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("CellTexture"), batch);
        this.player = player;
        if (player != null) {
            bacteriaAmount = 50;
        }
        else
            bacteriaAmount = 10;
        checkColor();
        bacteriaAmountText = new TextEntity(Integer.toString(bacteriaAmount), (BitmapFont)ResourceManager.getInstance().get("Font"), this.batch);
        sprite.setOrigin(0,0);
        sprite.setScale(radius*2/sprite.getWidth());
        sprite.setX(x-radius);
        sprite.setY(y-radius);
        bacteriaAmountText.label.setPosition(getCenterX()-bacteriaAmountText.label.getWidth()/2,getCenterY()-bacteriaAmountText.label.getHeight()/2);
        bitmapFont = ResourceManager.getInstance().get("Font");
    }

    @Override
    public void update() {
        if(System.currentTimeMillis()>lastUpdateTime+1000) {
            lastUpdateTime += 1000;
            if (bacteriaAmount < 100 && player != null) {
                bacteriaAmount++;
            }
        }
        bacteriaAmountText.label.setPosition(getCenterX()-bacteriaAmountText.label.getWidth()/2,getCenterY()-bacteriaAmountText.label.getHeight()/2);
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

    public Integer handleOutgoingBacteria() {
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
    
    public Player getPlayer() {
        return player;
    }

    private void checkColor() {
        if (player != null)
            sprite.setColor(player.getColor());
        else
            sprite.setColor(Color.WHITE);
    }
    public boolean isOnCell(int positionX,int positionY){
        return radius*radius>((positionX-getCenterX())*(positionX-getCenterX())+(positionY-getCenterY())*(positionY-getCenterY()));
    }
    public void glow(){
        sprite.setTexture((Texture) ResourceManager.getInstance().get("CellGlow"));
    }
    public void dim(){
        sprite.setTexture((Texture)ResourceManager.getInstance().get("CellTexture"));
    }
}

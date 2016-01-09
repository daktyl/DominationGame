package com.domination.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.players.Player;
import com.domination.game.engine.ResourceManager;

public class Cell extends GraphicalEntity{
    private Integer amount;
    private TextEntity amountText;
    public static final Integer radius = 75;
    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private Long lastUpdateTime = System.currentTimeMillis();

    public Cell(Player player, float x, float y, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("CellTexture"), batch);
        this.player = player;
        if (player != null) {
            amount = 50;
        }
        else
            amount = 10;
        checkColor();
        amountText = new TextEntity(Integer.toString(amount), (BitmapFont)ResourceManager.getInstance().get("Font"), this.batch);
        sprite.setOrigin(0,0);
        sprite.setScale(radius*2/sprite.getWidth());
        sprite.setX(x-radius);
        sprite.setY(y-radius);
        amountText.label.setPosition(getCenterX()- amountText.label.getWidth()/2,getCenterY()- amountText.label.getHeight()/2);
        bitmapFont = ResourceManager.getInstance().get("Font");
    }

    @Override
    public void update() {
        if(System.currentTimeMillis()>lastUpdateTime+1000) {
            lastUpdateTime += 1000;
            if (amount < 100 && player != null) {
                amount++;
            }
        }
        amountText.label.setPosition(getCenterX()- amountText.label.getWidth()/2,getCenterY()- amountText.label.getHeight()/2);
        amountText.label.setText(amount.toString());
    }

    @Override
    public void draw() {
        super.draw();
        amountText.draw();
    }

    public void handleIncomingBacteria(Bacteria bacteria) {
        Integer amount = bacteria.getAmount();
        Player owner = bacteria.getSource().player;
        if (player == bacteria.getSource().player)
            this.amount +=amount;
        else {
            if (this.amount > amount)
                this.amount -= amount;
            else if (this.amount < amount){
                this.amount = amount - this.amount;
                player = owner;
            }
            else {
                this.amount = 0;
                player = null;
            }
        }
        moveCellWithBacteria(bacteria);
        checkColor();
    }

    private void moveCellWithBacteria(Bacteria bacteria) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int amount = bacteria.getAmount();
//        float relation = (float)amount / (float)this.getAmount();

        float nextX = getCenterX() + amount*(float)bacteria.getDistanceX()/1000f;
        float nextY = getCenterY() + amount*(float)bacteria.getDistanceY()/1000f;
        if (nextX < getScaledWidth()/2 ) nextX = getScaledWidth()/2;
        if (nextX > screenWidth - getScaledWidth()/2) nextX = screenWidth - getScaledWidth()/2;
        if (nextY < getScaledHeight()/2) nextY = getScaledHeight()/2;
        if (nextY > screenHeight - getScaledHeight()/2) nextY = screenHeight - getScaledHeight()/2;
        setPositionCenter( nextX, nextY);
    }

    public Integer getBacteriaAmount() {
        int outgoingAmount = Math.floorDiv(amount,2);
        return outgoingAmount;
    }

    public void handleOutgoingBacteria(Bacteria bacteria) {
        amount = amount - bacteria.getAmount();
        moveCellWithBacteria(bacteria);
    }

    public Integer getAmount() {
        return amount;
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

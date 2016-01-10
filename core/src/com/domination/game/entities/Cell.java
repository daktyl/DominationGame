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
    private Long lastGrowingTime = System.currentTimeMillis();
    private Long lastMovingTime = System.currentTimeMillis();
    private Color freeCellColor = new Color(1f,1f,1f,0.5f);
    private float targetCenterX;
    private float targetCenterY;

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
        targetCenterX = x;
        targetCenterY = y;
        setPositionCenter(x,y);
        amountText.label.setPosition(getCenterX()- amountText.label.getWidth()/2,getCenterY()- amountText.label.getHeight()/2);
        bitmapFont = ResourceManager.getInstance().get("Font");
    }

    @Override
    public void update() {

        Long currentTime = System.currentTimeMillis();
        if(currentTime - lastGrowingTime > 1000) {
            lastGrowingTime += 1000;
            if (amount < 100 && player != null) {
                amount++;
            }
        }

        moveToTargetPosition(currentTime,10);

        amountText.label.setPosition(getCenterX()- amountText.label.getWidth()/2,getCenterY()- amountText.label.getHeight()/2);
        amountText.label.setText(amount.toString());

    }

    private void moveToTargetPosition(Long currentTime, float movingTimeSec){
        if(currentTime - lastMovingTime > 10) { // once per 1/100 sec
            lastMovingTime += 10;
            Float distanceX = targetCenterX - getCenterX();
            Float distanceY = targetCenterY - getCenterY();
            if (distanceX != 0 || distanceY != 0) {
                float nextCenterX = getCenterX() + distanceX / (10 * movingTimeSec);
                float nextCenterY = getCenterY() + distanceY / (10 * movingTimeSec);
                setPositionCenter(nextCenterX, nextCenterY);
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        amountText.draw();
    }

    public void handleIncomingBacteria(Bacteria bacteria) {
        Integer amount = bacteria.getAmount();
        Player owner = bacteria.getSource().player;
        if (player == bacteria.getSource().player) {
            this.amount += amount;
            this.amount %= 100;
        }
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
        int bacteriaAmount = bacteria.getAmount();
        float relation;
        if (this.amount == 0)
            relation = (float)(10f + (bacteriaAmount));
        else
            relation = (float)(10f + (bacteriaAmount / (this.amount)));
        int directionX = (int)(bacteria.getDistanceX() / Math.abs(bacteria.getDistanceX()));
        int directionY = (int)(bacteria.getDistanceY() / Math.abs(bacteria.getDistanceY()));
        float directionRatio = (float)Math.abs(bacteria.getDistanceY()/bacteria.getDistanceX());
        float nextCenterX = targetCenterX + relation * directionX;
        float nextCenterY = targetCenterY + relation * directionY * directionRatio;
        if (nextCenterX < getScaledWidth()/2 ) nextCenterX = getScaledWidth()/2;
        if (nextCenterX > screenWidth - getScaledWidth()/2) nextCenterX = screenWidth - getScaledWidth()/2;
        if (nextCenterY < getScaledHeight()/2) nextCenterY = getScaledHeight()/2;
        if (nextCenterY > screenHeight - getScaledHeight()/2) nextCenterY = screenHeight - getScaledHeight()/2;
        targetCenterX = nextCenterX;
        targetCenterY = nextCenterY;
    }

    public Integer getBacteriaAmount() {
        return Math.floorDiv(amount,2);
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
            sprite.setColor(freeCellColor);
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
    public void stopMoving(){
        targetCenterX = getCenterX();
        targetCenterY = getCenterY();
    }
}

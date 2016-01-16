package com.domination.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.engine.ResourceManager;
import com.domination.game.players.Player;

public class Cell extends GraphicalEntity {
    public static final Integer radius = 75;
    BitmapFont font;
    private Integer amount;
    private TextEntity amountText;
    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private Long lastGrowingTime = System.currentTimeMillis();
    private Long lastMovingTime = System.currentTimeMillis();
    private Color freeCellColor = new Color(1f, 1f, 1f, 0.5f);
    private float targetCenterX;
    private float targetCenterY;
    private Velocity velocity = new Velocity();

    public Cell(Player player, float x, float y, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("CellTexture"), batch);
        font = ResourceManager.getInstance().get("CellFont");
        this.player = player;
        if (player != null) {
            amount = 100;
        } else
            amount = 3;
        checkColor();
        amountText = new TextEntity(Integer.toString(amount), (BitmapFont) ResourceManager.getInstance().get("Font"), this.batch);
        sprite.setOrigin(0, 0);
        sprite.setScale(radius * 2 / sprite.getWidth());
        targetCenterX = x;
        targetCenterY = y;
        setPositionCenter(x, y);
        amountText.label.setPosition(getCenterX() - amountText.label.getWidth() / 2, getCenterY() - amountText.label.getHeight() / 2);
        bitmapFont = ResourceManager.getInstance().get("Font");
    }

    @Override
    public void update() {

        Long currentTime = System.currentTimeMillis();
        if (currentTime - lastGrowingTime > 1000) {
            lastGrowingTime += 1000;
            if (amount < 100 && player != null) {
                amount++;
            }
        }

        updatePosition(currentTime);

        amountText.label.setPosition(getCenterX() - amountText.label.getWidth() / 2, getCenterY() - amountText.label.getHeight() / 2);
        amountText.label.setText(amount.toString());

    }

    public void updatePosition(Long currentTime) {
        if (currentTime - lastMovingTime > 10) { // once per 1/100 sec
            lastMovingTime += 10;
            velocity.mulitiply(0.95f);
            Float positionX = velocity.x + getCenterX();
            Float positionY = velocity.y + getCenterY();
            int screenWidth = Gdx.graphics.getWidth();
            int screenHeight = Gdx.graphics.getHeight();
            if (positionX < getScaledWidth() / 2) {
                positionX = getScaledWidth() / 2;
                velocity.x *= -1;
            }
            if (positionX > screenWidth - getScaledWidth() / 2) {
                positionX = screenWidth - getScaledWidth() / 2;
                velocity.x *= -1;
            }
            if (positionY < getScaledHeight() / 2) {
                positionY = getScaledHeight() / 2;
                velocity.y *= -1;
            }
            if (positionY > screenHeight - getScaledHeight() / 2) {
                positionY = screenHeight - getScaledHeight() / 2;
                velocity.y *= -1;
            }

            setPositionCenter(positionX, positionY);
        }
    }

    @Override
    public void draw() {
        super.draw();
        font.draw(batch, String.valueOf(amount), getCenterX() - (10 * ((amount == 100) ? 3.f : (amount >= 10) ? 2.f : 1.f)), getCenterY() + 10.f);

    }

    public void handleIncomingBacteria(Bacteria bacteria) {
        Integer amount = bacteria.getAmount();
        Player owner = bacteria.getPlayer();
        if (player == owner) {
            this.amount += amount;
            if (this.amount > 100) this.amount = 100;
        } else {
            if (this.amount > amount)
                this.amount -= amount;
            else if (this.amount < amount) {
                this.amount = amount - this.amount;
                player = owner;
            } else {
                this.amount = 0;
                player = null;
            }
        }
        moveCellWithBacteria(bacteria);
        checkColor();
    }

    private void moveCellWithBacteria(Bacteria bacteria) {
        int bacteriaAmount = bacteria.getAmount();
        float relation = bacteriaAmount / 25000f;
        velocity.x += relation * (float) bacteria.getDistanceX();
        velocity.y += relation * (float) bacteria.getDistanceY();
    }

    public Integer getBacteriaAmount() {
        return Math.floorDiv(amount, 2);
    }

    public void handleOutgoingBacteria(Bacteria bacteria) {
        amount -= bacteria.getAmount();
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

    public boolean isOnCell(int positionX, int positionY) {
        return radius * radius > ((positionX - getCenterX()) * (positionX - getCenterX()) + (positionY - getCenterY()) * (positionY - getCenterY()));
    }

    public void glow() {
        sprite.setTexture((Texture) ResourceManager.getInstance().get("CellGlow"));
    }

    public void dim() {
        sprite.setTexture((Texture) ResourceManager.getInstance().get("CellTexture"));
    }

    public void stopMoving() {
        targetCenterX = getCenterX();
        targetCenterY = getCenterY();
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void handleBouncing(Cell colider) {
        float newVelX1 = (colider.velocity.x);
        float newVelY1 = (colider.velocity.y);
        float newVelX2 = (velocity.x);
        float newVelY2 = (velocity.y);
        colider.velocity.x = newVelX2;
        colider.velocity.y = newVelY2;
        velocity.x = newVelX1;
        velocity.y = newVelY1;
        updatePosition(System.currentTimeMillis());
        colider.updatePosition(System.currentTimeMillis());
    }

    public class Velocity {
        public float x = 0, y = 0;

        public void mulitiply(float num) {
            x *= num;
            y *= num;
        }
    }
}

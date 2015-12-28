package com.domination.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.Player;
import com.domination.game.engine.ResourceManager;

public class Cell extends GraphicalEntity{
    private Integer bacteriaAmount = 50;
    private TextEntity bacteriaAmountText;
    private Integer radius = 35;
    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private Long lastUpdateTime = System.currentTimeMillis();

    public Cell(Player player, float x, float y, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("TestTexture"),100,100,50,50, batch);
        this.player = player;
        bacteriaAmountText = new TextEntity(Integer.toString(bacteriaAmount),(BitmapFont) ResourceManager.getInstance().get("Font"), this.batch);
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
            if (bacteriaAmount < 100 && player != null)
                bacteriaAmount++;
        }
        bacteriaAmountText.label.setText(Integer.toString(bacteriaAmount));
    }

    @Override
    public void draw() {
        super.draw();
//        shapeRenderer.setColor(Color.WHITE);
//        shapeRenderer.begin();
//        shapeRenderer.circle(x,y,radius);
//        shapeRenderer.end();
        bacteriaAmountText.draw();
    }
    public void handleComingBacterias(int number, Player owner){
        if(player == owner)
            bacteriaAmount +=number;
        else{
            if(bacteriaAmount > number)
                bacteriaAmount -= number;
            else if(bacteriaAmount < number){
                bacteriaAmount = number - bacteriaAmount;
                player = owner;
            } else {
                bacteriaAmount = 0;
                player = null;
            }

        }
    }

    public Integer getBacteriaAmount() {
        return bacteriaAmount;
    }
}

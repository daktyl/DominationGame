package com.domination.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.AI.AI;
import com.domination.game.engine.ResourceManager;

public class Cell extends GraphicalEntity{
    private Integer bacteriaNumber = 0;
    private Integer radius = 35;
    private AI ai;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private float x,y;
//    public float getY() {return y;}
//    public float getX() {return x;}
    private Long lastUpdateTime = System.currentTimeMillis();

    public Cell(AI ai, float x, float y, SpriteBatch batch) {
        super((Texture) ResourceManager.getInstance().get("CellTexture"), batch);
        this.ai = ai;
        this.x = x;
        this.y = y;
        sprite.setX(x-sprite.getWidth()/2);
        sprite.setY(y-sprite.getHeight()/2);
        sprite.setScale(0.2f);
        bitmapFont = ResourceManager.getInstance().get("Font");
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);

    }

    @Override
    public void update() {
        if(System.currentTimeMillis()>lastUpdateTime+1000) {
            lastUpdateTime += 1000;
            if (bacteriaNumber < 100 && ai != null)
                bacteriaNumber++;
        }
    }

    @Override
    public void draw() {
        super.draw();
//        shapeRenderer.setColor(Color.WHITE);
//        shapeRenderer.begin();
//        shapeRenderer.circle(x,y,radius);
//        shapeRenderer.end();
        bitmapFont.draw(batch,bacteriaNumber.toString(),x-bitmapFont.getSpaceWidth(),y+bitmapFont.getCapHeight()/2);
    }
    public void handleComingBacterias(int number, AI player){
        if(this.ai == player)
            bacteriaNumber +=number;
        else{
            if(bacteriaNumber > number)
                bacteriaNumber -= number;
            else if(bacteriaNumber < number){
                bacteriaNumber = number - bacteriaNumber;
                this.ai = player;
            } else {
                bacteriaNumber = 0;
                player = null;
            }

        }
    }

    public Integer getBacteriaNumber() {
        return bacteriaNumber;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

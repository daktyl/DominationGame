package com.domination.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.AI.AI;
import com.domination.game.engine.ResourceManager;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by Mrugi on 2015-12-27.
 */
public class Cell extends GraphicalEntity{
    private Integer bacteriaNumber = 0;
    private Integer radius = 35;
    private AI ai;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont bitmapFont;
    private float x,y;
    private Thread reproduce = new Thread(){
        @Override
        public void run() {
            while (!Thread.interrupted()){
                try{
                    synchronized(this) { wait(1000); }
                }
                catch (InterruptedException e){
                    Gdx.app.log("Unexcpected interaption",e.toString());
                }
                if(bacteriaNumber<100 && ai != null)
                    bacteriaNumber++;
            }
        }
    };

    public Cell(AI ai, float x, float y, Texture texture, SpriteBatch batch) {
        super(texture, batch);
        this.ai = ai;
        this.x = x;
        this.y = y;
        sprite.setX(x-sprite.getWidth()/2);
        sprite.setY(y-sprite.getHeight()/2);
        sprite.setScale(0.2f);
        bitmapFont = ResourceManager.getInstance().get("Font");
        reproduce.start();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);

    }

    @Override
    public void draw() {
        super.draw();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin();
        shapeRenderer.circle(x,y,radius);
        shapeRenderer.end();
        batch.begin();
            bitmapFont.draw(batch,bacteriaNumber.toString(),x-bitmapFont.getSpaceWidth(),y+bitmapFont.getCapHeight()/2);
        batch.end();
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

    @Override
    protected void finalize() throws Throwable {
        reproduce.interrupt();
        super.finalize();
    }

    public Integer getBacteriaNumber() {
        return bacteriaNumber;
    }
}

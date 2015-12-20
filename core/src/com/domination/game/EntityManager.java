package com.domination.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Collection;

class Entity{
    public int x, y;
    public void update(){}
    public void render(SpriteBatch batch, Texture img){
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }
    Entity(int x, int y){
        this.x=x; this.y=y;
    }
}

public class EntityManager {

    Collection<Entity> entities;

    public void add(Entity addee){
        entities.add(addee);
    }

    public void remove(Entity removee){
        entities.remove(removee);
    }

    public void updateAll(){
        for(Entity ent : entities) ent.update();
    }

    public void renderAll(SpriteBatch batch, Texture img){
        for(Entity ent : entities) ent.render(batch, img);
    }

    EntityManager(){

    }
}

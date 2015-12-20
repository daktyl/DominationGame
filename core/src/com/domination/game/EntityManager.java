package com.domination.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

class Entity{
    public int x, y;
    public boolean update(){
        if(x==200) return true;
        else return false;
    }
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

    LinkedList<Entity> entities;

    public void add(Entity addee){
        entities.add(addee);
    }

    public void remove(Entity removee){
        entities.remove(removee);
    }

    public void updateAll(){
        for(Entity ent : entities){
            if(ent.update()) remove(ent);
        }
    }

    public void renderAll(SpriteBatch batch, Texture img){
        for(Entity ent : entities){
            ent.render(batch, img);
        }
    }

    EntityManager(){
        entities = new LinkedList<Entity>();
    }
}

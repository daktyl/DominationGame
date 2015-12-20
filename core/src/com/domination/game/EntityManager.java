package com.domination.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.Collection;

class Entity{
    Texture bitmap;
    public void update(){}
    public void render(){}
    Entity(Texture bitmap){
        this.bitmap=bitmap;
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

    public void renderAll(){
        for(Entity ent : entities) ent.render();
    }

    EntityManager(){

    }
}

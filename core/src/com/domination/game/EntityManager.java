package com.domination.game;

import java.util.Collection;

class Entity{
    public void update(){}
    public void render(){}
}

public class EntityManager {

    Collection<Entity> entities;

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

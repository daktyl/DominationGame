package com.domination.game;

import java.util.LinkedList;

class Entity{
    public void update(){}
    public void render(){}
}

public class EntityManager {

    LinkedList<Entity> entities;

    public void add(Entity addee){
        entities.add(addee);
    }

    public void remove(Entity removee){
        entities.remove(removee);
    }

    public void removeAll(){
        entities.clear();
    }

    public void updateAll(){
        for(Entity ent : entities) ent.update();
    }

    public void renderAll(){
        for(Entity ent : entities) ent.render();
    }

    EntityManager(){
        entities = new LinkedList<Entity>();
    }
}

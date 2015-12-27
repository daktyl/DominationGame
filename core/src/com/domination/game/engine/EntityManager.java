package com.domination.game.engine;
import com.domination.game.entities.Entity;
import java.util.LinkedList;

public class EntityManager {

    LinkedList<Entity> entities;

    public synchronized void add(Entity addee){
        entities.add(addee);
    }

    public synchronized void remove(Entity removee){
        entities.remove(removee);
    }

    public synchronized void removeAll(){
        entities.clear();
    }

    public synchronized void updateAll(){
        for (Entity ent : entities) ent.update();
    }

    public synchronized void drawAll(){
        for (Entity ent : entities) ent.draw();
    }

    public EntityManager(){
        entities = new LinkedList<Entity>();
    }
}

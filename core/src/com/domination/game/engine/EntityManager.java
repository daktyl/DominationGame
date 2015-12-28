package com.domination.game.engine;

import com.domination.game.entities.Entity;
import java.util.List;
import java.util.ArrayList;

public class EntityManager {
    private List<Entity> entities;

    public synchronized void add(Entity addee) {
        entities.add(addee);
    }
    public synchronized void add(List<Entity> entities){
        for(Entity e : entities)
            this.entities.add(e);
    }

    public synchronized void remove(Entity removee){
        entities.remove(removee);
    }

    public synchronized void removeAll(){
        entities.clear();
    }

    public synchronized void updateAll() {
        for (int i = 0; i<entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();
            if (entity.isBroken) {
                i++;
                entities.remove(entity);
            }
        }
    }

    public synchronized void drawAll(){
        for (Entity ent : entities) ent.draw();
    }

    public EntityManager(){
        entities = new ArrayList<Entity>();
    }
}

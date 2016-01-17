package com.domination.game.engine;

import com.domination.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<Entity>();
    }

    public synchronized void add(Entity addee) {
        entities.add(addee);
    }

    public synchronized void add(List<Entity> entities) {
        for (Entity e : entities)
            this.entities.add(e);
    }

    public synchronized void remove(Entity removee) {
        entities.remove(removee);
    }

    public synchronized void removeAll() {
        entities.clear();
    }

    public synchronized void updateAll() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();
            if (entity.isBroken()) {
                i++;
                entities.remove(entity);
            }
        }
    }

    public synchronized void drawAll() {
        for (Entity ent : entities) ent.draw();
    }
}

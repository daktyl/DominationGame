package com.domination.game.engine;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ResourceManager<T> {
    private HashMap<String, T> resourceMap;

    public void put(String path, T value) {
        resourceMap.put(path, value);
    }

    public T get(String key) {
        return resourceMap.get(key);
    }

    public int size() {
        return resourceMap.size();
    }

    public int find(String key) {

        return 0;
    }
}

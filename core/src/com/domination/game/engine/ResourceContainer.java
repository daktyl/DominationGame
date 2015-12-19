package com.domination.game.engine;

import java.util.ArrayList;

public class ResourceContainer<T> {
    private ArrayList<T> container = new ArrayList<T>();
    private int size = 0;

    public int put(T resource) {
        container.add(size, resource);
        return size++;
    }

    public T get(int index) {
        return container.get(index);
    }
}

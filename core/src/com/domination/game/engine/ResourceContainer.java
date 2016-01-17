package com.domination.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class ResourceContainer {
    private final List<Object> container;

    public ResourceContainer() {
        this.container = new ArrayList<Object>();
    }

    public Object add(Object resource) {
        container.add(resource);
        return resource;
    }

    public Object add(int index, Object resource) {
        container.add(index, resource);
        return resource;
    }

    public Object get(int index) {
        return container.get(index);
    }

    private Object remove(int index) {
        return container.remove(index);
    }

    public Object remove(Object resource) {
        return remove(find(resource));
    }

    public Object replace(Object replaceRef, Object resource) {
        int index = find(replaceRef);
        if (index == -1) {
            throw new NoSuchElementException("ResourceContainer::find() returned null.");
        }
        return container.set(index, resource);
    }

    private int find(Object resource) {
        for (int index = 0; index < container.size(); ++index) {
            if (resource == container.get(index)) {
                return index;
            }
        }
        return -1;
    }

    private int size() {
        return container.size();
    }

    public boolean empty() {
        return (size() == 0);
    }

    public List getRawContainer() {
        return container;
    }

    @SuppressWarnings("unchecked")
    public Class<Object> getResourceType() {
        if (!container.isEmpty()) {
            return (Class<Object>) container.get(0).getClass();
        }
        return null;
    }
}

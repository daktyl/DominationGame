package com.domination.game.engine;

import java.util.List;
import java.util.ArrayList;

public class ResourceContainer {
    private List<Object> container;

    public ResourceContainer() {
        this.container = new ArrayList<Object>();
    }

    public Object add(Object resource) {
        return add(container.size(), resource);
    }

    public Object add(int index, Object resource) {
        container.add(index, resource);
        return resource;
    }

    public Object get(int index) {
        return container.get(index);
    }

    public Object remove(int index) {
        return container.remove(index);
    }

    public Object remove(Object resource) {
        return remove(find(resource));
    }

    public Object replace(Object replaceRef, Object resource) {
        int index = find(replaceRef);
        //TODO If -1 throw exception
        return container.set(index, resource);
    }

    public int find(Object resource) {
        for (int index = 0; index < container.size(); ++index) {
            if (resource == container.get(index)) {
                return index;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public Class<Object> getResourceType() {
        if (!container.isEmpty()) {
            return (Class<Object>)container.get(0).getClass();
        }
        return null;
    }
}

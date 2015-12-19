package com.domination.game.engine;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {
    private List<ResourceContainer> containersList;
    public Map<String, Object> resourceMap;

    public ResourceManager() {
        this.containersList = new ArrayList<ResourceContainer>();
        this.resourceMap = new HashMap<String, Object>();
    }

    public ResourceContainer getCorrectContainer(Object resource) {
        for (ResourceContainer container: containersList) {
            if (container.getResourceType() == resource.getClass()) {
                return container;
            }
        }
        return null;
    }

    public Object add(String key, Object resource, Object... override) {
        if (override.length > 1) {
            throw new IllegalArgumentException("Only one additional boolean parameter is allowed there.");
        }
        ResourceContainer container = getCorrectContainer(resource);
        if (container == null) {    // If there is no container for particular object type
            container = new ResourceContainer();
            containersList.add(container);
        }
        if (resourceMap.containsKey(key)) {
            if (override.length > 0 && (Boolean)override[0]) {
                container.replace(get(key), resource);
                resourceMap.put(key, resource);
                return resource;
            }
            else {
                throw new KeyAlreadyExistsException("To override a key, write 'true' as a third parameter of the 'add' function");
            }
        }
        container.add(resource);
        resourceMap.put(key, resource);
        return resource;
    }

    public Object get(String key) {
        if (resourceMap.containsKey(key)) {
            return resourceMap.get(key);
        }
        else {
            throw new InvalidKeyException();
        }
    }

    public Object remove(String key) {
        if (resourceMap.containsKey(key)) {
            Object resource = get(key);
            ResourceContainer container = getCorrectContainer(resource);
            container.remove(resource);
            resourceMap.remove(key);
            return resource;
        }
        else {
            throw new InvalidKeyException();
        }
    }

    public void removeAll() {
        resourceMap.clear();
        containersList.clear();
    }

}

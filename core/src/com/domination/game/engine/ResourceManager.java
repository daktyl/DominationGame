package com.domination.game.engine;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

//        ResourceManager.getInstance().add("cell", new Texture("cell.jpg"));              - adds a Texture with "cell" as a key, will throw an exception when the key already exists
//        ResourceManager.getInstance().add("cell", new Texture("cell.jpg"), true);        - adds a Texture with "cell" as a key, will override when the key already exists
//        ResourceManager.getInstance().replace("cell", new Texture("cell.jpg"));          - adds a Texture with "cell" as a key, will override when the key already exists
//        Texture cell = ResourceManager.getInstance().get("cell");                        - gets a Texture with "cell" as a key (recommended)
//        Texture cell = ResourceManager.getInstance().add("cell, new Texture("cell.jpg")) - adds a Texture with "cell" as a key and save its reference to the variable
//        ResourceManager.getInstance().remove("cell");                                    - removes an Object with "cell" as a key
//        ResourceManager.getInstance().removeAll(Texture.class);                          - removes all Textures
//        ResourceManager.getInstance().getAll(Texture.class);                             - returns an ArrayList with all Textures
//        ResourceManager.getInstance().clear()                                            - removes everything from the ResourceManager

public class ResourceManager {
    private static final ResourceManager instance = new ResourceManager();
    private final List<ResourceContainer> containersList;
    private final Map<String, Object> resourceMap;

    private ResourceManager() {
        this.containersList = new ArrayList<ResourceContainer>();
        this.resourceMap = new HashMap<String, Object>();
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    private ResourceContainer getCorrectContainer(Class<?> resourceType) {
        for (ResourceContainer container : containersList) {
            if (container.getResourceType() == resourceType) {
                return container;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public synchronized <T> T add(String key, Object resource, Object... override) {
        if (override.length > 1) {
            throw new IllegalArgumentException("Only one additional boolean parameter is allowed there.");
        }
        ResourceContainer container = getCorrectContainer(resource.getClass());
        if (container == null) {    // If there is no container for particular object type
            container = new ResourceContainer();
            containersList.add(container);
        }
        if (resourceMap.containsKey(key)) {
            if (override.length > 0 && (Boolean) override[0]) {
                Object oldRef = get(key);
                if (oldRef.getClass() != resource.getClass()) {
                    remove(key);
                    container.add(resource);
                } else {
                    container.replace(get(key), resource);
                }
                resourceMap.put(key, resource);
                return (T) resource;
            } else {
                throw new KeyAlreadyExistsException("To override a key, write 'true' as a third parameter of the 'add' function.");
            }
        }
        container.add(resource);
        resourceMap.put(key, resource);
        return (T) resource;
    }

    private Object getObject(String key) {
        if (resourceMap.containsKey(key)) {
            return resourceMap.get(key);
        } else {
            throw new InvalidKeyException();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (resourceMap.containsKey(key)) {
            return (T) resourceMap.get(key);
        } else {
            throw new InvalidKeyException(key);
        }
    }

    @SuppressWarnings("unchecked")
    private synchronized <T> T remove(String key) {
        if (resourceMap.containsKey(key)) {
            Object resource = getObject(key);
            ResourceContainer container = getCorrectContainer(resource.getClass());
            if (container == null) {
                throw new NoSuchElementException("Container was null in ResourceManager::remove() function.");
            }
            container.remove(resource);
            resourceMap.remove(key);
            if (container.empty()) {
                containersList.remove(container);
            }
            return (T) resource;
        } else {
            throw new InvalidKeyException();
        }
    }

    public <T> T replace(String key, Object resource) {
        return add(key, resource, true);
    }

    public synchronized void clear() {
        resourceMap.clear();
        containersList.clear();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<T> type) {
        ResourceContainer container = getCorrectContainer(type);
        if (container == null) {
            throw new NoSuchElementException("getCorrectContainer() at ResourceManager::getAll() returned null.");
        }
        return container.getRawContainer();
    }

    public synchronized void removeAll(Class<?> type) {
        ResourceContainer container = getCorrectContainer(type);
        containersList.remove(container);

    }
}

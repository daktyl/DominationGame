package com.domination.game.containers;

import java.util.HashMap;

public class BidiHashMap<K, V> extends HashMap<K, V> {
    public K getKey(V value) {
        for (K key : keySet()) {
            if (get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }
}

package jackson.rick;

import static java.util.Objects.hash;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Created by rickjackson on 2/22/17.
 */
public class MyMap<K, V> {
    static final int DEFAULT_START_SIZE = 16;
    static final int MAXIMUM_SIZE = Integer.MAX_VALUE - 16;
    static final float DEFAULT_LOAD =  0.75f;
    private Pair<K, V>[] table;
    private Set<Pair<K, V>> pair;
    private int size;
    int nextResize;
    final float load;
    
    static final int tableSizeFor(int size) {
        int n = size - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_SIZE) ? MAXIMUM_SIZE : n + 1;
    }
    
    public MyMap(int initialSize, float load) {
        if (initialSize < 0) {
            throw new IllegalArgumentException();
        }
        
        if (initialSize > MAXIMUM_SIZE) {
            initialSize = MAXIMUM_SIZE;
        }
        
        if (load <= 0 || Float.isNaN(load)) {
            throw new IllegalArgumentException();
        }
        this.load = load;
        this.nextResize = tableSizeFor(initialSize);
    }
    
    public MyMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD);
    }
    
    public MyMap() {
        this.load = DEFAULT_LOAD;
    }
    
    public MyMap(Map<? extends K, ? extends V> m) {
        this.load = DEFAULT_LOAD;
        putMapPairs(m, false);
    }
    
    public void putMapPairs(Map<? extends K, ? extends V> map, boolean remove) {
        int size = map.size();
        
        if (size > 0) {
            if (table == null) {
                float f = ((float) size / load) + 1.0f;
                int nr = ((f < (float) MAXIMUM_SIZE) ? (int) f : MAXIMUM_SIZE);
                
                if (nr > nextResize) {
                    nextResize = tableSizeFor(nr);
                } else if (size > nextResize) {
                    resize();
                }
                
                for (MyMap.Pair<? extends K, ? extends V> mp : map.pairSet()) {
                    K k = mp.getKey();
                    V v = mp.getValue();
                    putValue(hash(k), k, v, false, evict);
                }
            }
        }
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public boolean containsKey(Object key) {
        return getPair(hash(key), key) != null;
    }
    
    public boolean containsValue(Object value) {
        Pair<K, V>[] table = this.table;
        V v;
        
        if (table != null && size > 0) {
            for (int i = 0; i < table.length; ++i) {
                Pair<K, V> pair = table[i];
                
                for(; pair != null; pair = pair.next) {
                    v = pair.v;
                    
                    if (v == value || (value != null && value.equals(v))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public V get(Object key) {
        Pair<K, V> p;
        
        return (p = getPair(hash(key), key)) == null ? null : p.v;
    }
    
    public V put(K key, V value) {
        return putValue(hash(key), key, value, false, true);
    }
    
    public V putValue(int hash, K key, V value, boolean onlyIfAbsent, boolean
            evict) {
        Pair<K, V>[] table = this.table;
        Pair<K, V> p;
        int n = table.length;
        int i;

        if (table == null || n == 0) {
            table = resize();
            n = table.length;
        }
        i = (n - 1) & hash;
        p = table[i];

        if (p == null) {
            table[i] = newPair(hash, key, value, null);
        } else {
            Pair<K, V> pair;
            K k = p.k;

            if ((p.hashCode() == this.hashCode()) &&
                (k == key || (key != null && key.equals(k)))) {
                pair = p;
            } else {
                for (int count = 0; ; ++count) {
                    pair = p.next;

                    if (pair == null) {
                        p.next = newPair(hash, key, value, null);

                        if (count >= this.nextResize -1) {
                            resize();
                        }
                        break;
                    }
                    k = pair.k;
                    
                    if (pair.hashCode() == this.hashCode() &&
                        (k == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    p = pair;
                }
            }
    
            if (pair != null) {
                V old = pair.v;
                
                if (old == null) {
                    pair.v = value;
                }
                return old;
            }
        }
        size++;
        
        if (size > nextResize) {
            resize();
        }
        return null;
    }
    
    public V remove(Object key) {
        return null;
    }
    
    public void putAll(Map<? extends K, ? extends V> m) {
        
    }
    
    public void clear() {
        Pair<K, V>[] table = this.table;
        
        if (table != null && size > 0) {
            size = 0;
            
            for (int i = 0; i < table.length; ++i) {
                table[i] = null;
            }
        }
    }
    
    public Set<K> keySet() {
        return null;
    }
    
    public Collection<V> values() {
        return null;
    }
    
    public Set<MyMap.Pair<K, V>> pairSet() {
        return null;
    }
    
    public boolean equals(Object o) {
        return false;
    }
    
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V>
                                   function) {
        
    }
    
    public V putIfAbsent(K key, V value) {
        return null;
    }
    
    public boolean remove(Object key, Object value) {
        return false;
    }
    
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }
    
    public V replace(K key, V value) {
        return null;
    }
    
    public int hashCode() {
        return this.hashCode();
    }
    
    public Pair<K, V> getPair(int code, Object key) {
        int n;
        Pair<K, V>[] table = this.table;
        Pair<K, V> first = table[(n - 1) & code];
        Pair<K, V> p;
        K k;

        if ((table != null) && (n > 0) && (first != null)) {
            k = first.k;
            
            if ((first.code == code) &&
                (k == key) || (key != null && key.equals(k))) {
                return first;
            }
            p = first.next;
            
            if (p != null) {
                // if (first instanceof Pair) {
                //     return ((Pair<K, V>) first).getTreePair(code, key);
                // }
                
                do {
                    if (p.code == code &&
                        ((k = p.k) == key || (key != null & key.equals(k)))) {
                        return p;
                    }
                } while ((p = p.next) != null);
            }
        }
        return null;
    }
    
    static class Pair<K, V> implements Map.Entry<K, V> {
        final int code;
        final K k;
        V v;
        Pair<K, V> next;
        
        Pair(int code, K key, V value, Pair<K, V> next) {
            this.code = code;
            this.k = key;
            this.v = value;
            this.next = next;
        }
        
        public final K getKey() {
            return k;
        }
        
        public final V getValue() {
            return v;
        }
        
        public final V setValue(V newV) {
            V oldV = v;
            v = newV;
            return oldV;
        }
        
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> me = (Map.Entry<?, ?>) o;
                
                if (Objects.equals(k, me.getKey()) &&
                    Objects.equals(v, me.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }
}

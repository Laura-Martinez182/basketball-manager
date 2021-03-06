package tree;

import java.util.List;

public interface TreeInterface<K, V extends Comparable<V>> {

    public boolean isEmpty();

    public V root() throws TreeException;

    public void insert(K k, V v);

    public boolean containsKey(K k);

    public boolean containsValue(V v);

    public void remove(K k) throws TreeException;

    public V minimum() throws TreeException;

    public V maximum() throws TreeException;

    public List<K> getKeysEqualTo(V value);

    public List<K> getKeysInRange(V min, V max);

    public String preOrder();

    public String inOrder();

    public String posOrder();

}

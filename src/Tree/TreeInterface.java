package Tree;

public interface TreeInterface<K, V extends Comparable<V>> {

    public V root();

    public void insert(K k, V v);

    public boolean containsKey(K k);

    public boolean containsValue(V v);

    public void remove(K k);

    public V minimum();

    public V maximum();

    public String inorder();

}

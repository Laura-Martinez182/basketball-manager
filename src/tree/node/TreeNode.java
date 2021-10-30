package tree.node;

public class TreeNode<K, V extends Comparable<V>> implements Comparable<V> {

    private K key;
    private V value;
    private TreeNode<K, V> parent;
    private TreeNode<K, V> left;
    private TreeNode<K, V> right;

    public TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        parent = null;
        left = null;
        right = null;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K key() {
        return key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V value() {
        return value;
    }

    public void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }

    public TreeNode<K, V> parent() {
        return parent;
    }

    public void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    public TreeNode<K, V> left() {
        return left;
    }

    public void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    public TreeNode<K, V> right() {
        return right;
    }

    @Override
    public int compareTo(V o) {
        return value.compareTo(o);
    }

}

package tree;

import tree.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<K, V extends Comparable<V>> implements TreeInterface<K, V> {

    private TreeNode<K, V> root;

    public BinarySearchTree() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    public int height() {
        return calcHeight(root);
    }

    protected int calcHeight(TreeNode<K, V> subTree) {
        if(subTree == null)
            return 0;
        else
            return 1 + Math.max(calcHeight(subTree.left()), calcHeight(subTree.right()));
    }

    public TreeNode<K, V> getRootNode() {
        return root;
    }

    protected void setRootNode(TreeNode<K, V> root) {
        this.root = root;
    }

    @Override
    public V root() throws TreeException {
        if(root == null)
            throw new TreeException();
        return root.value();
    }

    @Override
    public synchronized void insert(K k, V v) throws TreeException {
        TreeNode<K, V> toAdd = new TreeNode<>(k, v);
        if(containsKey(k))
            throw new TreeException("Could not insert the node because the key is already in use");
        if(root == null)
            root = toAdd;
        else
            insert(root, toAdd);
    }

    private synchronized void insert(TreeNode<K, V> aux, TreeNode<K, V> toAdd) {
        if(toAdd.compareTo(aux.value()) >= 0) {
            if(aux.right() == null) {
                aux.setRight(toAdd);
                toAdd.setParent(aux);
            } else {
                insert(aux.right(), toAdd);
            }
        } else {
            if(aux.left() == null) {
                aux.setLeft(toAdd);
                toAdd.setParent(aux);
            } else {
                insert(aux.left(), toAdd);
            }
        }
    }

    protected TreeNode<K, V> searchNodeByKey(TreeNode<K, V> aux, K k) {
        if(aux == null || aux.key() == k)
            return aux;
        TreeNode<K, V> left = searchNodeByKey(aux.left(), k);
        TreeNode<K, V> right = searchNodeByKey(aux.right(), k);
        if(left != null && left.key() == k)
            return left;
        else
            return right;
    }

    @Override
    public boolean containsKey(K k) {
        return (searchNodeByKey(root, k) != null);
    }

    protected TreeNode<K, V> searchNodeByValue(TreeNode<K, V> aux, V v) {
        if(aux == null || aux.value() == v) {
            return aux;
        } else {
            if(v.compareTo(aux.value()) >= 0)
                return searchNodeByValue(aux.right(), v);
            else
                return searchNodeByValue(aux.left(), v);
        }
    }

    @Override
    public boolean containsValue(V v) {
        return (searchNodeByValue(root, v) != null);
    }

    @Override
    public void remove(K k) throws TreeException {
        if(isEmpty())
            throw new TreeException();
        TreeNode<K, V> toRemove = searchNodeByKey(root, k);
        if(toRemove == null)
            throw new TreeException();
        else
            remove(toRemove);
    }

    private void transplant(TreeNode<K, V> nodeU, TreeNode<K, V> nodeV) {
        if(nodeU.parent() == null)
            root = nodeV;
        else if(nodeU == nodeU.parent().left())
            nodeU.parent().setLeft(nodeV);
        else
            nodeU.parent().setRight(nodeV);
        if(nodeV != null)
            nodeV.setParent(nodeU.parent());
    }

    protected synchronized TreeNode<K, V> remove(TreeNode<K, V> toRemove) {
        TreeNode<K, V> parent = toRemove.parent();
        if(toRemove.left() == null) {
            transplant(toRemove, toRemove.right());
            return parent;
        } else if(toRemove.right() == null) {
            transplant(toRemove, toRemove.left());
            return parent;
        } else {
            TreeNode<K, V> min = successor(toRemove);
            if(min.parent() != toRemove) {
                transplant(min, min.right());
                min.setRight(toRemove.right());
                min.right().setParent(min);
            }
            transplant(toRemove, min);
            min.setLeft(toRemove.left());
            min.left().setParent(min);
            return min.right();
        }
    }

    private TreeNode<K, V> predecessor(TreeNode<K, V> node) {
        if(node.left() != null)
            return maxNode(node);
        TreeNode<K, V> aux = node.parent();
        while(aux != null && node == aux.left()) {
            node = aux;
            aux = aux.parent();
        }
        return aux;
    }

    private TreeNode<K, V> successor(TreeNode<K, V> node) {
        if(node.right() != null)
            return minNode(node.right());
        TreeNode<K, V> aux = node.parent();
        while(aux != null && node == aux.right()) {
            node = aux;
            aux = aux.parent();
        }
        return aux;
    }

    @Override
    public V minimum() throws TreeException {
        if(isEmpty())
            throw new TreeException();
        return minNode(root).value();
    }

    private TreeNode<K, V> minNode(TreeNode<K, V> subTree) {
        while(subTree.left() != null)
            subTree = subTree.left();
        return subTree;
    }

    @Override
    public V maximum() throws TreeException {
        if(isEmpty())
            throw new TreeException();
        return maxNode(root).value();
    }

    private TreeNode<K, V> maxNode(TreeNode<K, V> subTree) {
        while(subTree.right() != null)
            subTree = subTree.right();
        return subTree;
    }

    @Override
    public List<K> getKeysEqualTo(V value) {
        List<K> list = new ArrayList<>();
        return getKeysEqualTo(list, root, value);
    }

    private List<K> getKeysEqualTo(List<K> list, TreeNode<K, V> current, V value) {
        if(current != null) {
            if(current.value().compareTo(value) == 0) {
                list.add(current.key());
            }
            getKeysEqualTo(list, current.left(), value);
            getKeysEqualTo(list, current.right(), value);
        }
        return list;
    }

    @Override
    public List<K> getKeysInRange(V min, V max) {
        List<K> list = new ArrayList<>();
        return getKeysInRange(list, root, min, max);
    }

    private List<K> getKeysInRange(List<K> list, TreeNode<K, V> current, V min, V max) {
        if(current != null) {
            if(current.value().compareTo(min) >= 0 && current.value().compareTo(max) <= 0) {
                list.add(current.key());
            }
            getKeysInRange(list, current.left(), min, max);
            getKeysInRange(list, current.right(), min, max);
        }
        return list;
    }

    public void changeKey(K o, K n) {
        TreeNode<K, V> node = searchNodeByKey(root, o);
        if(node != null)
            node.setKey(n);
    }

    @Override
    public String preOrder() {
        return preOrder(root);
    }

    public String preOrder(TreeNode<K, V> aux) {
        String info = "";
        if(aux != null) {
            info += aux.value().toString() + "\n";
            info += preOrder(aux.left());
            info += preOrder(aux.right());
        }
        return info;
    }

    @Override
    public String inOrder() {
        return inOrder(root);
    }

    private String inOrder(TreeNode<K, V> aux) {
        String info = "";
        if(aux != null) {
            info += inOrder(aux.left());
            info += aux.value().toString() + "\n";
            info += inOrder(aux.right());
        }
        return info;
    }

    @Override
    public String posOrder() {
        return posOrder(root);
    }

    private String posOrder(TreeNode<K, V> aux) {
        String info = "";
        if(aux != null) {
        	info += posOrder(aux.left());
            info += posOrder(aux.right());
            info += aux.value().toString() + "\n";
        }
        return info;
    }

}

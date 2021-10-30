package tree;

import tree.node.TreeNode;

public class BinarySearchTree<K, V extends Comparable<V>> implements TreeInterface<K, V> {

    private TreeNode<K, V> root;

    public BinarySearchTree() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public V root() throws TreeException {
        if(root == null)
            throw new TreeException();
        return root.value();
    }

    @Override
    public void insert(K k, V v) {
        TreeNode<K, V> toAdd = new TreeNode<>(k, v);
        if(root == null)
            root = toAdd;
        else
            insert(root, toAdd);
    }

    private void insert(TreeNode<K, V> aux, TreeNode<K, V> toAdd) {
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

    private TreeNode<K, V> searchNodeByKey(TreeNode<K, V> aux, K k) {
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

    private TreeNode<K, V> searchNodeByValue(TreeNode<K, V> aux, V v) {
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

    private void remove(TreeNode<K, V> toRemove) {
        if(toRemove.left() == null)
            transplant(toRemove, toRemove.right());
        else if(toRemove.right() == null)
            transplant(toRemove, toRemove.left());
        else {
            TreeNode<K, V> min = minNode(toRemove.right());
            if(min.parent() != toRemove) {
                transplant(min, min.right());
                min.setRight(toRemove.right());
                min.right().setParent(min);
            }
            transplant(toRemove, min);
            min.setLeft(toRemove.left());
            min.left().setParent(min);
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
    public String inorder() {
        return inorder(root);
    }

    private String inorder(TreeNode<K, V> aux) {
        String info = "";
        if(aux != null) {
            info += inorder(aux.left());
            info += aux.value().toString() + "\n";
            info += inorder(aux.right());
        }
        return info;
    }

}

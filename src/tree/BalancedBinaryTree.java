package tree;

import tree.node.TreeNode;

public class BalancedBinaryTree<K, V extends Comparable<V>> extends BinarySearchTree<K, V> {

    public BalancedBinaryTree() {
        super();
    }

    @Override
    public void insert(K k, V v) {
        TreeNode<K, V> toAdd = new TreeNode<>(k, v);
        if(super.getRootNode() == null)
            super.setRootNode(toAdd);
        else
            insert(super.getRootNode(), toAdd);
    }

    private void insert(TreeNode<K, V> aux, TreeNode<K, V> toAdd) {
        if(toAdd.compareTo(aux.value()) >= 0) {
            if(aux.right() == null) {
                aux.setRight(toAdd);
                toAdd.setParent(aux);
                rebalance(aux.parent());
            } else {
                insert(aux.right(), toAdd);
            }
        } else {
            if(aux.left() == null) {
                aux.setLeft(toAdd);
                toAdd.setParent(aux);
                rebalance(aux.parent());
            } else {
                insert(aux.left(), toAdd);
            }
        }
    }

    @Override
    public void remove(K k) throws TreeException {
        if(super.isEmpty())
            throw new TreeException();
        TreeNode<K, V> toRemove = super.searchNodeByKey(super.getRootNode(), k);
        if(toRemove == null)
            throw new TreeException();
        else {
            rebalance(super.remove(toRemove));
        }
    }

    public int balanceFactor(TreeNode<K, V> subTree) {
        int balanceFactor = 0;
        if(subTree != null) {
            int rightBF = calcHeight(subTree.right());
            int leftBF = calcHeight(subTree.left());
            balanceFactor = rightBF - leftBF;
        }
        return balanceFactor;
    }

    private void leftRotate(TreeNode<K, V> subTree) {
        TreeNode<K, V> node = subTree.right();
        subTree.setRight(node.left());
        if(node.left() != null)
            node.left().setParent(subTree);
        node.setParent(subTree.parent());
        if(subTree.parent() == null)
            super.setRootNode(node);
        else if(subTree == subTree.parent().left())
            subTree.parent().setLeft(node);
        else
            subTree.parent().setRight(node);
        node.setLeft(subTree);
        subTree.setParent(node);
    }

    private void rightRotate(TreeNode<K, V> subTree) {
        TreeNode<K, V> node = subTree.left();
        subTree.setLeft(node.right());
        if(node.right() != null)
            node.right().setParent(subTree);
        node.setParent(subTree.parent());
        if(subTree.parent() == null)
            super.setRootNode(node);
        else if(subTree == subTree.parent().right())
            subTree.parent().setRight(node);
        else
            subTree.parent().setLeft(node);
        node.setRight(subTree);
        subTree.setParent(node);
    }

    private void rebalance(TreeNode<K, V> subTree) {
        while(subTree != null) {
            int bf = balanceFactor(subTree);
            if (Math.abs(bf) > 1) {
                if (bf == 2) {
                    int rbf = balanceFactor(subTree.right());
                    if (rbf == 0 || rbf == 1) {
                        leftRotate(subTree);
                    } else {
                        rightRotate(subTree.right());
                        leftRotate(subTree);
                    }
                } else {
                    int lbf = balanceFactor(subTree.left());
                    if (lbf == -1 || lbf == 0) {
                        rightRotate(subTree);
                    } else {
                        leftRotate(subTree.left());
                        rightRotate(subTree);
                    }
                }
            }
            subTree = subTree.parent();
        }
    }

}

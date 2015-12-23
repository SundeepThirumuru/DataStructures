import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 18/12/15
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinarySearchTree<T> {

    public class Node<T>
    {
        private T value;
        private Node<T> left = null, right = null;
        private Node<T> parent = null;
        public String toString()
        {
            return value.toString();
        }
    }

    private Node<T> root;

    public BinarySearchTree()
    {
        root =  null;
    }

    public Node<T> getParent(T value)
    {
        Node<T> curNode = root;
        while (true)
        {
            if(((Comparable)curNode.value).equals(value))
            {
                return curNode.parent;
            }
            else if(((Comparable)curNode.value).compareTo(value) > 0)
            {
                if(curNode.left == null)
                {
                    return curNode;
                }
                curNode = curNode.left;
            }
            else
            {
                if(curNode.right == null)
                {
                    return curNode;
                }
                curNode = curNode.right;
            }
        }
    }

    public boolean contains(T value)
    {
        if(root == null)
        {
            return false;
        }

        Node<T> parent = getParent(value);
        if(parent == null)
        {
            return false;
        }
        else
        {
            if(((Comparable)parent.value).compareTo(value) > 0)
            {
                return parent.left != null;
            }
            else
            {
                return parent.right != null;
            }
        }
    }


    public void add(T value)
    {
        if(root == null)
        {
            root  = new Node<T>();
            root.value = value;
        }

        Node<T> parent = getParent(value);
        if(parent == null)
        {
            root.value = value;
            return;
        }
        else
        {
            if(((Comparable)parent.value).compareTo(value) > 0)
            {
                if(parent.left == null)
                {
                    parent.left = new Node<T>();
                }
                parent.left.value = value;
            }
            else
            {
                if(parent.right == null)
                {
                    parent.right = new Node<T>();
                }
                parent.right.value = value;
            }
        }
    }

    public Node<T> getPredecessor(Node<T> node)
    {
        if(node == null)
        {
            return null;
        }

        Node<T> leftChild = node.left;

        if(leftChild == null)
        {
            if(node.parent != null && node.parent.right == node)
            {
                return node.parent;
            }
            return null;
        }

        if(leftChild.right == null)
        {
            return leftChild;
        }
        Node<T> rightMostChildOfLeftChild = leftChild.right;
        while (rightMostChildOfLeftChild.right != null)
        {
            rightMostChildOfLeftChild = rightMostChildOfLeftChild.right;
        }

        return rightMostChildOfLeftChild;
    }

    public Node<T> getSuccessor(Node<T> node)
    {
        if(node == null)
        {
            return null;
        }

        if(node.right == null)
        {
            if(node.parent != null && node.parent.left == node)
            {
                return node.parent;
            }
            return null;
        }

        Node<T> rightChild = node.right;
        if(rightChild.left == null)
        {
            return rightChild;
        }
        Node<T> leftMostChildOfRightChild = rightChild.left;
        while (leftMostChildOfRightChild.left != null)
        {
            leftMostChildOfRightChild = leftMostChildOfRightChild.left;
        }
        return leftMostChildOfRightChild;
    }


    public List<T> doInOrderTraversal()
    {
        List<T> values = new LinkedList<T>();
        doInOrderTraversal(root, values);
        return values;
    }

    public void doInOrderTraversal(Node<T> rootNode, List<T> values)
    {
        if(rootNode == null) return;
        doInOrderTraversal(rootNode.left, values);
        values.add(rootNode.value);
        doInOrderTraversal(rootNode.right, values);
    }

    public List<T> doPreOrderTraversal()
    {
        List<T> values = new LinkedList<T>();
        doPreOrderTraversal(root, values);
        return values;
    }

    private void doPreOrderTraversal(Node<T> rootNode, List<T> values)
    {
        if(rootNode == null) return;
        values.add(rootNode.value);
        doPreOrderTraversal(rootNode.left, values);
        doPreOrderTraversal(rootNode.right, values);
    }

    public List<T> doPostOrderTraversal()
    {
        List<T> values = new LinkedList<T>();
        doPostOrderTraversal(root, values);
        return values;
    }

    private void doPostOrderTraversal(Node<T> rootNode, List<T> values)
    {
        if(rootNode == null) return;
        doPostOrderTraversal(rootNode.left, values);
        doPostOrderTraversal(rootNode.right, values);
        values.add(rootNode.value);
    }

    public List<T> doLevelWalk()
    {
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        List<T> values = new LinkedList<T>();
        if(root == null) return values;
        queue.add(root);
        while(!queue.isEmpty())
        {
            Node<T> curNode = queue.remove();
            values.add(curNode.value);
            if(curNode.left != null)
            {
                queue.add(curNode.left);
            }
            if(curNode.right != null)
            {
                queue.add(curNode.right);
            }
        }
        return values;
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        bst.add(3);
        bst.add(2);
        bst.add(1);
        bst.add(4);
        bst.add(5);
        System.out.println(bst.doInOrderTraversal());
        System.out.println(bst.doPreOrderTraversal());
        System.out.println(bst.doPostOrderTraversal());
        System.out.println(bst.doLevelWalk());
    }
}

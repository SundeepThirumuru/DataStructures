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
        private int depth = 1;
        public String toString()
        {
            return value.toString();
        }

        public int getDepth()
        {
            return depth;
        }

        public void updateDepth()
        {
            int leftDepth = left != null ? left.getDepth() + 1 : 1;
            int rightDepth = right != null ? right.getDepth() + 1 : 1;
            depth = Math.max(leftDepth, rightDepth);
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
            if(((Comparable)curNode.value).compareTo(value) == 0)
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
            return ((Comparable)root.value).compareTo(value) == 0;
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
                    parent.left.parent = parent;
                }
                parent.left.value = value;
            }
            else
            {
                if(parent.right == null)
                {
                    parent.right = new Node<T>();
                    parent.right.parent = parent;
                }
                parent.right.value = value;
            }
            fixDepthProperty(parent);
        }
    }

    public int getDepth(Node<T> node)
    {
        return node != null ? node.getDepth() : 0;
    }

    public void fixDepthProperty(Node<T> node)
    {
        while(node != null)
        {
            node.updateDepth();
            Node<T> parent = node.parent;
            if(getDepth(node.left) > (getDepth(node.right) + 1))
            {
                if(getDepth(node.left.right) > getDepth(node.left.left))
                {

                    rotateAntiClockwise(node.left);
                    assert (root.parent == null);
                }
                rotateClockwise(node);
            }
            else if(getDepth(node.right) > (getDepth(node.left) + 1))
            {
                if(getDepth(node.right.left) > getDepth(node.right.right))
                {
                    rotateClockwise(node.right);
                    assert (root.parent == null);
                }
                rotateAntiClockwise(node);
                assert (root.parent == null);
            }
            node = parent;
            assert (root.parent == null);
        }
    }

    private void rotateAntiClockwise(Node<T> node)
    {
        boolean isRoot = node == root;
        boolean isNodeLeftChild = node.parent != null ? node.parent.left == node : false;
        Node<T> rightChild = node.right;
        rightChild.parent = node.parent;
        if(rightChild.parent != null)
        {
            if(isNodeLeftChild)
            {
                rightChild.parent.left = rightChild;
            }
            else
            {
                rightChild.parent.right = rightChild;
            }
        }

        node.parent = rightChild;
        node.right = rightChild.left;
        rightChild.left = node;

        if(node.right != null)
        {
            node.right.parent = node;
        }

        node.updateDepth();
        rightChild.updateDepth();
        if(isRoot)
        {
            root = rightChild;
            assert (rightChild.parent == null);
        }
    }

    private void rotateClockwise(Node<T> node)
    {
        boolean isRoot = node == root;
        boolean isNodeLeftChild = node.parent != null ? node.parent.left == node : false;
        Node<T> leftChild = node.left;
        leftChild.parent  = node.parent;
        if(leftChild.parent != null)
        {
            if(isNodeLeftChild)
            {
                leftChild.parent.left = leftChild;
            }
            else
            {
                leftChild.parent.right = leftChild;
            }
        }
        node.parent = leftChild;
        node.left = leftChild.right;
        leftChild.right = node;
        if(node.left != null)
        {
            node.left.parent = node;
        }

        node.updateDepth();
        leftChild.updateDepth();
        if(isRoot)
        {
            root = leftChild;
            assert (root.parent == null);
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

        List<Integer> input = new ArrayList<Integer>();
        int maxNum = 10000000;
        for(int i=0; i < maxNum; i++)
        {
            input.add((int)(Math.random() * maxNum));
        }

        Set<Integer> inputSet = new LinkedHashSet<Integer>(input);
        System.out.println("Input size: " + inputSet.size());
//        System.out.println("Input: " + inputSet);

        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
//        bst.add(5);
//        bst.add(4);
//        bst.add(3);
//        bst.add(2);
//        bst.add(1);
       // Insertion
        List<Integer> list = new ArrayList<Integer>(maxNum);
        long startTime = System.currentTimeMillis();
        for(Integer value : input)
        {
            list.add(value);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion on a list: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for(Integer value : input)
        {
            bst.add(value);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion on a bst: " + (endTime - startTime) + " ms");

        TreeSet<Integer> javaBst = new TreeSet<Integer>();
        startTime = System.currentTimeMillis();
        for(Integer value : input)
        {
            javaBst.add(value);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion on a java bst: " + (endTime - startTime) + " ms");

        int aNum = (int)(Math.random() * maxNum);
        startTime = System.currentTimeMillis();
        list.contains(aNum);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for retrieval on a list: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        bst.contains(aNum);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for retrieval on a bst: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        javaBst.contains(aNum);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for retrieval on a java bst: " + (endTime - startTime) + " ms");
        System.out.println("bst size: " + bst.doInOrderTraversal().size());
        System.out.println("java bst size: " + javaBst.size());
//        System.out.println("Pre order traversal: " + bst.doPreOrderTraversal());
//        System.out.println("Post order traversal: " + bst.doPostOrderTraversal());
//        System.out.println("Level walk: " + bst.doLevelWalk());
    }
}

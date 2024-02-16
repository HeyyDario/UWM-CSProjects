// --== CS400 Fall 2023 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group: C14
// TA: Matthew Schwennesen
// Lecturer: Florian Heimerl
// Notes to Grader: For convenience of tests, I overrode the toInOrderString() and
// toLevelOrderString() that originally used in BinarySearchTree.java()

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class IterableMultiKeyRBT<KeyType extends Comparable<KeyType>>
        extends RedBlackTree<KeyListInterface<KeyType>>
        implements IterableMultiKeySortedCollectionInterface<KeyType> {
    //Do I need to add <T extends Comparable<T>> behind RedBlackTree and Iterable...
    private Comparable startPoint; // store the iteration start point
    private int numKeys; // store the number of keys in our tree

    public IterableMultiKeyRBT() {
        super();
        numKeys = 0;
        startPoint = null;
    }

    /**
     * Inserts value into tree that can store multiple objects per key by keeping lists of objects in
     * each node of the tree.
     *
     * @param key object to insert
     * @return true if obj was inserted
     */
    @Override
    public boolean insertSingleKey(KeyType key) {
        // create a KeyList with the new key
        KeyList<KeyType> newList = new KeyList<>(key);
        // check if the tree already contains a node with duplicates.
        Node<KeyListInterface<KeyType>> existingNode = findNode(newList);
        if (existingNode != null) { // there are duplicates
            // If it does, add the key to the KeyList in the node that contains the duplicates and return
            // false.
            existingNode.data.addKey(key);
            numKeys++;
            return false;
        } else {
            // If it does not, then insert the new KeyList into a new node of the tree and return true.
            this.insert(newList);
            numKeys++;
            return true;
        }
    }

    /**
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return this.numKeys;
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator iterator() {
        Stack<Node<KeyListInterface<KeyType>>> stack = getStartStack();

        return new Iterator<KeyType>() {
            //private Stack<Node<KeyListInterface<KeyType>>> startStack = stack;
            private Node<KeyListInterface<KeyType>> current = null;
            private Iterator<KeyType> iter = null;

            @Override
            public boolean hasNext() {
                boolean b = iter != null && iter.hasNext();
                return !stack.isEmpty() || current != null || b;
            }

            @Override
            public KeyType next() {
                while (hasNext()) {
                    if (current != null) {
                        stack.push(current);
                        current = current.down[0];
                    } else if (iter != null) {
                        if (iter.hasNext()) {
//                            Iterator<KeyType> iter1 = iter;
//                            iter = null;
                            return iter.next();
                        } else {
                            iter = null; // Set iter to null when it's exhausted
                        }
                    } else if (!stack.isEmpty()) {
                        Node<KeyListInterface<KeyType>> tmp = stack.pop();
                        current = tmp.down[1];
                        iter = tmp.data.iterator();
                    }
                }
                return null;
            }
        };
    }

    /**
     * This method helps generate stacks that will only visit the nodes that are equal to or greater
     * than the start point by popping those nodes off the stack to visit them and then visit their
     * right subtree afterwards.
     *
     * @return an instance of java.util.Stack containing nodes after initialization
     */
    protected Stack<Node<KeyListInterface<KeyType>>> getStartStack() {
        Stack<Node<KeyListInterface<KeyType>>> stack = new Stack<>();
        Node<KeyListInterface<KeyType>> current = this.root;

        // Initialize with all left subtrees

        // If no iteration start point is set (the field that stores the start point is set to null),
        // the stack is initialized with the nodes on the path from the root node to (and including)
        // the node with the smallest key in the tree.
        if (startPoint == null) {
            while (current != null) {
                stack.push(current);
                current = current.down[0];
            }
        } else {// If the iteration start point is set, then the stack is initialized with all the
            // nodes with keys equal to or larger than the start point along the path of the search for
            // the start point.
            while (current != null) {
                if (startPoint.compareTo(current.data.iterator().next()) <= 0) {
                    stack.push(current);
                    current = current.down[0];
                } else {
                    current = current.down[1];
                }
            }
        }
        return stack;
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the starting point or
     * the key closest to it in the tree. This setting is remembered until it is reset. Passing in
     * null disables the starting point.
     *
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public void clear() {
        super.clear();
        this.numKeys = 0;
    }

    /**
     * This method performs an inorder traversal of the ITERABLE MULTIKEY RED BLACK tree.  The string
     * representations of each data value within this tree are assembled into a comma separated string
     * within brackets (similar to many implementations of java.util.Collection, like
     * java.util.ArrayList, LinkedList, etc).
     *
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    @Override
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<KeyListInterface<KeyType>>> nodeStack = new Stack<>();
            Node<KeyListInterface<KeyType>> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<KeyListInterface<KeyType>> popped = nodeStack.pop();
                    sb.append(popped.data.iterator().next().toString());
                    if (!nodeStack.isEmpty() || popped.down[1] != null)
                        sb.append(", ");
                    current = popped.down[1];
                } else {
                    nodeStack.add(current);
                    current = current.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the ITERABLE MULTIKEY RED BLACK tree. The
     * string representations of each data value within this tree are assembled into a comma separated
     * string within brackets (similar to many implementations of java.util.Collection). This method
     * will be helpful as a helper for the debugging and testing of your rotation implementation.
     *
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<KeyListInterface<KeyType>>> q = new LinkedList<>();
            q.add(this.root);
            while (!q.isEmpty()) {
                Node<KeyListInterface<KeyType>> next = q.removeFirst();
                if (next.down[0] != null)
                    q.add(next.down[0]);
                if (next.down[1] != null)
                    q.add(next.down[1]);
                sb.append(next.data.iterator().next().toString());
                if (!q.isEmpty())
                    sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    // testers

    /**
     * Creates an iterator for empty tree and checks if hasNext() returns
     * false.
     */
    @Test
    public void testEmptyTree() {
        IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();
        Assertions.assertTrue(iterableRBT.iterator().hasNext() == false);
    }

    /**
     * This method tests insertSingleKey() and numKeys() simultaneously.
     * <p>
     * This test include 1 scenario: An iterable rbt tree is built and 7, 14, 18, 23 are inserted one
     * by one. The tree should have 4 keys with the keys listed in the correct order.
     * <p>
     * If the in-order sequence is [ 7, 14, 18, 23 ], the to-level-order sequence is [ 14, 7, 18, 23
     * ], the number of keys is 4, then the test passes, otherwise doesn't.
     */
    @Test
    public void testInsertAndNum() {
        IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();

        iterableRBT.insertSingleKey(7);
        iterableRBT.insertSingleKey(14);
        iterableRBT.insertSingleKey(18);
        iterableRBT.insertSingleKey(23);

        if (!iterableRBT.toInOrderString()
                .equals("[ 7, 14, 18, 23 ]") || !iterableRBT.toLevelOrderString()
                .equals("[ 14, 7, 18, 23 ]"))
            Assertions.fail("Failure in insertSingleKey().");
        else if (iterableRBT.numKeys() != 4)
            Assertions.fail("Failure in numKeys().");
        else
            Assertions.assertTrue(true);
    }

    /**
     * This method tests iterator().
     * <p>
     * This test includes 1 scenario: An iterable rbt tree is built and 7, 14, 18, 23 are inserted one
     * by one. Then an iterator is generated. The iterator is supposed to iterate in in-order
     * sequence, which is 7, 14, 18 and 23. If not, then there is something wrong in the iterator()
     * method.
     */
    @Test
    public void testIterator() {
        IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();

        iterableRBT.insertSingleKey(7);
        iterableRBT.insertSingleKey(14);
        iterableRBT.insertSingleKey(18);
        iterableRBT.insertSingleKey(23);

        Iterator<Integer> iterator = iterableRBT.iterator();

        //    if (iterator.next() != 7)
        //      Assertions.fail("There's something wrong in the iterator().");

        Assertions.assertTrue(iterator.next() == 7);
        Assertions.assertTrue(iterator.next() == 14);
        Assertions.assertTrue(iterator.next() == 18);
        Assertions.assertTrue(iterator.next() == 23);
    }

    /**
     * This method tests setIterationStartPoint().
     * <p>
     * This test includes 1 scenario: An iterable rbt tree is built and 7, 14, 18, 23 are inserted one
     * by one. Then set the iteration start point to 18. Then an iterator is generated. The iterator
     * is supposed to only output 18 and 23. If not, there's something wrong in
     * setIterationStartPoint()
     */
    @Test
    public void testSetIterationStartPoint() {
        IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();

        iterableRBT.insertSingleKey(7);
        iterableRBT.insertSingleKey(14);
        iterableRBT.insertSingleKey(18);
        iterableRBT.insertSingleKey(23);

        iterableRBT.setIterationStartPoint(18);

        Iterator<Integer> iterator = iterableRBT.iterator();

        Assertions.assertTrue(iterator.next() == 18);
        Assertions.assertTrue(iterator.next() == 23);
    }

    /**
     * This method tests edge cases that may emerge in insertSingleKey() and numKeys().
     * <p>
     * This test include 1 scenario: An iterable rbt tree is built and 7, 14, 21, 28 are inserted one
     * by one. Then a duplicate 28, 21, 14, 7 is inserted. The tree should have 8 keys
     * with the keys listed in the correct order.
     * <p>
     * If the in-order sequence is [ 7, 14, 18, 23 ], the to-level-order sequence is [ 14, 7, 18, 23
     * ], the number of keys is 8, then the test passes, otherwise doesn't.
     */
    @Test
    public void edgeCase1InsertAndNum() {
        IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();

        iterableRBT.insertSingleKey(7);
        iterableRBT.insertSingleKey(14);
        iterableRBT.insertSingleKey(21);
        iterableRBT.insertSingleKey(28);
        iterableRBT.insertSingleKey(28);
        iterableRBT.insertSingleKey(21);
        iterableRBT.insertSingleKey(7);
        iterableRBT.insertSingleKey(14);

        if (!iterableRBT.toInOrderString()
                .equals("[ 7, 14, 21, 28 ]") || !iterableRBT.toLevelOrderString()
                .equals("[ 14, 7, 21, 28 ]"))
            Assertions.fail("Failure in insertSingleKey().");
        else if (iterableRBT.numKeys() != 8)
            Assertions.fail("Failure in numKeys().");
        else
            Assertions.assertTrue(true);

        int count = 0;
        Iterator<Integer> iter = iterableRBT.iterator();
        for (Integer key : iterableRBT) {
            int expected = ((count++ / 2) + 1) * 7;
            Assertions.assertEquals(expected, iter.next());
        }
    }

    /**
     * This method tests edge cases that will emerge in setIterationStartPoint().
     * <p>
     * This test includes 2 scenarios:
     * <p>
     * 1. An iterable rbt tree is built and 7, 14, 18, 23 are inserted one by one.
     * Then set the iteration start point to 16, which does not exist in the tree.
     * Then an iterator is generated. The iterator is supposed to only output 18 and 23.
     * If not, there's something wrong in setIterationStartPoint().
     * <p>
     * 2. An iterable rbt tree is built and 7, 14, 18, 23 are inserted one by one. Besides, a duplicate 18 and 23 is inserted.
     * Then set the iteration start point to 16, which does not exist in the tree.
     * Then an iterator is generated. The iterator is supposed to traverse through 18 twice and 23 twice.
     * If not, there's something wrong in setIterationStartPoint().
     */
    @Test
    public void edgeCaseSetIterationStartPoint() {
        {
            IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();

            iterableRBT.insertSingleKey(7);
            iterableRBT.insertSingleKey(14);
            iterableRBT.insertSingleKey(18);
            iterableRBT.insertSingleKey(23);

            iterableRBT.setIterationStartPoint(16);

            Iterator<Integer> iterator = iterableRBT.iterator();

            Assertions.assertTrue(iterator.next() == 18);
            Assertions.assertTrue(iterator.next() == 23);
        }

        {
            IterableMultiKeyRBT<Integer> iterableRBT = new IterableMultiKeyRBT<>();

            iterableRBT.insertSingleKey(7);
            iterableRBT.insertSingleKey(14);
            iterableRBT.insertSingleKey(18);
            iterableRBT.insertSingleKey(23);
            iterableRBT.insertSingleKey(18);
            iterableRBT.insertSingleKey(23);

            iterableRBT.setIterationStartPoint(16);

            Iterator<Integer> iterator = iterableRBT.iterator();

            Assertions.assertTrue(iterator.next() == 18);
            Assertions.assertTrue(iterator.next() == 18);
            Assertions.assertTrue(iterator.next() == 23);
            Assertions.assertTrue(iterator.next() == 23);
        }
    }

}

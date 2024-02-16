import java.util.LinkedList;
import java.util.Stack;


/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes of the tree. We will turn this Binary Search Tree into a self-balancing
 * tree as part of project 1 by modifying its insert functionality.
 * In week 0 of project 1, we will start this process by implementing tree rotations.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

  /**
   * This class represents a node holding a single value within a binary tree.
   */
  protected static class Node<T> {
    public T data;

    // up stores a reference to the node's parent
    public Node<T> up;
    // The down array stores references to the node's children:
    // - down[0] is the left child reference of the node,
    // - down[1] is the right child reference of the node.
    // The @SupressWarning("unchecked") annotation is use to supress an unchecked
    // cast warning. Java only allows us to instantiate arrays without generic
    // type parameters, so we use this cast here to avoid future casts of the
    // node type's data field.
    @SuppressWarnings("unchecked")
    public Node<T>[] down = (Node<T>[])new Node[2];
    public Node(T data) { this.data = data; }

    /**
     * @return true when this node has a parent and is the right child of
     * that parent, otherwise return false
     */
    public boolean isRightChild() {
      return this.up != null && this.up.down[1] == this;
    }

  }

  protected Node<T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree

  /**
   * Inserts a new data value into the tree.
   * This tree will not hold null references, nor duplicate data values.
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided data argument is null
   */
  public boolean insert(T data) throws NullPointerException {
    if (data == null)
      throw new NullPointerException("Cannot insert data value null into the tree.");
    return this.insertHelper(new Node<>(data));
  }

  /**
   * Performs a naive insertion into a binary search tree: adding the new node
   * in a leaf position within the tree. After this insertion, no attempt is made
   * to restructure or balance the tree.
   * @param newNode the new node to be inserted
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided node is null
   */
  protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
    if(newNode == null) throw new NullPointerException("new node cannot be null");

    if (this.root == null) {
      // add first node to an empty tree
      root = newNode;
      size++;
      return true;
    } else {
      // insert into subtree
      Node<T> current = this.root;
      while (true) {
        int compare = newNode.data.compareTo(current.data);
        if (compare == 0) {
          return false;
        } else if (compare < 0) {
          // insert in left subtree
          if (current.down[0] == null) {
            // empty space to insert into
            current.down[0] = newNode;
            newNode.up = current;
            this.size++;
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.down[0];
          }
        } else {
          // insert in right subtree
          if (current.down[1] == null) {
            // empty space to insert into
            current.down[1] = newNode;
            newNode.up = current;
            this.size++;
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.down[1];
          }
        }
      }
    }
  }

  /**
   * Performs the rotation operation on the provided nodes within this tree.
   * When the provided child is a left child of the provided parent, this
   * method will perform a right rotation. When the provided child is a
   * right child of the provided parent, this method will perform a left rotation.
   * When the provided nodes are not related in one of these ways, this method
   * will throw an IllegalArgumentException.
   * @param child is the node being rotated from child to parent position
   *      (between these two node arguments)
   * @param parent is the node being rotated from parent to child position
   *      (between these two node arguments)
   * @throws IllegalArgumentException when the provided child and parent
   *      node references are not initially (pre-rotation) related that way
   */
  protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // TODO: Implement this method.

    if (child.isRightChild()){
      // perform a left rotation
      leftRotate(child, parent);
    } else if (!child.isRightChild()) {
      // perform a right rotation
      rightRotate(child, parent);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This is a helper method that do a left rotation implementation on a BST.
   * @param child is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   */
  private void leftRotate(Node<T> child, Node<T> parent) {

    Node<T> G = parent.up; //stands for grandparent
    Node<T> LGC = child.down[0]; // stands for left grandchild

    // first we set the root
    if (G == null){ // if we don't have a grandparent, then child should be the root
      this.root = child;
    } else { // if we do have a grandparent, then grandparent's left child should be left child
      // and right one should be just right
      if (parent.equals(G.down[0]))
        G.down[0] = child;
      else
        G.down[1] = child;
    }

    // then we update everything else, i.e. connects everything else
    child.up = G; //grandparents don't change here
    parent.down[1] = LGC; // since we have a right child relationship, right child of parent
    // should be former left grandchild. i.e move the original left grandchild to right child of
    // parents

    if (LGC != null) // if left grandchild is not null, then point back to parent so that mutual
      // connection is constructed
      LGC.up = parent;

    child.down[0] = parent; // now parent become a new parent tree and connects it with child(new
    // parent after rotation)
    parent.up = child;
  }

  /**
   * This is a helper method that do a right rotation implementation on a BST.
   * @param child is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   */
  private void rightRotate(Node<T> child, Node<T> parent) {

    Node<T> G = parent.up;
    Node<T> RGC = child.down[1]; // stands for right grandchild

    // set the root
    if (G == null){ // if we don't have a grandparent, then child should be the root
      this.root = child;
    } else { // if we do have a grandparent, then grandparent's left child should be left child
      // and right one should just be right
      if (parent.equals(G.down[0]))
        G.down[0] = child;
      else
        G.down[1] = child;
    }

    // then we update everything else, i.e. connects everything else
    child.up = G; //grandparents don't change here
    parent.down[0] = RGC; // since we have a left child relationship, left child of parent
    // should be former right grandchild. i.e move the original right grandchild to left child of
    // parents

    if (RGC != null) // if left grandchild is not null, then point back to parent so that mutual
      // connection is constructed
      RGC.up = parent;

    child.down[1] = parent; // now parent become a new parent tree and connects it with child(new
    // parent after rotation)
    parent.up = child;
  }

  /**
   * Get the size of the tree (its number of nodes).
   * @return the number of nodes in the tree
   */
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   * @return true of this.size() returns 0, false if this.size() != 0
   */
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Checks whether the tree contains the value *data*.
   * @param data a comparable for the data value to check for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  public boolean contains(Comparable<T> data) {
    // null references will not be stored within this tree
    if (data == null) {
      throw new NullPointerException("This tree cannot store null references.");
    } else {
      Node<T> nodeWithData = this.findNode(data);
      // return false if the node is null, true otherwise
      return (nodeWithData != null);
    }
  }

  /**
   * Removes all keys from the tree.
   */
  public void clear() {
    this.root = null;
    this.size = 0;
  }

  /**
   * Helper method that will return the node in the tree that contains a specific
   * key. Returns null if there is no node that contains the key.
   * @param data the data value for which we want to find the node that contains it
   * @return the node that contains the data value or null if there is no such node
   */
  protected Node<T> findNode(Comparable<T> data) {
    Node<T> current = this.root;
    while (current != null) {
      int compare = data.compareTo(current.data);
      if (compare == 0) {
        // we found our value
        return current;
      } else if (compare < 0) {
        if (current.down[0] == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the left subtree
        current = current.down[0];
      } else {
        if (current.down[1] == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the right subtree
        current = current.down[1];
      }
    }
    return null;
  }

  /**
   * This method performs an inorder traversal of the tree. The string
   * representations of each data value within this tree are assembled into a
   * comma separated string within brackets (similar to many implementations
   * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  public String toInOrderString() {
    // generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (this.root != null) {
      Stack<Node<T>> nodeStack = new Stack<>();
      Node<T> current = this.root;
      while (!nodeStack.isEmpty() || current != null) {
        if (current == null) {
          Node<T> popped = nodeStack.pop();
          sb.append(popped.data.toString());
          if(!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
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
   * This method performs a level order traversal of the tree. The string
   * representations of each data value
   * within this tree are assembled into a comma separated string within
   * brackets (similar to many implementations of java.util.Collection).
   * This method will be helpful as a helper for the debugging and testing
   * of your rotation implementation.
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while(!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if(next.down[0] != null) q.add(next.down[0]);
        if(next.down[1] != null) q.add(next.down[1]);
        sb.append(next.data.toString());
        if(!q.isEmpty()) sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() +
        "\nin order: " + this.toInOrderString();
  }

  // Implement at least 3 tests using the methods below. You can
  // use your notes from lecture for ideas of rotation examples to test with.
  // Make sure to include rotations at the root of a tree in your test cases.
  // Give each of the methods a meaningful header comment that describes what is being
  // tested and make sure your tests have inline comments that help with reading your test code.
  // If you'd like to add additional tests, then name those methods similar to the ones given below.
  // Eg: public static boolean test4() {}
  // Do not change the method name or return type of the existing tests.
  // You can run your tests through the static main method of this class.

  /**
   * This method tests a tree that only has left child relationships.
   *
   * This test case first built a tree that only has left child relationships. Then do a right
   * rotation. Finally, use string representation of level order and in order to test if the
   * rotation is successful or not.
   *
   * @return true if result of rotation is correct otherwise false
   */
  public static boolean test1() {
    // TODO: Implement this test.
    // create a tree that has only left child relationship
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    tree.insert(7); //     7
    tree.insert(1); //   1
    tree.insert(0); // 0

    tree.rotate(tree.root.down[0], tree.root); // then do a right rotation
    String expected1 = "[ 1, 0, 7 ]";
    String expected2 = "[ 0, 1, 7 ]";
    return tree.toLevelOrderString().equals(expected1) && tree.toInOrderString().equals(expected2);
  }

  // larger trees
  // edge cases: 1.rotate at root of tree(both directions); 2.all of the subtrees are null; 3.
  // all of the subtrees are non-null;
  /**
   * This method tests a tree that only has right child relationships.
   *
   * This test case first built a tree that only has right child relationships. Then do a left
   * rotation. Finally, use string representation of level order and in order to test if the
   * rotation is successful or not.
   *
   * @return true if result of rotation is correct otherwise false
   */
  public static boolean test2() {
    // TODO: Implement this test.
    // create a tree that has only right child relationship
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    tree.insert(1); //      1
    tree.insert(2); //        2
    tree.insert(8); //          8

    tree.rotate(tree.root.down[1], tree.root); // then do a left rotation
    String expected1 = "[ 2, 1, 8 ]";
    String expected2 = "[ 1, 2, 8 ]";
    return tree.toLevelOrderString().equals(expected1) && tree.toInOrderString().equals(expected2);
  }

  /**
   * This method tests a tree that has a left child relationship and a right child relationship.
   *
   * This test case first built a tree that has both left and right child relationships. Then do a
   * left rotation before a right rotation. Finally, use string representation of level order and in
   * order to test if the rotation is successful or not.
   *
   * @return true if result of rotation is correct otherwise false
   */
  public static boolean test3() {
    // TODO: Implement this test.
    // create a tree that has both left and right child relationship
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    tree.insert(7);    //    7
    tree.insert(1);    //  1
    tree.insert(2);    //    2
    // Tree is something like this

    tree.rotate(tree.root.down[0].down[1], tree.root.down[0]); // first do a left rotation to
    // make it all left
    tree.rotate(tree.root.down[0], tree.root); // then do a right rotation
    String expected1 = "[ 2, 1, 7 ]";
    String expected2 = "[ 1, 2, 7 ]";
    return tree.toLevelOrderString().equals(expected1) && tree.toInOrderString().equals(expected2);
  }

  /**
   * This method tests a tree that has lots of left and right child relationships. It is a
   * complex tree.
   *
   * This test case first built a tree that has 9 nodes. Then do a left rotation before a right
   * rotation. Finally, use string representation of level order and in order to test if the
   * rotation is successful or not.
   *
   * @return true if result of rotation is correct otherwise false
   */
  public static boolean test4() {
    // TODO: Implement this test.
    // create a complex tree that has many nodes
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    tree.insert(70);    //       70
    tree.insert(10);    //  10        80
    tree.insert(0);    // 0  20   75    210
    //                                     90   110
    tree.insert(20);
    tree.insert(80);
    tree.insert(75);
    tree.insert(210);
    tree.insert(90);
    tree.insert(220);

    tree.rotate(tree.root.down[0], tree.root); // first do a left rotation to
    // make it all left
    tree.rotate(tree.root.down[1], tree.root); // then do a right rotation
    String expected1 = "[ 70, 10, 80, 0, 20, 75, 210, 90, 220 ]";
    String expected2 = "[ 0, 10, 20, 70, 75, 80, 90, 210, 220 ]";
    return tree.toLevelOrderString().equals(expected1) && tree.toInOrderString().equals(expected2);
  }

  /**
   * Main method to run tests. If you'd like to add additional test methods, add a line for each
   * of them.
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Test 1 passed: " + test1());
    System.out.println("Test 2 passed: " + test2());
    System.out.println("Test 3 passed: " + test3());
    System.out.println("Test 4 passed: " + test4());
  }

}

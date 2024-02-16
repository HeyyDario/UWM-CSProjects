// --== CS400 Fall 2023 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group: C14
// TA: MATTHEW SCHWENNESEN
// Lecturer: FLORIAN HEIMERL
// Notes to Grader: <optional extra notes>
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;


public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {


  /**
   * This class defines an extended node that stores the color for each node.
   */
  protected static class RBTNode<T> extends Node<T> {
    public int blackHeight = 0; // 0 stands for red, 1 for black

    public RBTNode(T data) {
      super(data);
    }

    public RBTNode<T> getUp() {
      return (RBTNode<T>) this.up;
    }

    public RBTNode<T> getDownLeft() {
      return (RBTNode<T>) this.down[0];
    }

    public RBTNode<T> getDownRight() {
      return (RBTNode<T>) this.down[1];
    }
  }

  /**
   * The method is used to resolve any red property violations that are introduced by inserting a
   * new node into the red-black tree. This method may also be called recursively, in which case the
   * input parameter may reference a different red node in the tree that potentially has a red
   * parent node.
   *
   * @param newRedNode - a reference to a newly added red node
   */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newRedNode) {
    if (newRedNode == null || newRedNode == this.root) {
      newRedNode.blackHeight = 1; // change RED to BLACK
      return;
    }

    RBTNode<T> parent = newRedNode.getUp(); // what if parent doesn't exist? i.e newNode is root
    RBTNode<T> grandparent = null;
    if (parent == null) {
      grandparent = null;
    } else {
      grandparent = parent.getUp();
    }
    RBTNode<T> aunt = null;
    if (grandparent == null) { // if grandparent is null, then aunt is also null
      aunt = null;
    } else { // else if grandparent is not null, then parent can either be left child or right
      // child of grandparent
      if (parent.isRightChild()) {
        aunt = grandparent.getDownLeft(); // also parent.getUp().getDownLeft() or newNode.getUp
        // .getUp.getDownLeft()
      } else {
        aunt = grandparent.getDownRight();
      }
    }

    // till now we have set up grandparent, parent and newNode

    // Scenario1: parent is BLACK, then no violation
    if (parent.blackHeight == 1)
      return;
    else { // Scenario2: parent is RED
      if (aunt == null || aunt.blackHeight == 1) {//Scenario2.1: aunt is black
        if (newRedNode.isRightChild() && !parent.isRightChild()) { // Scenario2.2.1: aunt is
          // black, newNode is
          // rightChild,
          // then first left rotate between newNode and parent, then right rotate between old
          // newNode and grandparent and color swap
          rotate(newRedNode, parent);
          newRedNode = parent;
          parent = newRedNode.getUp();

          rotate(parent, grandparent);

          parent.blackHeight = 1;
          grandparent.blackHeight = 0;
        } else if (!newRedNode.isRightChild() && parent.isRightChild()) {
          rotate(newRedNode, parent);
          newRedNode = parent;
          parent = newRedNode.getUp();

          rotate(parent, grandparent);
          // FIXME HERE COLOR SWAP MISSING
          parent.blackHeight = 1;
          grandparent.blackHeight = 0;
        } else {
          //Scenario2.2.2: aunt is black, newNode is leftChild, then just rightRotate and swap color
          rotate(parent, grandparent);

          parent.blackHeight = 1;
          grandparent.blackHeight = 0;
        }
      } else { //Scenario2.2: aunt is red, then recolor
        parent.blackHeight = 1; // parent red -> black
        aunt.blackHeight = 1; //aunt red -> black
        grandparent.blackHeight = 0; // grandparent black -> red
        // check if there is a violation when grandparent turn to red
        enforceRBTreePropertiesAfterInsert(grandparent);
        return;
      }
    }



    // if the parent of newNode is RED, then there is a RED violation
    // if newNode is right child of parent, then leftRotate, then rightRotate between newNode's
    // parent and grandParent, then swap color (newNode and its parent).
    // if newNode is left child of parent
    // if Black aunt, rightRotate between newNode's parent and grandparent, then swap color
    // if Red aunt, recolor (newNode.up.down[1], newNode) with newNode.up
  }

  @Override
  public boolean insert(T data) throws NullPointerException {
    RBTNode<T> newNode = new RBTNode<>(data); // Create a new red node.

    // Insert the new node using the insertHelper of BinarySearchTree.
    boolean isValidInsertion = super.insertHelper(newNode);

    if (isValidInsertion) {
      enforceRBTreePropertiesAfterInsert(newNode);
    }

    /* maybe not necessary?
    if (newNode == this.root)
      newNode.blackHeight = 1;

     */

    return isValidInsertion;
  }

  /**
   * This method tests the insertion into an empty tree.
   *
   * Insert a RED 7 node into an empty tree.
   *
   * @return true if the test passes, false otherwise
   */
  //@Test
  public static boolean test1() {
    // first insert 7 into an empty tree

    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(7);

    // if color is not RED or toString is not 7, return false
    //if()
    //Assertions.fail("There's something wrong in the first step of setting a root");
    // root cannot be RED, so repair
    tree.enforceRBTreePropertiesAfterInsert((RBTNode<Integer>) tree.root);
    String expectedToLevel = "[ 7 ]";
    String expectedInOrder = "[ 7 ]";
    // after repair, if color is not BLACK or toString is not 7, return false
    if (!tree.toInOrderString().equals(expectedInOrder) || !tree.toLevelOrderString()
        .equals(expectedToLevel))
      //Assertions.fail("Fail to change root to BLACK");

      return false; // return true; FIXME HERE this return follows the if
    return true;
    //Assertions.assertTrue(true);
  }

  /**
   * This method tests the insertion with a null aunt.
   *
   * On the basis of test1, first insert a RED 14 node and everything is good. Then insert a RED 18
   * node to the tree and there's a RED violation between 14 and 18, with 18's aunt BLACK.
   *
   * @return true if the test passes, false otherwise
   */
  public static boolean test2() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(7);
    tree.insert(14);
    tree.insert(18);
    // then insert 14 into the existing tree
    // then insert 18 into the existing tree and there should be a RED violation between 14 and 18
    String expectedToLevel = "[ 14, 7, 18 ]";
    String expectedInOrder = "[ 7, 14, 18 ]";
    if (!tree.toInOrderString().equals(expectedInOrder) || !tree.toLevelOrderString()
        .equals(expectedToLevel))
      //Assertions.fail("Fail to change root to BLACK");

      return false; // return true; FIXME HERE this return follows the if
    return true;
  }

  /**
   * This method tests the insertion with a RED aunt.
   *
   * On the basis of test2, insert a RED 23 node into the existing tree and there's a RED violation
   * between 18 and 23, with 23's aunt RED.
   *
   * @return true if the test passes, false otherwise
   */
  public static boolean test3() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(7);
    tree.insert(14);
    tree.insert(18);
    tree.insert(23);

    String expectedToLevel = "[ 14, 7, 18, 23 ]";
    String expectedInOrder = "[ 7, 14, 18, 23 ]";
    if (!tree.toInOrderString().equals(expectedInOrder) || !tree.toLevelOrderString()
        .equals(expectedToLevel))
      //Assertions.fail("Fail to change root to BLACK");

      return false; // return true; FIXME HERE this return follows the if
    return true;
  }

  /**
   * This method tests the complex scenario of insertion.
   *
   * On the basis of test3, insert RED 1 and 11 node into the existing tree and there's not a RED
   * violation. Besides, insert RED 20 node and there's a Red violation. Then insert RED 29 node
   * and there's a RED violation. Finally, insert RED 25 node and there's a RED violation.
   *
   */
  public static boolean test4() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(7);
    tree.enforceRBTreePropertiesAfterInsert((RBTNode<Integer>) tree.root);
    tree.insert(14);
    tree.insert(18);
    tree.insert(23);

    // new inserted nodes in this test
    tree.insert(11);
    tree.insert(1);
    tree.insert(20);
    tree.insert(29);
    tree.insert(25);

    String expectedToLevel = "[ 14, 7, 20, 1, 11, 18, 25, 23, 29 ]";
    String expectedInOrder = "[ 1, 7, 11, 14, 18, 20, 23, 25, 29 ]";
    if (!tree.toInOrderString().equals(expectedInOrder) || !tree.toLevelOrderString()
        .equals(expectedToLevel))
      return false;

    return true;
  }

  /**
   * This method tests cascading fix of insertion, which required multiple operations to fix.
   *
   * On the basis of test4, insert the RED 27 node into the existing tree and there's a RED
   * violation. After that, there will be new RED violation. This test is to see if the tree can
   * do the cascading fix operation.
   *
   */
  public static boolean test5() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(7);
    tree.enforceRBTreePropertiesAfterInsert((RBTNode<Integer>) tree.root);
    tree.insert(14);
    tree.insert(18);
    tree.insert(23);

    tree.insert(11);
    tree.insert(1);
    tree.insert(20);
    tree.insert(29);
    tree.insert(25);
    //newly inserted node
    tree.insert(27);

    String expectedToLevel = "[ 20, 14, 25, 7, 18, 23, 29, 1, 11, 27 ]";
    String expectedInOrder = "[ 1, 7, 11, 14, 18, 20, 23, 25, 27, 29 ]";
    if (!tree.toInOrderString().equals(expectedInOrder) || !tree.toLevelOrderString()
        .equals(expectedToLevel))
     return false;

    return true;
  }

  /**
   * Main method to run tests. If you'd like to add additional test methods, add a line for each of
   * them.
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Test 1 passed: " + test1());
    System.out.println("Test 2 passed: " + test2());
    System.out.println("Test 3 passed: " + test3());
    System.out.println("Test 4 passed: " + test4());
    System.out.println("Test 5 passed: " + test5());
  }
}

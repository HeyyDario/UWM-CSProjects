//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P06: Password Cracking - PasswordStorage
// Course:   CS 300 Summer 2023
//
// Author:   Chengtao Dai
// Email:    cdai53@wisc.edu
// Lecturer: Michelle Jensen
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         No partner.
// Online Sources:  No help received.
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

/**
 * This class represents a BST to store the password.
 *
 * @author Michelle & Chengtao Dai
 */
public class PasswordStorage {

  protected PasswordNode root; //the root of this BST that contains passwords
  private int size; //how many passwords are in the BST
  private final Attribute COMPARISON_CRITERIA;
  //what password information to use to determine order in the tree

  /**
   * Constructor that creates an empty BST and sets the comparison criteria.
   *
   * @param comparisonCriteria, the Attribute that will be used to determine order in the tree
   */
  public PasswordStorage(Attribute comparisonCriteria) {
    //TODO
    this.COMPARISON_CRITERIA = comparisonCriteria;
    this.size = 0;
    this.root = null;
  }

  /**
   * Getter for this BST's criteria for determining order in the three
   *
   * @return the Attribute that is being used to make comparisons in the tree
   */
  public Attribute getComparisonCriteria() {
    return this.COMPARISON_CRITERIA; //TODO
  }

  /**
   * Getter for this BST's size.
   *
   * @return the size of this tree
   */
  public int size() {
    return this.size; //TODO
  }

  /**
   * Determines whether or not this tree is empty.
   *
   * @return true if it is empty, false otherwise
   */
  public boolean isEmpty() {
    return this.size == 0 && this.root == null; //TODO
  }

  /**
   * Provides in-order String representation of this BST, with each Password on its own line. The
   * String representation ends with a newline character ('\n')
   *
   * @return this BST as a string
   */
  @Override
  public String toString() {
    return toStringHelper(this.root);
  }

  /**
   * Recursive method the uses an in-order traversal to create the string representation of this
   * tree.
   *
   * @param currentNode, the root of the current tree
   * @return the in-order String representation of the tree rooted at current node
   */
  private String toStringHelper(PasswordNode currentNode) {
    //THIS MUST BE IMPLEMENTED RECURSIVELY
    if (currentNode == null) {
      return ""; // Base case: an empty subtree, return an empty string
    }

    // In-order traversal: L S R
    String leftSubtree = toStringHelper(currentNode.getLeft());
    String currentNodeData = currentNode.getPassword().toString() + "\n";
    String rightSubtree = toStringHelper(currentNode.getRight());
    return leftSubtree + currentNodeData + rightSubtree;
    //TODO
  }

  /**
   * Determines whether or not this tree is actually a valid BST.
   *
   * @return true if it is a BST, false otherwise
   */
  public boolean isValidBST() {
    return isValidBSTHelper(this.root, Password.getMinPassword(), Password.getMaxPassword());
  }

  /**
   * Recurisvely determines if the tree rooted at the current node is a valid BST. That is, every
   * value to the left of currentNode is "less than" the value in currentNode and every value to the
   * right of it is "greater than" it.
   *
   * @param currentNode, the root node of the current tree
   * @param lowerBound,  the smallest possible password
   * @param upperBound,  the largest possible password
   * @return true if the tree rooted at currentNode is a BST, false otherwise
   */
  private boolean isValidBSTHelper(PasswordNode currentNode, Password lowerBound,
      Password upperBound) {
    //MUST BE IMPLEMENTED RECURSIVELY

    // BASE CASE 1: the tree rooted at currentNode is empty, which does not violate any BST rules
    if (currentNode == null)
      return true;

    // BASE CASE 2: the current Password is outside of the upper OR lower bound for this subtree,
    // which is against
    //              the rules for a valid BST
    if (currentNode.getPassword()
        .compareTo(lowerBound, getComparisonCriteria()) < 0 || currentNode.getPassword()
        .compareTo(upperBound, getComparisonCriteria()) > 0)
      return false;

    // If we do not have a base case situation, we must use recursion to verify currentNode's
    // child subtrees

    // RECURSIVE CASE 1: Check that the left subtree contains only Passwords greater than
    // lowerBound and less than
    //                   currentNode's Password; return false if this property is NOT satisfied
    boolean leftSubtree =
        isValidBSTHelper(currentNode.getLeft(), lowerBound, currentNode.getPassword());

    // RECURSIVE CASE 2: Check that the right subtree contains only Passwords greater than
    // currentNode's Password
    //                   and less than upperBound; return false if this property is NOT satisfied
    boolean rightSubtree =
        isValidBSTHelper(currentNode.getRight(), currentNode.getPassword(), upperBound);

    // COMBINE RECURSIVE CASE ANSWERS: this is a valid BST if and only if both case 1 and 2 are
    // valid
    return leftSubtree && rightSubtree; //TODO
  }

  /**
   * Returns the password that matches the criteria of the provided key. Ex. if the COMPARISON
   * CRITERIA is OCCURRENCE and the key has an occurrence of 10 it will return the password stored
   * in the tree with occurrence of 10
   *
   * @param key, the password that contains the information for the password we are searching for
   * @return the Password that matches the search criteria, if it does not exist in the tree it this
   * will be null
   */
  public Password lookup(Password key) {
    return lookupHelper(key, root);
  }

  /**
   * Recursive helper method to find the matching password in this BST
   *
   * @param key,         password containing the information we are searching for
   * @param currentNode, the node that is the current root of the tree
   * @return the Password that matches the search criteria, if it does not exist in the tree it this
   * will be null
   */
  private Password lookupHelper(Password key, PasswordNode currentNode) {
    //THIS MUST BE IMPLEMENTED RECURSIVELY
    // base case
    if (currentNode == null)
      return null;

    // recursive case1: the key matches the current node's password
    if (key.compareTo(currentNode.getPassword(), getComparisonCriteria()) == 0)
      return currentNode.getPassword();
      // recursive case2: the key "less" than ...
    else if (key.compareTo(currentNode.getPassword(), getComparisonCriteria()) < 0)
      return lookupHelper(key, currentNode.getLeft());
      // recursive case3: the key "greater" than ...
    else
      return lookupHelper(key, currentNode.getRight());
    //TODO
  }

  /**
   * Returns the best (max) password in this BST
   *
   * @return the best password in this BST
   * @throws NoSuchElementException if the BST is empty
   */
  public Password getBestPassword() {
    if (isEmpty())
      throw new NoSuchElementException();

    PasswordNode current = root;
    while (current.getRight() != null)
      current = current.getRight();
    return current.getPassword(); //TODO
  }

  /**
   * Returns the worst password in this BST
   *
   * @return the worst password in this BST
   * @throws NoSuchElementException if the BST is empty
   */
  public Password getWorstPassword() {
    if (isEmpty())
      throw new NoSuchElementException();

    PasswordNode current = root;
    while (current.getLeft() != null)
      current = current.getLeft();
    return current.getPassword();
    //TODO
  }

  /**
   * Adds the Password to this BST.
   *
   * @param toAdd, the password to be added to the tree
   * @throws IllegalArgumentException if the (matching) password object is already in the tree
   */
  public void addPassword(Password toAdd) {
    if (root == null) { // if the root is null, then the toAdd becomes the root
      root = new PasswordNode(toAdd);
      size++;
    } else { // if there is something to be root
      if (addPasswordHelper(toAdd, root)) { // if add successfully, size++
        size++;
      } else { // if not (there are duplicates), throw exception
        throw new IllegalArgumentException();
      }
    }
    //TODO
  }

  /**
   * Recursive helper that traverses the tree and adds the password where it belongs
   *
   * @param toAdd,       the password to add to the tree
   * @param currentNode, the node that is the current root of the (sub)tree
   * @return true if it was successfully added, false otherwise
   */
  private boolean addPasswordHelper(Password toAdd, PasswordNode currentNode) {
    //THIS MUST BE IMPLEMENTED RECURSIVELY

    // base case: current node is empty
    if (currentNode == null) {
      currentNode = new PasswordNode(toAdd);
      return true;
    }

    if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) > 0) {
      // If the password to add is less than the current node's password, traverse to the left
      // subtree.
      if (currentNode.getRight() == null) {
        currentNode.setRight(new PasswordNode(toAdd));
        return true;
      } else {
        return addPasswordHelper(toAdd, currentNode.getRight());
      }
    } else if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) < 0) {
      // If the password to add is greater than the current node's password, traverse to the
      // right subtree.
      if (currentNode.getLeft() == null) {
        currentNode.setLeft(new PasswordNode(toAdd));
        return true;
      } else {
        return addPasswordHelper(toAdd, currentNode.getLeft());
      }
    } else {
      // If the password to add matches the current node's password, return false (duplicate
      // password).
      return false;
    } //TODO
  }

  /**
   * Removes the matching password from the tree
   *
   * @param toRemove, the password to be removed from the tree
   * @throws NoSuchElementException if the password is not in the tree
   */
  public void removePassword(Password toRemove) {
    if (this.lookup(toRemove) == null)
      throw new NoSuchElementException();

    root = removePasswordHelper(toRemove, root);
    size--;
    //TODO
  }

  /**
   * Recursive helper method to that removes the password from this BST.
   *
   * @param toRemove,    the password to be removed from the tree
   * @param currentNode, the root of the tree we are removing from
   * @return the PasswordNode representing the NEW root of this subtree now that toRemove has been
   * removed. This may still be currentNode, or it may have changed!
   */
  private PasswordNode removePasswordHelper(Password toRemove, PasswordNode currentNode) {
    //TODO: MUST BE IMPLEMENTED RECURSIVELY

    //BASE CASE: current tree is empty
    if (currentNode == null) {
      return null;
    }

    //RECURSIVE CASE: toRemove is in the left subtree, continue searching
    if (toRemove.compareTo(currentNode.getPassword(), getComparisonCriteria()) < 0)
      currentNode.setLeft(removePasswordHelper(toRemove, currentNode.getLeft()));
      //RECURSIVE CASE: toRemove is in the right subtree, continue searching
    else if (toRemove.compareTo(currentNode.getPassword(), getComparisonCriteria()) > 0)
      currentNode.setRight(removePasswordHelper(toRemove, currentNode.getRight()));
      //otherwise we found the node to remove!
    else {
      if (currentNode.getLeft() == null && currentNode.getRight() == null)
        currentNode = null;
      else if (currentNode.getLeft() != null && currentNode.getRight() == null)
        currentNode = currentNode.getLeft();
      else if (currentNode.getLeft() == null && currentNode.getRight() != null)
        currentNode = currentNode.getRight();
      else {
        PasswordNode predecessor = findPredecessor(currentNode);
        currentNode = new PasswordNode(predecessor.getPassword(), currentNode.getLeft().getLeft(),
            currentNode.getRight());
        currentNode.setLeft(removePasswordHelper(predecessor.getPassword(), currentNode.getLeft()));
      }
    }

    //BASE CASE: current node has no children
    //BASE CASE(S): current node has one child (one for the left and right respectively)
    //RECURSIVE CASE: currentNode has 2 children
    //1)Find the predecessor password [HINT: Write a private helper method!] 
    //2)Make new node for the predecessor password. It should have same left and right subtree as
    // the current node.
    //3)Replace currentNode with the new predecessor node
    //4)Remove the (duplicate) predecessor from the current tree and update the left subtree

    return currentNode; //LEAVE THIS LINE AS IS
  }

  private PasswordNode findPredecessor(PasswordNode currentNode) {
    currentNode = currentNode.getLeft();

    while (currentNode.getRight() != null) {
      currentNode = currentNode.getRight();
    }
    return currentNode;
  }
}

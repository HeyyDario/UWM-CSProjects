//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P06: Password Cracking - PasswordCrackingTester
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
 * This class tests all the classes and methods used in Password Cracking project.
 *
 * @author Michelle & Chengtao Dai
 */
public class PasswordCrackingTester {

  /**
   * Validates the constructor and accessor methods of PasswordStorage, specifically the
   * getComparisonCriteria(), size(), and isEmpty() methods, as well as accessing the protected data
   * field root.
   *
   * Be sure to try making multiple PasswordStorage objects with different Attributes.
   *
   * @return true if the basic accessor methods work as expected, false otherwise
   */
  public static boolean testBasicPasswordStorageMethods() {
    // Create PasswordStorage objects with different Attributes
    PasswordStorage storage1 = new PasswordStorage(Attribute.OCCURRENCE);
    PasswordStorage storage2 = new PasswordStorage(Attribute.STRENGTH_RATING);
    PasswordStorage storage3 = new PasswordStorage(Attribute.HASHED_PASSWORD);

    // Test getComparisonCriteria()
    boolean criteriaTest1 = storage1.getComparisonCriteria() == Attribute.OCCURRENCE;
    boolean criteriaTest2 = storage2.getComparisonCriteria() == Attribute.STRENGTH_RATING;
    boolean criteriaTest3 = storage3.getComparisonCriteria() == Attribute.HASHED_PASSWORD;

    // Test size() and isEmpty()
    boolean sizeTest1 = storage1.size() == 0 && storage1.isEmpty();
    boolean sizeTest2 = storage2.size() == 0 && storage2.isEmpty();
    boolean sizeTest3 = storage3.size() == 0 && storage3.isEmpty();

    // Test accessing the protected data field root (if it compiles, it means we can access it)
    boolean fieldTest1 = storage1.root == null;
    boolean fieldTest2 = storage2.root == null;
    boolean fieldTest3 = storage3.root == null;

    // Combine the results of all tests
    return criteriaTest1 && criteriaTest2 && criteriaTest3 && sizeTest1 && sizeTest2 && sizeTest3 && fieldTest1 && fieldTest2 && fieldTest3;
    // TODO
  }

  /**
   * Validates the Password class compareTo() method. Create at least two DIFFERENT Password objects
   * and compare them on each of the Attribute values. See the writeup for details on how the
   * various comparisons are expected to work.
   *
   * @return true if Password's compareTo() works as expected, false otherwise
   */
  public static boolean testPasswordCompareTo() {
    Password password1 = new Password("password", 1000);
    Password password2 = new Password("StronkPass12#", 23);

    // Test when Attribute is OCCURRENCE
    int occurrenceResult1 = password1.compareTo(password2, Attribute.OCCURRENCE);
    int occurrenceResult2 = password2.compareTo(password1, Attribute.OCCURRENCE);
    boolean occurrenceTest = occurrenceResult1 > 0 && occurrenceResult2 < 0;

    // Test when Attribute is STRENGTH_RATING
    int strengthResult1 = password1.compareTo(password2, Attribute.STRENGTH_RATING);
    int strengthResult2 = password2.compareTo(password1, Attribute.STRENGTH_RATING);
    boolean strengthTest = strengthResult1 < 0 && strengthResult2 > 0;

    // Test when Attribute is HASHED_PASSWORD
    int hashedResult1 = password1.compareTo(password2, Attribute.HASHED_PASSWORD);
    int hashedResult2 = password2.compareTo(password1, Attribute.HASHED_PASSWORD);
    boolean hashedTest = hashedResult1 < 0 && hashedResult2 > 0;

    // Combine the results of all tests
    return occurrenceTest && strengthTest && hashedTest;// TODO
  }

  /**
   * Validates the incomplete methods in PasswordNode, specifically isLeafNode(),
   * numberOfChildren(), hasLeftChild() and hasRightChild(). Be sure to test all possible
   * configurations of a node in a binary tree!
   *
   * @return true if the status methods of PasswordNode work as expected, false otherwise
   */
  public static boolean testNodeStatusMethods() {
    // left - node1 - right
    PasswordNode node1 = new PasswordNode(new Password("password", 1000));
    PasswordNode left = new PasswordNode(new Password("left", 1));
    PasswordNode right = new PasswordNode(new Password("right", 1));

    // Test numberOfChildren when the node is a leaf
    int expectedNum1 = node1.numberOfChildren();
    int actualNum1 = 0;
    boolean numTest1 = expectedNum1 == actualNum1;

    // Test isLeafNode() on node1
    boolean expectedLeafNode = node1.isLeafNode();
    boolean actualLeafNode = true;
    boolean isLeafNodeTest = expectedLeafNode == actualLeafNode;

    // Test hasLeftChild and hasRightChild - 0 child
    boolean expectedHasLeft = node1.hasLeftChild();
    boolean expectedHasRight = node1.hasRightChild();
    boolean actualHasLeft = false;
    boolean actualHasRight = false;
    boolean hasLeftChildTest = expectedHasLeft == actualHasLeft;
    boolean hasRightChildTest = expectedHasRight == actualHasRight;

    // Test hasLeftChild and hasRightChild - 2 children
    PasswordNode node2 = new PasswordNode(new Password("password", 1000), left, right);
    boolean expectedHasLeft2 = node2.hasLeftChild();
    boolean expectedHasRight2 = node2.hasRightChild();
    boolean actualHasLeft2 = true;
    boolean actualHasRight2 = true;
    boolean hasLeftChildTest2 = expectedHasLeft2 == actualHasLeft2;
    boolean hasRightChildTest2 = expectedHasRight2 == actualHasRight2;

    // Test hasLeftChild and hasRightChild - 1 left child
    PasswordNode node3 = new PasswordNode(new Password("password", 1000), left, null);
    boolean expectedHasLeft3 = node3.hasLeftChild();
    boolean expectedHasRight3 = node3.hasRightChild();
    boolean actualHasLeft3 = true;
    boolean actualHasRight3 = false;
    boolean hasLeftChildTest3 = expectedHasLeft3 == actualHasLeft3;
    boolean hasRightChildTest3 = expectedHasRight3 == actualHasRight3;

    // Test hasLeftChild and hasRightChild - 1 right child
    PasswordNode node4 = new PasswordNode(new Password("password", 1000), null, right);
    boolean expectedHasLeft4 = node4.hasLeftChild();
    boolean expectedHasRight4 = node4.hasRightChild();
    boolean actualHasLeft4 = false;
    boolean actualHasRight4 = true;
    boolean hasLeftChildTest4 = expectedHasLeft4 == actualHasLeft4;
    boolean hasRightChildTest4 = expectedHasRight4 == actualHasRight4;

    return numTest1 && isLeafNodeTest && hasLeftChildTest && hasRightChildTest && hasLeftChildTest2 && hasRightChildTest2 && hasLeftChildTest3 && hasRightChildTest3 && hasLeftChildTest4 && hasRightChildTest4; // TODO
  }

  // GIVE TO STUDENTS
  public static boolean testToString() {
    try {
      PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

      // empty is empty string
      String expected = "";
      String actual = bst.toString();
      if (!actual.equals(expected)) {
        System.out.println("toString() does not return the proper value on an empty tree!");
        return false;
      }

      // size one only returns 1 thing
      Password p = new Password("1234567890", 15000);
      PasswordNode rootNode = new PasswordNode(p);

      bst.root = rootNode; // here I am manually building the tree by editing the root node
      // directly to be the node of my choosing

      expected = p.toString() + "\n";
      actual = bst.toString();
      if (!actual.equals(expected))
        return false;


      // big tree returns in-order traversal
      Password p2 = new Password("test", 500);
      Password p3 = new Password("iloveyou", 765);
      Password p4 = new Password("qwerty", 250);
      Password p5 = new Password("admin", 1002);
      Password p6 = new Password("password", 2232);
      Password p7 = new Password("abc123", 2090);

      PasswordNode p4Node = new PasswordNode(p4);
      PasswordNode p3Node = new PasswordNode(p3);
      PasswordNode p7Node = new PasswordNode(p7);
      PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
      PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
      PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
      rootNode = new PasswordNode(p, p2Node, p5Node);
      bst.root = rootNode;

      expected =
          p4.toString() + "\n" + p2.toString() + "\n" + p3.toString() + "\n" + p.toString() + "\n"
              + p5.toString() + "\n" + p7.toString() + "\n" + p6.toString() + "\n";
      actual = bst.toString();

      if (!actual.equals(expected))
        return false;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  // GIVE TO STUDENTS
  public static boolean testIsValidBST() {
    try {
      PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

      // empty tree is a valid bst
      /*
       * String expected = ""; String actual = bst.toString();
       */
      if (!bst.isValidBST()) {
        System.out.println("isValidBST() says that an empty tree is not a valid BST!");
        return false;
      }

      // size one is a bst
      Password p = new Password("1234567890", 1000);
      PasswordNode rootNode = new PasswordNode(p);

      bst.root = rootNode; // here I am manually building the tree by editing the root node
      // directly to be the node of my choosing

      if (!bst.isValidBST()) {
        System.out.println("isValidBST() says that a tree of size 1 is not a valid BST!");
        return false;
      }

      // p4 < p2 < p3 < p < p5 < p7 < p6
      Password p2 = new Password("test", 500);
      Password p3 = new Password("iloveyou", 765);
      Password p4 = new Password("qwerty", 250);
      Password p5 = new Password("admin", 1002);
      Password p6 = new Password("password", 2232);
      Password p7 = new Password("abc123", 2090);

      // works on indentifying small obviously invalid tree
      PasswordNode p7Node = new PasswordNode(p7);
      PasswordNode p3Node = new PasswordNode(p3);
      rootNode = new PasswordNode(p, p7Node, p3Node); //p7 should be in the right subtree
      bst.root = rootNode;
      if (bst.isValidBST())
        return false;

      // tree with only one subtree being valid, other subtree has a violation a couple layers deep


      PasswordNode p4Node = new PasswordNode(p4);
      p7Node = new PasswordNode(p7);
      p3Node = new PasswordNode(p3);
      PasswordNode p6Node = new PasswordNode(p6, null, p7Node);
      PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
      PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
      rootNode = new PasswordNode(p, p2Node, p5Node);
      bst.root = rootNode;

      if (bst.isValidBST()) {
        System.out.println(
            "isValidBST() says that a tree with only one valid subtree is a valid bst");
        return false;
      }


      // works on valid large tree
      p4Node = new PasswordNode(p4);
      p3Node = new PasswordNode(p3);
      p7Node = new PasswordNode(p7);
      p6Node = new PasswordNode(p6, p7Node, null);
      p5Node = new PasswordNode(p5, null, p6Node);
      p2Node = new PasswordNode(p2, p4Node, p3Node);
      rootNode = new PasswordNode(p, p2Node, p5Node);
      bst.root = rootNode;

      if (!bst.isValidBST())
        return false;

      //violates search order property further down the tree and comes from a node in a left subtree
      PasswordNode one = new PasswordNode(p4);
      PasswordNode three = new PasswordNode(p3, one, null);
      PasswordNode two = new PasswordNode(p2, null, three);
      bst.root = two;

      if (bst.isValidBST()) {
        System.out.println("bad bst is valid");
        return false;
      }

      //violates search order property further down the tree and comes from a node in a right
      // subtree
      one = new PasswordNode(p4);
      three = new PasswordNode(p3, null, one);
      two = new PasswordNode(p2, null, three);
      bst.root = two;

      if (bst.isValidBST()) {
        System.out.println("bad bst is valid");
        return false;
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  // STUDENT TODO: add javadoc

  /**
   * Validates the PasswordStorage class loopup() method. Create at least two DIFFERENT Password
   * objects and compare them on each of the Attribute values.
   *
   * @return true if PasswordStorage's lookup() works as expected, false otherwise
   */
  public static boolean testLookup() {
    // p4 < p2 < p3 < p < p5 < p7 < p6
    Password p = new Password("1234567890", 1000);
    Password p2 = new Password("test", 500);
    Password p3 = new Password("iloveyou", 765);


    PasswordStorage passwordStorage1 = new PasswordStorage(Attribute.OCCURRENCE);

    passwordStorage1.addPassword(p);
    passwordStorage1.addPassword(p2);
    passwordStorage1.addPassword(p3);

    Password result1 = passwordStorage1.lookup(new Password("1234567890", 1000));
    if (result1 == null || !result1.equals(p))
      return false;

    Password result2 = passwordStorage1.lookup(new Password("test", 500));
    if (result2 == null || !result2.equals(p2))
      return false;

    Password result3 = passwordStorage1.lookup(new Password("iloveyou", 765));
    if (result3 == null || !result3.equals(p3))
      return false;

    // Test lookup for a non-existent password
    Password nonExistentResult = passwordStorage1.lookup(new Password("nahnahnah", 50));
    if (nonExistentResult != null) {
      return false;
    }

    return true; // TODO
  }

  // STUDENT TODO: add javadoc

  /**
   * Validates the PasswordStorage class addPassword() method. Create at least two DIFFERENT
   * Password objects and compare them on each of the Attribute values.
   *
   * @return true if PasswordStorage's addPassword() works as expected, false otherwise
   */
  public static boolean testAddPassword() {
    Password p = new Password("1234567890", 1000);
    Password p2 = new Password("test", 500);
    Password p3 = new Password("iloveyou", 765);
    Password p4 = new Password("qwerty", 250);

    PasswordStorage passwordStorage1 = new PasswordStorage(Attribute.OCCURRENCE);

    passwordStorage1.addPassword(p);
    if (passwordStorage1.size() != 1) {
      return false;
    }
    String expected1 = p.toString() + "\n";
    if (!passwordStorage1.toString().equals(expected1))
      return false;

    passwordStorage1.addPassword(p2);
    if (passwordStorage1.size() != 2) {
      return false;
    }
    String expected2 = p2.toString() + "\n" + p.toString() + "\n";
    if (!passwordStorage1.toString().equals(expected2))
      return false;

    passwordStorage1.addPassword(p3);
    if (passwordStorage1.size() != 3) {
      return false;
    }
    String expected3 = p2.toString() + "\n" + p3.toString() + "\n" + p.toString() + "\n";
    if (!passwordStorage1.toString().equals(expected3))
      return false;

    passwordStorage1.addPassword(p4);
    if (passwordStorage1.size() != 4) {
      return false;
    }
    String expected4 =
        p4.toString() + "\n" + p2.toString() + "\n" + p3.toString() + "\n" + p.toString() + "\n";
    if (!passwordStorage1.toString().equals(expected4))
      return false;


    return true; // TODO
  }

  // STUDENT TODO: add javadoc

  /**
   * Validates the PasswordStorage class removePassword() method. Create at least two DIFFERENT
   * Password objects and compare them on each of the Attribute values.
   *
   * @return true if PasswordStorage's removePassword() works as expected, false otherwise
   */
  public static boolean testRemovePassword() {
    // p4 < p2 < p3 < p < p5 < p7 < p6
    Password p = new Password("1234567890", 1000);
    Password p2 = new Password("test", 500);
    Password p3 = new Password("iloveyou", 765);
    Password p4 = new Password("qwerty", 250);
    Password p5 = new Password("admin", 1002);
    Password p6 = new Password("password", 2232);
    Password p7 = new Password("abc123", 2090);

    PasswordStorage passwordStorage1 = new PasswordStorage(Attribute.OCCURRENCE);
    // here we build a BST in the form of [1000, 500, 1002, 250, 765, no, 2232, no, no, no, no,
    // 2090, no] where 500 has two children

    passwordStorage1.addPassword(p);
    passwordStorage1.addPassword(p2);
    passwordStorage1.addPassword(p3);
    passwordStorage1.addPassword(p4);
    passwordStorage1.addPassword(p5);
    passwordStorage1.addPassword(p6);
    passwordStorage1.addPassword(p7);

    // test case 1: remove a leaf - 0 child
    {
      passwordStorage1.removePassword(p7);
      if (passwordStorage1.lookup(p7) != null && passwordStorage1.size() != 6)
        return false;
    }
    // test case 2: remove a node with 1 child
    {
      // we'd better verify that 2232 still exist
      passwordStorage1.removePassword(p5);
      if (passwordStorage1.lookup(p5) != null && passwordStorage1.size() != 5)
        return false;
      if (passwordStorage1.lookup(p6) == null)
        return false;
    }
    // test case 3: remove a node with 2 children
    {
      // we'd better verify that 250 and 765 still exist as well as 250(predecessor) being the node
      passwordStorage1.removePassword(p2);
      if (passwordStorage1.lookup(p2) != null && passwordStorage1.size() != 4)
        return false;
      if (passwordStorage1.lookup(p4) == null && passwordStorage1.lookup(p3) == null)
        return false;
      if (!passwordStorage1.root.getLeft().getPassword().toString()
          .equals("qwerty" + "(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]"))
        return false;
    }
    // test case 4: remove a root (rebuild a BST)
    {
      PasswordStorage passwordStorage2 = new PasswordStorage(Attribute.OCCURRENCE);
      // here we build a BST in the form of [1000, 500, 1002, 250, 765, no, 2232, no, no, no, no,
      // 2090, no] where 500 has two children

      passwordStorage2.addPassword(p);
      passwordStorage2.addPassword(p2);
      passwordStorage2.addPassword(p3);
      passwordStorage2.addPassword(p4);
      passwordStorage2.addPassword(p5);
      passwordStorage2.addPassword(p6);
      passwordStorage2.addPassword(p7);

      passwordStorage2.removePassword(p); // AFter removing, left and right subtree become null
      if (passwordStorage2.lookup(p) != null && passwordStorage2.size() != 6)
        return false;
      // we'd better verify that 765 is the new root according to our algorithm

      if (passwordStorage2.lookup(p2) == null && passwordStorage2.lookup(p5) == null)
        return false;

      if (!passwordStorage2.root.getPassword().toString()
          .equals("iloveyou" + "(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]"))
        return false;

    }


    return true; // TODO
  }


  public static void main(String[] args) {
    runAllTests();
  }

  public static boolean runAllTests() {
    boolean compareToPassed = testPasswordCompareTo();
    boolean nodeStatusPassed = testNodeStatusMethods();
    boolean basicMethodsPassed = testBasicPasswordStorageMethods();
    boolean toStringPassed = testToString();
    boolean isValidBSTPassed = testIsValidBST();
    boolean lookupPassed = testLookup();
    boolean addPasswordPassed = testAddPassword();
    boolean removePasswordPassed = testRemovePassword();

    System.out.println("Password compareTo: " + (compareToPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordNode Status Methods: " + (nodeStatusPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage Basic Methods: " + (basicMethodsPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage toString: " + (toStringPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage isValidBST: " + (isValidBSTPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage lookup: " + (lookupPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage addPassword: " + (addPasswordPassed ? "PASS" : "FAIL"));
    System.out.println(
        "PasswordStorage removePassword: " + (removePasswordPassed ? "PASS" : "FAIL"));

    // AND ANY OTHER ADDITIONAL TEST METHODS YOU MAY WANT TO WRITE!

    return compareToPassed && nodeStatusPassed && basicMethodsPassed && toStringPassed && isValidBSTPassed && lookupPassed && addPasswordPassed && removePasswordPassed;
  }

}

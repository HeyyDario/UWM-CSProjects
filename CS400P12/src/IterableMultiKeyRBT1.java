// --== CS400 Fall 2023 File Header Information ==--
// Name: <Pai Peng>
// Email: <ppeng24@wisc.edu>
// Group: <c06>
// TA: <Binwei Yao>
// Lecturer: <Florian Heimerl>
// Notes to Grader: <>


import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class IterableMultiKeyRBT1<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T> {
  private Comparable<T> startPoint = null;
  private int totalKeys = 0;



  /**
   * Inserts value into tree that can store multiple objects per key by keeping
   * lists of objects in each node of the tree.
   * @param key object to insert
   * @return true if a new node was inserted, false if the key was added into an existing node
   */
  @Override
  public boolean insertSingleKey(T key) {
    //TODO check if the tree already contains a node with
    KeyList<T> lst = new KeyList<T>(key);
    Node<KeyListInterface<T>> keyinTree = super.findNode(lst);
    this.totalKeys++;
    if (keyinTree != null){
      keyinTree.data.addKey(key);
      return false;
    }
    else{
      super.insert(lst);
    }
    return true;
  }
  /**
   * @return the number of values in the tree.
   */
  @Override
  public int numKeys() {
    return this.totalKeys;
  }
  /**
   * Returns an iterator that does an in-order iteration over the tree.
   */
  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      Stack<Node<KeyListInterface<T>>> st = getStartStack();
      Iterator<T> currListIter;
      /** Check if there are more keys in the current node -> return true
       *  If there is no more keys, then update currListIterator to the next -> return true
       *  If Stack empty -> return false
       * @return boolean
       */
      @Override
      public boolean hasNext() {
        if (currListIter == null){
          if (!st.isEmpty()){
            currListIter = st.peek().data.iterator();
          }
          else{
            return false;
          }

        }
        if (!currListIter.hasNext()) {
          if (st.isEmpty()) {
            return false;
          }
          else{  // if stack is not empty
            // stack pop and push right node
            Node<KeyListInterface<T>> currNode = st.pop();
            if(currNode.down[1] != null){
              st.push(currNode.down[1]);
              currListIter = currNode.data.iterator();
            }
            //stack in left.
            while (currNode.down[0] != null) {
              currNode = currNode.down[0];
              st.push(currNode);
            }
          }
        }
        else {
          //currListIter.next();
        }
        return true;

      }
      /** Check the current value. if hasNext return true, return value, if not return null
       *
       * @return return the iterator of the current node visited
       */
      @Override
      public T next() {
        // if hasnext is false
        if (hasNext()) {
          return currListIter.next();
        }
        throw new NoSuchElementException("there is no more element");

      }
    };
  }

  protected Stack<Node<KeyListInterface<T>>> getStartStack() {
    Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
    Node<KeyListInterface<T>> curr = root;
    //TODO check the correctness
    if (startPoint == null) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.down[0];
      }
    } else {
      // If iteration start point is set, initialize with nodes equal to or larger than the start point
      while (curr != null) {
        int comparison = startPoint.compareTo(curr.data.iterator().next());
        if (comparison == 0) {
          stack.push(curr);
          break;
        } else if (comparison < 0) { // if startPoint < curr
          stack.push(curr);
          curr = curr.down[0]; // go to left
        } else {
          curr = curr.down[1]; //go to right
        }
      }

    }

    return stack;
  }


  /**
   * Sets the starting point for iterations. Future iterations will start at the
   * starting point or the key closest to it in the tree. This setting is remembered
   * until it is reset. Passing in null disables the starting point.
   * @param startPoint the start point to set for iterations
   */
  @Override
  public void setIterationStartPoint(Comparable<T> startPoint) {
    this.startPoint =startPoint;
  }

  @Override
  public void clear(){
    super.clear();
    this.size=0;
  }



  /**
   * CheckRBTree insertSingleKey by checking the method's output
   */
  @Test
  public void checkRBT(){
    boolean check;
    int count = 0;
    IterableMultiKeyRBT<Integer> rbt = new IterableMultiKeyRBT<>();
    check = rbt.insertSingleKey(10);
    assertTrue(check, "Failed to insert key 10 into the Red-Black Tree");

    check = rbt.insertSingleKey(20);
    assertTrue(check, "Failed to insert key 20 into the Red-Black Tree");

    check = rbt.insertSingleKey(30);
    assertTrue(check, "Failed to insert key 30 into the Red-Black Tree");

    check = rbt.insertSingleKey(40);
    assertTrue(check, "Failed to insert key 40 into the Red-Black Tree");
    assertEquals(4, rbt.numKeys(), "Failed to insert, the number of keys is not 4");
  }

  /**
   * check the implementation of iterator
   */
  @Test
  public void testComplexIterator() {
    // Inserting keys
    IterableMultiKeyRBT<String> rbt = new IterableMultiKeyRBT<>();
    rbt.insertSingleKey("grape");
    rbt.insertSingleKey("apple");
    rbt.insertSingleKey("banana");
    rbt.insertSingleKey("cherry");
    rbt.insertSingleKey("date");
    rbt.insertSingleKey("banana"); // Duplicate key

    Iterator<String> it = rbt.iterator();

    // Check order of keys //if my later week implementation is in order traversal
    assertTrue(it.hasNext());
    assertEquals("apple", it.next(), "Expected 'apple' as the first key");

    assertTrue(it.hasNext());
    assertEquals("banana", it.next(), "Expected 'banana' as the second key");

    assertEquals("banana", it.next(), "Expected 'banana' as the second key");

    assertTrue(it.hasNext());
    assertEquals("cherry", it.next(), "Expected 'cherry' as the third key");

    assertTrue(it.hasNext());
    assertEquals("date", it.next(), "Expected 'date' as the fourth key");

    assertTrue(it.hasNext());
    assertEquals("grape", it.next(), "Expected 'grape' as the fifth key");

    assertFalse(it.hasNext(), "No more keys should be present");

  }

  @Test
  public void testSetIterationStartPoint() {
    // Inserting keys
    IterableMultiKeyRBT<String> rbt = new IterableMultiKeyRBT<>();
    rbt.insertSingleKey("apple");
    rbt.insertSingleKey("cherry");
    rbt.insertSingleKey("banana");
    rbt.insertSingleKey("date");
    rbt.insertSingleKey("grape");

    // Setting iteration start point to "banana"
    rbt.setIterationStartPoint("banana");
    Iterator<String> it = rbt.iterator();

    assertTrue(it.hasNext());
    assertEquals("banana", it.next(), "Expected 'banana' as the first key after setting start point");
    assertTrue(it.hasNext());
    assertEquals("cherry", it.next(), "Expected 'cherry' as the second key after setting start point");
    rbt.setIterationStartPoint("blueberry");
    it = rbt.iterator();
    assertTrue(it.hasNext());
    assertEquals("cherry", it.next(), "Expected 'cherry' as the closest key to 'blueberry'");
    // test reset
    rbt.setIterationStartPoint(null);
    it = rbt.iterator();
    assertTrue(it.hasNext());
    assertEquals("apple", it.next(), "Expected 'apple' as the first key after resetting start point");
  }




}

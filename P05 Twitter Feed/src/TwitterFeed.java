//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05: TwitterFeed
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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwitterFeed implements ListADT<Tweet>, Iterable<Tweet> {
  private TweetNode head;//The node containing the most recent tweet
  private TweetNode tail;//The node containing the oldest tweet
  private int size;//The number of tweets in this linked list
  private TimelineMode mode;//The iteration mode for the timeline display
  private static double ratio;
  //The ratio of likes to total engagement that we want to see; set to .5 by default

  /**
   * Default constructor; creates an empty TwitterFeed by setting all data fields to their default
   * values, and the timeline mode to CHRONOLOGICAL.
   */
  public TwitterFeed() {
    head = null;
    tail = null;
    size = 0;
    mode = TimelineMode.CHRONOLOGICAL;
    ratio = 0.5;
  }

  /**
   * Accessor for the size of the feed.
   *
   * @return the number of TweetNodes in this TwitterFeed
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Determines whether this feed is empty. Recommend checking MORE than just the size field to get
   * this information, though if all methods are implemented correctly the size field's value will
   * be sufficient.
   *
   * @return true if there are NO TweetNodes in this TwitterFeed, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return this.size == 0 && this.head == null && this.tail == null;
  }

  /**
   * Determines whether a given Tweet is present in the TwitterFeed. Use Tweet's equals() method!
   *
   * @param findObject - the Tweet to search for
   * @return true if the Tweet is present, false otherwise
   */
  @Override
  public boolean contains(Tweet findObject) {
    TweetNode current = head;
    while (current != null) {
      if (current.getTweet() != null && current.getTweet().equals(findObject)) {
        return true;
      }
      current = current.getNext();
    }
    return false;
  }

  /**
   * Accessor method for the index of a given Tweet in the TwitterFeed.
   *
   * @param findObject - the Tweet to search for
   * @return the index of the Tweet in the TwitterFeed if present, -1 if not
   */
  @Override
  public int indexOf(Tweet findObject) {
    int index = 0;
    TweetNode current = head;
    while (current != null) {
      if (current.getTweet().equals(findObject)) {
        return index;
      }
      index++;
      current = current.getNext();
    }
    return -1;
  }

  /**
   * Accessor method for the Tweet at a given index.
   *
   * @param index - the index of the Tweet in question
   * @return the Tweet object at that index (NOT its TweetNode!)
   * @throws IndexOutOfBoundsException - if the index is negative or greater than the largest index
   *                                   of the TwitterFeed
   */
  @Override
  public Tweet get(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException();

    TweetNode current = head;
    for (int i = 0; i < index; i++) {
      current = current.getNext();
    }
    return (Tweet) current.getTweet(); //FIXME HERE
  }

  /**
   * Accessor method for the first Tweet in the TwitterFeed.
   *
   * @return the Tweet object at the head of the linked list
   * @throws java.util.NoSuchElementException - if the TwitterFeed is empty
   */
  public Tweet getHead() throws NoSuchElementException {
    if (isEmpty())
      throw new NoSuchElementException();
    return (Tweet) this.head.getTweet();
  }

  /**
   * Accessor method for the last Tweet in the TwitterFeed.
   *
   * @return the Tweet object at the tail of the linked list
   * @throws NoSuchElementException - if the TwitterFeed is empty
   */
  public Tweet getTail() throws NoSuchElementException {
    if (isEmpty())
      throw new NoSuchElementException();
    return (Tweet) this.tail.getTweet();
  }

  /**
   * Adds the given Tweet to the tail of the linked list.
   *
   * @param newObject - the Tweet to add
   */
  @Override
  public void addLast(Tweet newObject) {
    TweetNode newNode = new TweetNode(newObject); // FIXME HERE In TweetNode Class,
    // add<Tweet>
    if (isEmpty()) {
      this.head = newNode;
      this.tail = newNode;
    } else {
      tail.setNext(newNode);
      this.tail = newNode;
    }
    size++;
  }

  /**
   * Adds the given Tweet to the head of the linked list.
   *
   * @param newObject - the Tweet to add
   */
  @Override
  public void addFirst(Tweet newObject) {
    TweetNode newNode = new TweetNode(newObject);
    if (isEmpty()) {
      this.head = newNode;
      this.tail = newNode;
    } else {
      newNode.setNext(head);
      this.head = newNode;
    }
    size++;
  }

  /**
   * Adds the given Tweet to a specified position in the linked list.
   *
   * @param index     - the position at which to add the new Tweet
   * @param newObject - the Tweet to add
   * @throws IndexOutOfBoundsException - if the index is negative or greater than the size of the
   *                                   linked list
   */
  @Override
  public void add(int index, Tweet newObject) throws IndexOutOfBoundsException {
    if (index < 0 || index > size)
      throw new IndexOutOfBoundsException();

    if (index == 0) {
      addFirst(newObject);
    } else if (index == size) {
      addLast(newObject);
    } else {
      TweetNode current = head;
      for (int i = 0; i < index - 1; i++) {
        current = current.getNext();
      }
      TweetNode newNode = new TweetNode(newObject, current.getNext());
      current.setNext(newNode);
      size++;
    }
  }

  /**
   * Removes and returns the Tweet at the given index.
   *
   * @param index - the position of the Tweet to remove
   * @return the Tweet object that was removed from the list
   * @throws IndexOutOfBoundsException - if the index is negative or greater than the size of the
   *                                   linked list
   */
  @Override
  public Tweet delete(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException();

    TweetNode toReturn;
    if (size() == 1) { //edge case: remove from size 1, delete the only element
      //toReturn = this.head;
      this.head = null;
      this.tail = null;
      size--;
    } else if (index == 0) { //delete front  // here it means that size is at least >= 2
      toReturn = this.head; // save toReturn, ie the deleted thing
      this.head = this.head.getNext();
      toReturn.setNext(null);
      size--;
      return (Tweet) toReturn.getTweet();
    } else {
      TweetNode current = head;
      for (int i = 0; i < index - 1; i++) {
        current = current.getNext();
      }
      toReturn = current.getNext();
      current.setNext(current.getNext().getNext());
      if (index == size - 1) { // delete back
        tail = current;
      }
      size--;
      return (Tweet) toReturn.getTweet(); // FIXME HERE WHY need to cast???
    }

    //size--; // FIXME HERE if you delete the only element, then size will be -1
    return null; // FIXME HERE why null??
  }

  /**
   * Sets the iteration mode of this TwitterFeed.
   *
   * @param m - the iteration mode; any value from the TimelineMode enum
   */
  public void setMode(TimelineMode m) {
    this.mode = m;
  }


  /**
   * Creates and returns the correct type of iterator based on the current mode of this TwitterFeed
   *
   * @return any of ChronoTwiterator, VerifiedTwiterator, or RatioTwiterator, initialized to the
   * head of this TwitterFeed list
   */
  @Override
  public Iterator<Tweet> iterator() {
    if (mode == TimelineMode.CHRONOLOGICAL) {
      return (Iterator<Tweet>) new ChronoTwiterator(head);
    } else if (mode == TimelineMode.VERIFIED_ONLY) {
      return (Iterator<Tweet>) new VerifiedTwiterator(head);
    } else {
      return (Iterator<Tweet>) new RatioTwiterator(head, ratio);
    }
  }


}

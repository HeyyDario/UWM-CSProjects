//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05: VerifiedTwiterator
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

/**
 * This class is an iterator that moves through tweets in reverse chronological order by verified
 * users only.
 */
public class VerifiedTwiterator implements Iterator<Tweet> {
  private TweetNode next; // the next linked node in the list

  /**
   * Constructs a new twiterator at the given starting node
   *
   * @param startNode - the node to begin the iteration at
   */
  public VerifiedTwiterator(TweetNode startNode) {
    this.next = startNode;
  }

  /**
   * Checks whether there is a next tweet to return.
   *
   * @return true if there is a next tweet, false if the value of next is null
   */
  @Override
  public boolean hasNext() {
    return next != null;
  }

  /**
   * Returns the next tweet in the iteration if one exists, and advances next to the next tweet by a
   * verified user.
   *
   * @return the next tweet in the iteration if one exists
   * @throws java.util.NoSuchElementException - if there is not a next tweet to return
   */
  @Override
  public Tweet next() throws NoSuchElementException {
    if (!hasNext())
      throw new NoSuchElementException();

    Tweet toReturn = (Tweet) next.getTweet();

    next = next.getNext();
    while (next != null && !((Tweet) next.getTweet()).isUserVerified()) {
      next = next.getNext();
    }
    return toReturn;
  }
}

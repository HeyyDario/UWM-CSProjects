//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05: RatioTwiterator
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
 * This class is an iterator that moves through tweets in reverse chronological order by high like
 * ratio only.
 */
public class RatioTwiterator implements Iterator<Tweet> {
  private TweetNode next;//the next linked node in the list
  private final double THRESHOLD;
  //The minimum threshold value for the ratio of likes to total engagement

  /**
   * Constructs a new twiterator at the given starting node
   *
   * @param startNode - the node to begin the iteration at
   * @param threshold - the minimum threshold value for the ratio of likes to total engagement,
   *                  assumed to be between 0.0 and 1.0 thanks to range checking in Timeline
   */
  public RatioTwiterator(TweetNode startNode, double threshold) {
    this.next = startNode;
    THRESHOLD = threshold;
  }

  /**
   * Checks whether there is a next tweet to return.
   *
   * @return true if there is a next tweet, false if the value of next is null
   */
  @Override
  public boolean hasNext() {
    return this.next != null;
  }

  /**
   * Returns the next tweet in the iteration if one exists, and advances next to the next tweet with
   * a likes ratio in excess of the given threshold
   *
   * @return the next tweet in the iteration if one exists
   * @throws java.util.NoSuchElementException - if there is not a next tweet to return
   */
  @Override
  public Tweet next() throws NoSuchElementException{
    if (!hasNext())
      throw new NoSuchElementException();

    Tweet toReturn = (Tweet) next.getTweet();

    next = next.getNext();
    while (next != null && ((Tweet) next.getTweet()).getLikesRatio() <= THRESHOLD){
      next = next.getNext();
    }
    return toReturn;
  }
}

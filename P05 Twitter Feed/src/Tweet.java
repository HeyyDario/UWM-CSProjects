//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05: Tweet
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

import java.util.Calendar;
import java.util.Date;
/**
 * This class is used to model the tweets the users make.
 */
public class Tweet {
  private static Calendar dateGenerator; // A shared Calendar object for this class to use to
  // generate consistent dates.
  private static final int MAX_LENGTH = 280; // A value determining the maximum length of a tweet.
  private int numLikes; // The number of likes this tweet has
  private int numRetweets; // The number of retweets this tweet has.
  private String text; // The text of this tweet.
  private Date timestamp; // The date and time this tweet was posted, a Date object created by
  // calling dateGenerator.getTime() at construction time.
  private User user; // The user associated with this tweet.

  /**
   * Creates a fresh, new tweet by the given user. This tweet has no likes or retweets yet, and its
   * timestamp should be set to the current time.
   *
   * @param user - the User posting this tweet
   * @param text - the text of the tweet
   * @throws IllegalArgumentException - if the tweet's text exceeds MAX_LENGTH characters
   * @throws NullPointerException     - if the provided text or user are null
   * @throws IllegalStateException    - if the dateGenerator field has not yet been initialized
   */
  public Tweet(User user, String text) {
    if (text.length() > MAX_LENGTH)
      throw new IllegalArgumentException();
    if (text == null || user == null)
      throw new NullPointerException();
    if (dateGenerator == null)
      throw new IllegalStateException();

    this.numLikes = 0;
    this.numRetweets = 0;
    this.timestamp = dateGenerator.getTime(); // FIXME maybe NullPointerException HERE???
    this.user = user;
    this.text = text;
  }

  /**
   * Initializes the dateGenerator static field to the provided Calendar object. For tests which do
   * not require a consistent date, you can use Calendar.getInstance() to get a Calendar set to the
   * current time. If your tests require a consistent date, see the writeup for examples of how to
   * set the time.
   *
   * @param c - the Calendar to use for date generation for this run of the program
   */
  public static void setCalendar(Calendar c) {
    dateGenerator = c;
  }

  /**
   * Accesses the text of this tweet.
   *
   * @return the text of this tweet
   */
  public String getText() {
    return this.text;
  }

  /**
   * Gets the total engagement numbers (likes + retweets) of this tweet.
   *
   * @return the total engagement of this tweet
   */
  public int getTotalEngagement() {
    return numLikes + numRetweets;
  }

  /**
   * Checks whether the author of this tweet is a verified user.
   *
   * @return true if this tweet's User is verified, false otherwise
   */
  public boolean isUserVerified() {
    return this.user.isVerified();
  }

  /**
   * Returns the proportion of the total engagement that is composed of "likes". We only do
   * positive, uplifting ratios around here.
   *
   * @return the ratio of likes to total engagement , as a value between 0.0 and 1.0, or -1 if the
   * total engagement is 0.
   */
  public double getLikesRatio() {
    if (getTotalEngagement() == 0)
      return -1;
    return (double)this.numLikes / (double)this.getTotalEngagement();
  }

  /**
   * Add one(1) to the number of likes for this tweet.
   */
  public void like() {
    this.numLikes++;
  }

  /**
   * Add one(1) to the number of retweets for this tweet.
   */
  public void retweet() {
    this.numRetweets++;
  }

  /**
   * Compares the contents of this tweet to the provided object. If the provided object is a Tweet
   * that contains the same text posted at the same time by the same User (use the toString() method
   * from User to compare these!) then the two Tweets are considered equal regardless of their
   * respective likes/retweets.
   *
   * @param o - the object to compare this Tweet to
   * @return true if this Tweet is equivalent to the provided object, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    Tweet tmp = (Tweet) o;

    if (!user.toString().equals(tmp.user.toString())) return false;
    return text.equals(tmp.text);
  }

  /**
   * A string representation of this tweet. See writeup for examples.
   * @return a formatted string representation of this tweet
   */
  @Override
  public String toString(){
    String time = timestamp.toString();

    String toReturn = "tweet from " + user.toString() + " at " + time + ":\n";
    toReturn += "-- " + text + "\n";
    toReturn += "-- likes: " + numLikes + "\n";
    toReturn += "-- retweets: " + numRetweets;

    return toReturn;
  }
}

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05: TwiteratorTester
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

/**
 * Contains methods for testing the classes related to P05 Twitter Feed.
 *
 * @author Chengtao Dai
 */
public class TwiteratorTester {

  /**
   * This method is used to verify that the User class is working properly.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testUser() {
    try {
      // test cases for user class
      User user1 = new User("dario");
      User user2 = new User("cassie");

      // Test getUsername()
      if (!user1.getUsername().equals("dario") || !user2.getUsername().equals("cassie"))
        return false;

      // Test isVerified()
      if (user1.isVerified())
        return false;

      // Test verify()
      user1.verify();
      if (!user1.isVerified())
        return false;

      // Test revokeVerification()
      user1.revokeVerification();
      if (user1.isVerified())
        return false;

      // Test toString()
      if (!user1.toString().equals("@dario") || !user2.toString().equals("@cassie"))
        return false;

      // Test verify user toString()
      user1.verify();
      if (!user1.toString().equals("@dario*"))
        return false;

      //Test unvalid username
      User user3 = new User("leon*");
      User user4 = new User(null);
      User user5 = new User("");
      User user6 = new User("    ");

    }catch (IllegalArgumentException e){
      return true;
    }
    return true;
  }

  /**
   * This method used to verify that the Tweet class is working properly.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testTweet() {
    User user1 = new User("dario");
    User user2 = new User("cassie");

    Calendar test = Calendar.getInstance();
    test.set(2012, Calendar.MAY, 22, 14, 46, 03);
    Tweet.setCalendar(test);

    Tweet tweet1 = new Tweet(user1, "This weekend I went to Nanjing City.");
    Tweet tweet2 = new Tweet(user2, "This weekend I went to Nanjing City, too.");

    // Test getText()
    if (!tweet1.getText().equals("This weekend I went to Nanjing City.") || !tweet2.getText()
        .equals("This weekend I went to Nanjing City, too."))
      return false;

    // Test getTotalEngagement()
    if (tweet1.getTotalEngagement() != 0 || tweet2.getTotalEngagement() != 0)
      return false;

    // Test like() and retweet()
    tweet1.like();
    tweet2.retweet();
    if (tweet1.getTotalEngagement() != 1 || tweet2.getTotalEngagement() != 1)
      return false;

    // Test isUserVerified()
    if (tweet1.isUserVerified() || tweet2.isUserVerified())
      return false;

    // Test getLikesRatio()
    if (tweet1.getLikesRatio() != 1.0 || tweet2.getLikesRatio() == 1.0)
      return false;

    // Test toString()
    if (!tweet1.toString().equals(
        "tweet from @dario at Tue May 22 14:46:03 CDT " + "2012:\n-- This weekend I went " + "to "
            + "Nanjing City.\n-- likes:" + " " + "1\n-- " + "retweets: 0") || !tweet2.toString()
        .equals(
            "tweet from @cassie at Tue May 22 14:46:03 " + "CDT " + "2012:\n-- This weekend I " + "went to Nanjing City, too.\n-- " + "likes: 0\n-- " + "retweets: 1"))
      return false;

    return true;
  }

  /**
   * This method is used to verify that the accessor methods of the two nodes are working properly.
   *
   * Creates two TweetNodes and connect one to the other.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testNode() {
    try {
      Calendar test = Calendar.getInstance();
      test.set(2012, Calendar.MAY, 22, 14, 46, 03);
      Tweet.setCalendar(test);

      Tweet tweet1 = new Tweet(new User("dario"), "NJ1");
      Tweet tweet2 = new Tweet(new User("cassie"), "NJ2");
      Tweet tweet3 = new Tweet(new User("leon"), "NJ3");

      TweetNode node1 = new TweetNode(tweet1);
      TweetNode node2 = new TweetNode(tweet2);

      // Connect the nodes
      node1.setNext(node2);
      if (!node1.getTweet().equals(tweet1))
        return false;
      if (!node2.getTweet().equals(tweet2))
        return false;
      TweetNode node3 = new TweetNode(tweet3, node1); // node3 -> node1 -> node2
      if (!node3.getNext().equals(node1))
        return false;
    } catch (Exception e) {
      return false;
    }
    // Verify accessor methods
    return true;
  }

  /**
   * This method is used to verify that all the ADD methods are working properly.
   *
   * Creates a TwitterFeed and verify that it is empty and has size 0. Use addFirst()/addLast() to
   * add a Tweet to it. Verify that it is no longer empty, has size 1, contains() the Tweet you just
   * added, and that get(0) matches that Tweet. Try this a few more times, and also test
   * getHead()/getTail().
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testAddTweet() {
    TwitterFeed twitterFeed = new TwitterFeed();

    // Verify that it is empty and has size 0
    if (!twitterFeed.isEmpty() || twitterFeed.size() != 0) {
      return false;
    }

    // Use addFirst() to add a tweet
    Tweet tweet1 = new Tweet(new User("dario"), "NJ1");
    twitterFeed.addFirst(tweet1);

    // Verify that it is no longer empty, has size 1, contains() the Tweet you just added
    // also test getHead and getTail
    if (twitterFeed.isEmpty() || twitterFeed.size() != 1 || !twitterFeed.contains(
        tweet1) || !twitterFeed.getHead().equals(tweet1) || !twitterFeed.getTail()
        .equals(tweet1) || !twitterFeed.get(0).equals(tweet1)) {
      return false;
    }

    // Use addLast() to add a tweet
    Tweet tweet2 = new Tweet(new User("cassie"), "NJ2");
    twitterFeed.addLast(tweet2);

    // Verify the changes after adding the second tweet
    if (twitterFeed.isEmpty() || twitterFeed.size() != 2 || !twitterFeed.contains(
        tweet2) || !twitterFeed.getHead().equals(tweet1) || !twitterFeed.getTail()
        .equals(tweet2) || !twitterFeed.get(1).equals(tweet2)) {
      return false;
    }

    // Add a few more tweets
    Tweet tweet3 = new Tweet(new User("leon"), "NJ3");
    Tweet tweet4 = new Tweet(new User("gwen"), "NJ4");
    twitterFeed.addFirst(tweet3);
    twitterFeed.addLast(tweet4);

    // Verify the changes after adding more tweets
    if (twitterFeed.isEmpty() || twitterFeed.size() != 4 || !twitterFeed.contains(
        tweet3) || !twitterFeed.contains(tweet4) || !twitterFeed.getHead()
        .equals(tweet3) || !twitterFeed.getTail().equals(tweet4) || !twitterFeed.get(0)
        .equals(tweet3) || !twitterFeed.get(3).equals(tweet4)) {
      return false;
    }

    return true;
  }

  /**
   * This method is used to verify that the insert methods are working properly.
   *
   * create a TwitterFeed and several Tweet objects. Add them using add() with various indexes.
   * Verify that the size is correct, and that get() with various indexes returns the Tweets you
   * expect. You may also wish to test getHead()/getTail().
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testInsertTweet() {
    TwitterFeed twitterFeed = new TwitterFeed();

    // Create tweets
    Tweet tweet1 = new Tweet(new User("dario"), "NJ1");
    Tweet tweet2 = new Tweet(new User("cassie"), "NJ2");

    // Add tweets using add()
    twitterFeed.add(0, tweet1);
    twitterFeed.add(0, tweet2);

    // Verify the size and contents of the TwitterFeed
    if (twitterFeed.size() != 2 || !twitterFeed.get(0).equals(tweet2) || !twitterFeed.get(1)
        .equals(tweet1)) {
      return false;
    }

    // Add a tweet at the end
    Tweet tweet3 = new Tweet(new User("leon"), "NJ3");
    Tweet tweet4 = new Tweet(new User("gwen"), "NJ4");
    twitterFeed.addLast(tweet3);
    twitterFeed.add(1, tweet4);

    // Verify the size and contents after adding the last tweet
    if (twitterFeed.size() != 4 || !twitterFeed.get(3).equals(tweet3) || !twitterFeed.getTail()
        .equals(tweet3) || !twitterFeed.get(1).equals(tweet4)) {
      return false;
    }

    return true;
  }

  /**
   * This method is used to verify that the delete method is working properly.
   *
   * create a TwitterFeed and add at least five (5) Tweet objects. Try removing the last Tweet and
   * verify that getTail() has been updated correctly; try removing the first Tweet and verify that
   * getHead() has been updated correctly. Then try removing a Tweet from a middle index (like 1)
   * and verify that when you get() that index, it returns the value you expect.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testDeleteTweet() {
    TwitterFeed twitterFeed = new TwitterFeed();

    // Add some tweets
    Tweet tweet1 = new Tweet(new User("dario"), "NJ1");
    Tweet tweet2 = new Tweet(new User("cassie"), "NJ2");
    Tweet tweet3 = new Tweet(new User("leon"), "NJ3");
    Tweet tweet4 = new Tweet(new User("gwen"), "NJ4");
    twitterFeed.addLast(tweet1);
    twitterFeed.addLast(tweet2);
    twitterFeed.addLast(tweet3);
    twitterFeed.addLast(tweet4);

    // Verify the initial state
    if (twitterFeed.size() != 4 || !twitterFeed.getHead().equals(tweet1) || !twitterFeed.getTail()
        .equals(tweet4)) {
      return false;
    }

    // Remove the last tweet
    Tweet deletedTweet1 = (Tweet) twitterFeed.delete(3);

    // Verify the state after removing the last tweet
    if (twitterFeed.size() != 3 || !twitterFeed.getTail().equals(tweet3) || !deletedTweet1.equals(
        tweet4)) {
      return false;
    }

    // Remove the first tweet
    Tweet deletedTweet2 = (Tweet) twitterFeed.delete(0);

    // Verify the state after removing the first tweet
    if (twitterFeed.size() != 2 || !twitterFeed.getHead().equals(tweet2) || !deletedTweet2.equals(
        tweet1)) {
      return false;
    }

    // Remove a tweet from the middle (index 1)
    Tweet deletedTweet3 = (Tweet) twitterFeed.delete(1);

    // Verify the state after removing a tweet from the middle
    if (twitterFeed.size() != 1 || !twitterFeed.get(0).equals(tweet2) || !deletedTweet3.equals(
        tweet3)) {
      return false;
    }

    return true;
  }

  /**
   * This method is used to verify that ChronoTwiterator Class is working properly.
   *
   * Create a TwitterFeed object and add at least three(3) Tweets to it. Then, use an enhanced for
   * loop to iterate directly through the TwitterFeed and verify that all Tweets are returned in the
   * correct order.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testChronoTwiterator() {
    // Initialize dateGenerator
    Tweet.setCalendar(Calendar.getInstance());

    // Create a TwitterFeed object
    TwitterFeed twitterFeed = new TwitterFeed();

    // Add at least three Tweets to the TwitterFeed
    Tweet tweet1 = new Tweet(new User("dario"), "NJ1");
    Tweet tweet2 = new Tweet(new User("cassie"), "NJ2");
    Tweet tweet3 = new Tweet(new User("leon"), "NJ3");

    twitterFeed.addFirst(tweet1);
    twitterFeed.addFirst(tweet2);
    twitterFeed.addFirst(tweet3);
    twitterFeed.setMode(TimelineMode.CHRONOLOGICAL);

    // Use the ChronoTwiterator to iterate through the TwitterFeed
    ChronoTwiterator chronotwiterator = (ChronoTwiterator) twitterFeed.iterator();

    // Verify that all Tweets are returned in the correct order

    boolean toReturn = true;
    for (Object tweet : twitterFeed) {
      if (!tweet.equals(chronotwiterator.next())) {
        toReturn = false;
        break;
      }
    }

    return toReturn;
  }

  /**
   * This method is used to verify that VerifiedTwiterator Class is working properly.
   *
   * Create a TwitterFeed object and add at least three(3) Tweets to it. Then, make user1 and user3
   * of User object become verify. Then,use an enhanced for loop to iterate directly through the
   * TwitterFeed and verify that all Tweets are returned in the correct order.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testVerifiedTwiterator() {
    // Initialize dateGenerator
    Tweet.setCalendar(Calendar.getInstance());

    // Create a TwitterFeed object
    TwitterFeed twitterFeed = new TwitterFeed();

    // Add at least three Tweets to the TwitterFeed, including one from a verified user
    User user1 = new User("dario");
    User user2 = new User("cassie");
    User user3 = new User("leon");
    Tweet tweet1 = new Tweet(user1, "NJ1");
    Tweet tweet2 = new Tweet(user2, "NJ2");
    Tweet tweet3 = new Tweet(user3, "NJ3");
    user1.verify();
    user3.verify();

    twitterFeed.addFirst(tweet1);
    twitterFeed.addFirst(tweet2);
    twitterFeed.addFirst(tweet3);
    twitterFeed.setMode(TimelineMode.VERIFIED_ONLY);

    // Use the VerifiedTwiterator to iterate through the TwitterFeed
    VerifiedTwiterator verifiedTwiterator = (VerifiedTwiterator) twitterFeed.iterator();

    // Verify that the iterator skips over any tweets made by unverified users
    boolean toReturn = true;
    while (verifiedTwiterator.hasNext()) {
      Tweet tweet = verifiedTwiterator.next();
      if (!tweet.isUserVerified()) {
        toReturn = false;
        break;
      }
    }

    return toReturn;
  }

  /**
   * This method is used to verify that RatioTwiterator Class is working properly.
   *
   * Create a TwitterFeed object and add at least three(3) Tweets to it. Then, make user1, user2 and
   * user3 of User object have a likesRatio of 20%, 80% and 75% respectively and set the default
   * THRESHOLD ratio to be 0.5. Then,use an enhanced for loop to iterate directly through the
   * TwitterFeed and verify that all Tweets are returned in the correct order.
   *
   * @return true if all scenarios pass and false otherwise
   */
  public static boolean testRatioTwiterator() {
    // Initialize dateGenerator
    Tweet.setCalendar(Calendar.getInstance());

    // Create a TwitterFeed object
    TwitterFeed twitterFeed = new TwitterFeed();

    // Add at least three Tweets to the TwitterFeed, including one from a verified user
    User user1 = new User("dario");
    User user2 = new User("cassie");
    User user3 = new User("leon");
    Tweet tweet1 = new Tweet(user1, "NJ1");
    Tweet tweet2 = new Tweet(user2, "NJ2");
    Tweet tweet3 = new Tweet(user3, "NJ3");

    // make the ratio of user1, user2, user3 20%, 80%, 75% respectively
    tweet1.like();
    tweet1.retweet();
    tweet1.retweet();
    tweet1.retweet();
    tweet1.retweet();

    for (int i = 0; i < 4; i++) {
      tweet2.like();//like 4 times
    }
    tweet2.retweet();

    for (int i = 0; i < 3; i++) {
      tweet3.like();//like 3 times
    }
    tweet3.retweet();

    twitterFeed.addFirst(tweet1);
    twitterFeed.addFirst(tweet2);
    twitterFeed.addFirst(tweet3);
    twitterFeed.setMode(TimelineMode.LIKE_RATIO);

    // Use the RatioTwiterator to iterate through the TwitterFeed
    RatioTwiterator ratioTwiterator = (RatioTwiterator) twitterFeed.iterator();
    //ratio is set to be .5 by default

    // Verify that the iterator skips over any tweets made by unverified users
    boolean toReturn = true;
    while (ratioTwiterator.hasNext()) {
      Tweet tweet = ratioTwiterator.next();
      double ratio = tweet.getLikesRatio();
      if (ratio < 0.5) {
        toReturn = false;
        break;
      }
    }

    return toReturn;
  }

  /**
   * Runs each of the tester methods and displays their result.
   *
   * @param args - unused
   */
  public static void main(String[] args) {
    System.out.println("The result for the tester of User is " + TwiteratorTester.testUser());
    System.out.println("The result for the tester of Tweet is " + TwiteratorTester.testTweet());
    System.out.println("The result for the tester of TweetNode is " + TwiteratorTester.testNode());
    System.out.println(
        "The result for the tester of AddTweet is " + TwiteratorTester.testAddTweet());
    System.out.println(
        "The result for the tester of InsertTweet is " + TwiteratorTester.testInsertTweet());
    System.out.println(
        "The result for the tester of DeleteTweet is " + TwiteratorTester.testDeleteTweet());
    System.out.println(
        "The result for the tester of ChronoTwiterator is " + TwiteratorTester.testChronoTwiterator());
    System.out.println(
        "The result for the tester of VerifiedTwiterator is " + TwiteratorTester.testVerifiedTwiterator());
    System.out.println(
        "The result for the tester of RatioTwiterator is " + TwiteratorTester.testRatioTwiterator());

  }
}

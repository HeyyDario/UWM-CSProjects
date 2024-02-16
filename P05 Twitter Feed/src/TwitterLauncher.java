import java.util.Calendar;

public class TwitterLauncher {

  public static void main(String[] args) {
    User u1 = new User("uwmadison");
    u1.verify();
    User u2 = new User("dril");
    Calendar test = Calendar.getInstance();
    test.set(2023, Calendar.APRIL, 3, 17, 07, 50);
    Tweet.setCalendar(test);
    TwitterFeed feed = new TwitterFeed();
    feed.addFirst(new Tweet(u1, "Join us for Bucky's Big Event next Wednesday! " + "#CelebrateUW"));
    feed.addFirst(new Tweet(u2, "type \"Gaming is back \", if you think gaming is " + "back"));
    feed.addFirst(new Tweet(u1, "It's a GREAT day for @BadgerMHockey! #OnWisconsin"));
    feed.addFirst(
        new Tweet(u1, "Welcome to the University of Wisconsin-Madison, " + "#FutureBadger!"));
    feed.addLast(new Tweet(u2, "got botulism from a pair of bad jeans"));
    Tweet tmp = (Tweet) feed.get(2);
    for (int i = 0; i < 5243; i++)
      tmp.like();
    for (int i = 0; i < 4702; i++)
      tmp.retweet();
    tmp = (Tweet) feed.get(3);
    tmp.like();
    tmp = (Tweet) feed.get(4);
    for (int i = 0; i < 307; i++)
      tmp.like();
    for (int i = 0; i < 4015; i++)
      tmp.retweet();
    System.out.println("---------- \"REVERSE CHRONOLOGICAL\" ORDER -----------");
    for (Object t : feed) {
      System.out.println(t);
    }
    System.out.println("\n---------- VERIFIED ONLY -----------");
    feed.setMode(TimelineMode.VERIFIED_ONLY);
    for (Object t : feed) {
      System.out.println(t);
    }
    System.out.println("\n---------- HIGH LIKES RATIO ONLY -----------");
    feed.setMode(TimelineMode.LIKE_RATIO);
    for (Object t : feed) {
      System.out.println(t);
    }
  }
}

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P06: Password Cracking - Password
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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class represents a password object for a password cracker system.
 *
 * @author Michelle & Chengtao Dai
 */
public class Password {

  private String password; // the actual password
  private String hashedPassword; // the password into a jumbled string, called ciphertext for
  // protection from hackers uses the SHA-1 hash protocol 
  //(don't actually use this for a system in real life, it's broken
  private int occurrences; // how many times that password has been found used across different
  // systems
  private float strengthRating; // how strong the password is, calculated based on length, and use
  // different sets of possible characters

  private static final String MAX_SHA1_HASH = "ffffffffffffffffffffffffffffffffffffffff";
  private static final String MIN_SHA1_HASH = "0000000000000000000000000000000000000000";
  private static final int MAX_LENGTH = 14; // max number of characters a password can have
  private static final int MAX_OCCURRENCES = 1000000000; // max number of times a password
  // could have been repeated, 1 billion

  /**
   * Constructor for a new password object with the given password and number of occurrences.
   *
   * @param password,    the String that is the password
   * @param occurrences, the number of times the password has been used by people
   * @throws IllegalArgumentException if the password is longer than the defined MAX_LENGTH or empty
   *                                  OR the number of occurrences is outside of the range
   *                                  (0,MAX_OCCURRENCES]
   * @author Michelle
   */
  public Password(String password, int occurrences) {
    if (password.length() > MAX_LENGTH || password.isBlank())
      throw new IllegalArgumentException("password too long or blank!");
    if (occurrences <= 0 || occurrences > MAX_OCCURRENCES)
      throw new IllegalArgumentException("invalid num of occurrences");
    this.password = password;
    this.occurrences = occurrences;
    this.strengthRating = computeStrengthRating(password);
    computeAndSetHashedPassword();
  }

  /**
   * Creates a password objects with all fields (used for comparisons) at their lower bound for the
   * minimum value. Used ONLY for implementation of front facing PasswordStorage.isValidBST()
   * method.
   *
   * @return A password with all field values (used for comparisons) at their minimum.
   */
  public static Password getMinPassword() {
    Password min = new Password("min", 1);
    // i'm gonna edit these values locally to bypass the restrictions
    min.occurrences = 0; // the lowest possible occurrences is 1
    min.strengthRating = 0; // lowest possible score is to have 1 character
    min.hashedPassword = MIN_SHA1_HASH; // 0 is smallest hexidecimal value, all hash strings are 40
    // long.
    return min;
  }

  /**
   * Creates a password objects with all fields (used for comparisons) at their upper bound for
   * maximum value. Used ONLY for implementation of front facing PasswordStorage.isValidBST()
   * method.
   *
   * @return A password with all field values (used for comparisons) at their maximum.
   */
  public static Password getMaxPassword() {
    Password max = new Password("max", 100);
    max.occurrences = MAX_OCCURRENCES + 1; // upper bound is one over the allowed occurrences
    max.strengthRating = (float) (4 * 1.75 + MAX_LENGTH + 0.1); // 0.1 over the max possible
    // strengthRating calculated
    max.hashedPassword = MAX_SHA1_HASH;// f is largest hexidecimal value, all hash strings are 40
    // long
    return max;
  }

  /**
   * Computes and sets the hashPassword by hashing the password using the SHA-1 protocol into a
   * string in hexadecimal. Code adapted from the following online resource:
   * https://www.geeksforgeeks.org/sha-1-hash-in-java/
   *
   * @author Michelle
   */
  private void computeAndSetHashedPassword() {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1"); // get the type of hash protocol
      byte[] hash = md.digest(password.getBytes()); // apply the SHA-1 protocol to the string
      BigInteger num = new BigInteger(1, hash);
      hashedPassword = num.toString(16); // convert it to be represented in hexadecimal

      // pad out the hashedPassword with zeros if it is too short
      while (hashedPassword.length() < 40) {
        hashedPassword = "0" + hashedPassword;
      }

    } catch (NoSuchAlgorithmException e) { // if "SHA-1" isn't a supported algorithm, catch the
      // exception
      System.out.println("not a valid hash algorithm!");
    }
  }

  /**
   * Computes a strength rating for the given string. Strength is determined by length and what kind
   * of characters are used.
   *
   * @param s, the string to compute the strength rating
   * @return the strength rating of the string
   * @author Michelle
   */
  private static float computeStrengthRating(String s) {
    int charSet = 0;
    // if you want you can read up on regular expressions to decipher the strings I used
    if (s.matches(".*[A-Z].*")) // has upper case letter
      charSet++;
    if (s.matches(".*[a-z].*")) // has lower case letter
      charSet++;
    if (s.matches(".*\\d.*")) // has a number digit
      charSet++;
    if (s.matches(".*[`~!@#$%^&*()_+\\;\\',./{}|:\\\"<>?].*")) // has a special character
      charSet++;

    return (float) (charSet * 1.75 + s.length());
  }

  /**
   * Determines if the argument object is equal to the current instance of a Password object.
   *
   * @param obj, the object to check to see if it is equal
   * @return true if obj is a Password, occurrences, strength ratings, passwords, and
   * hashedPasswords are equal false otherwise
   * @author Michelle
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Password) {
      Password p = (Password) obj;
      boolean occurancesMatch = (this.occurrences == p.occurrences);
      boolean ratingsMatch = (this.strengthRating == p.strengthRating);
      boolean passwordsMatch = (this.password.equals(p.password));
      boolean hashesMatch = (this.hashedPassword.equals(p.hashedPassword));

      return occurancesMatch && ratingsMatch && passwordsMatch && hashesMatch;
    }
    return false;
  }

  /**
   * Returns a string representation of a password object.
   *
   * @return The string representation in the form "PASSWORD (HASHED_PASSWORD): OCURRENCES
   * [STRENGTH_RATING]"
   * @author Michelle
   */
  @Override
  public String toString() {
    return this.password + "(" + this.hashedPassword + "): " + this.occurrences + " [" + this.strengthRating + "]";
  }

  /**
   * Determines if the other password is less than, greater than, or equal to this password. The
   * comparison will be made based on the attribute given.
   *
   * @param other the password object to compare to this one
   * @param a     the data field to use when making the comparison.
   * @return <0 if this is less than other, >0 if this is greater than other, and 0 if they are
   * equal
   * @author Chengtao Dai
   */
  public int compareTo(Password other, Attribute a) {
    if (a == Attribute.OCCURRENCE)
      return this.occurrences - other.occurrences;
    if (a == Attribute.STRENGTH_RATING)
      return Float.compare(this.strengthRating, other.strengthRating);
    if (a == Attribute.HASHED_PASSWORD) {
      this.computeAndSetHashedPassword();
      other.computeAndSetHashedPassword();
      return this.hashedPassword.charAt(0) - other.hashedPassword.charAt(0);
    }
    return 0;
    // TODO
  }
}

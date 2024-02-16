//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05: User
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

/**
 * This class is used to model the users of the twitter simulation.
 */
public class User {
  private boolean isVerified; // The verified status of this User (whether they have a blue
  // checkmark or not)
  private String username; // The username this User tweets under

  /**
   * Constructs a new User object with a given username. All Users are unverified by default.
   *
   * @param username - the username of this User.
   * @throws IllegalArgumentException - if the given username contains an asterisk ("*") character,
   *                                  or is null, or is blank
   */
  public User(String username) throws IllegalArgumentException{
    if (username.contains("*"))
      throw new IllegalArgumentException();
    if (username == null)
      throw new IllegalArgumentException();
    if (username.isBlank())
      throw new IllegalArgumentException();
    if (username.trim().isEmpty())
      throw new IllegalArgumentException();

    this.username = username;
    this.isVerified = false;
  }

  /**
   * Accesses the username of this User
   *
   * @return the username this User tweets under
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Determines whether the User is a verified user or not
   *
   * @return true if this User is verified
   */
  public boolean isVerified() {
    return this.isVerified;
  }

  /**
   * Gives this User an important-looking asterisk.
   */
  public void verify() {
    // add an asteroid(*) at the end of username
    this.isVerified = true;
  }

  /**
   * Takes this User an important-looking asterisk away.
   */
  public void revokeVerification() {
    // remove an asteroid(*) from the end of username
    this.isVerified = false;
  }

  /**
   * Creates a String representation of this User for display. For example, if this User's username
   * is "uwmadison" and they are verified, this method will return "@uwmadison*"; if this User's
   * username is "dril" and they are not verified, this method will return "@dril" (with no
   * asterisk).
   *
   * @return a String representation of this User as described above.
   */
  @Override
  public String toString() {
    if (isVerified())
      return "@" + getUsername() + "*";
    return "@" + getUsername();
  }
}

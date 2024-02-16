//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P04: Changemaker
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

import java.util.Arrays;

/**
 * This class simulates a cash register dispensing change to a customer, where the goal is to find a
 * combination of coins of different denominations that add up to a given amount, using the minimal
 * total amount of coins, given we have a limited supply of each denomination.
 *
 * @author Chengtao Dai
 */
public class Changemaker {

  /**
   * The method determines number of possible ways to make change for a given value with a limited
   * number of coins of varying denominations. The method should be implemented recursively by
   * either directly calling itself (possibly more than once), or by calling a private static helper
   * method which is recursive. NOTE: All the detailed assumptions of the parameters will be
   * described here. Parameters in the other two methods also follow the assumptions described
   * here.
   *
   * @param denominations  - a list of various denominations or values of coins. Assume that each
   *                       denomination will have a strictly positive integer value (≥ 1), that no
   *                       two denominations are identical, and that the list is not necessarily
   *                       sorted in any order.
   * @param coinsRemaining - represents how many coins of each corresponding denomination in the
   *                       denominations array are remaining in our cash register. Assume that there
   *                       is a non-negative integer quantity of each denomination of coin (≥ 0),
   *                       and that coinsRemaining is the same length as denominations.
   * @param value          - the total value that we wish to make change for. If it is negative then
   *                       there is no possible way to make change for such a value, and that if it
   *                       is zero then there is precisely one way to make change by returning no
   *                       coins.
   * @return the number of possible ways to make change for a given value
   */
  public static int count(int[] denominations, int[] coinsRemaining, int value) {
    // base case
    if (value < 0) {
      return 0;
    }
    if (value == 0) {
      return 1;
    }

    int toReturn = 0;
    // recursive step
    // check if there is any coin remaining in the machine, if there do exist, then pick it out
    // and the recursive step...
    for (int i = 0; i < denominations.length; i++) {
      if (coinsRemaining[i] > 0) {
        int[] newCoinsRemaining = useCoin(coinsRemaining, i);
        toReturn += count(denominations, newCoinsRemaining, value - denominations[i]); // minus the
        // denomination in the corresponding place that is parellel to the coin we pick in
        // coinsRemaining
      }
    }
    return toReturn;
  }

  /**
   * This method uses a coin that exists in the coinsRemaining array.
   *
   * @param coinsRemaining - represents how many coins of each corresponding denomination in the
   *                       denominations array are remaining in our cash register.
   * @param coinToUse      - represents the coin we are going to use.
   * @return the updated version of the array coinsRemaining after we use a coin.
   */
  private static int[] useCoin(int[] coinsRemaining, int coinToUse) {
    int[] toReturn = Arrays.copyOf(coinsRemaining, coinsRemaining.length);
    toReturn[coinToUse]--;
    return toReturn;
  }

  /**
   * This method determines the optimal way to make change. This one must also be implemented
   * recursively.
   *
   * @param denominations  - a list of various denominations or values of coins.
   * @param coinsRemaining - represents how many coins of each corresponding denomination in the
   *                       denominations array are remaining in our cash register.
   * @param value          - the total value that we wish to make change for.
   * @return the minimum total number of coins needed to make change for the given value using a
   * limited number of coins of various denominations. If there is no way to make change using the
   * given coins your method should instead return -1.
   */
  public static int minCoins(int[] denominations, int[] coinsRemaining, int value) {

    // base case
    if (value < 0) {
      return -1;
    }
    if (value == 0) {
      return 0;
    }

    // recursive step
    // intuition:         0   1   2   3   4   5   6   7   8   9          use denominations[1,2,5]
    //    origin        [12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
    //    minimal       [ 0,  1,  1, 1+1,1+1, 1, 1+1,1+1,1+2,1+2]
    int minCount = Integer.MAX_VALUE;
    for (int i = 0; i < denominations.length; i++) {
      if (coinsRemaining[i] > 0 && value >= denominations[i]) {
        int[] newCoinsRemaining = useCoin(coinsRemaining, i);
        int count = minCoins(denominations, newCoinsRemaining, value - denominations[i]);
        if (count != -1 && count + 1 < minCount) { //IMPORTANT!!!! HERE you need to add "count !=
          // -1" to make sure that -1 (in this case value < 0) is not a valid count!!!!!!
          minCount = count + 1;
        }
      }
    }
    if (minCount == Integer.MAX_VALUE) {
      return -1;
    }
    return minCount;
  }

  /**
   * This method computes an array representing the exact number of each type of coin needed to make
   * change optimally so that coins can be dispensed to the customer.
   *
   * @param denominations  - a list of various denominations or values of coins.
   * @param coinsRemaining - represents how many coins of each corresponding denomination in the
   *                       denominations array are remaining in our cash register.
   * @param value          - the total value that we wish to make change for.
   * @return the optimal number of coins needed to make change
   */
  public static int[] makeChange(int[] denominations, int[] coinsRemaining, int value) {
    // We do not have any assumptions for this parameter besides that it is an integer value, but
    // note that if it is negative then there is no possible way to make change for such a value,
    // and that if it is zero then there is precisely one way to make change by returning no coins.

    //base case
    if (value < 0) {
      return null;
    }
    if (value == 0) {
      return new int[denominations.length];
    }


    //recursive step
    int[] toReturn = null;
    for (int i = 0; i < denominations.length; i++) {
      if (coinsRemaining[i] > 0 && value >= denominations[i]) {
        int[] newCoinsRemaining = useCoin(coinsRemaining, i);
        int[] coinsUsed = makeChange(denominations, newCoinsRemaining, value - denominations[i]);
        if (coinsUsed != null) {
          coinsUsed[i]++;
          if (toReturn == null || sum(coinsUsed) < sum(toReturn)) { // the total amounts of coins
            // used now is less than toReturn
            toReturn = coinsUsed;
          }
        }
      }
    }
    return toReturn;
  }

  /**
   * Adds all the coins used up and returns the sum of all coins in the given array.
   *
   * @param coins - array representing combinations of coins.
   * @return the sum of all coins in the given array.
   */
  private static int sum(int[] coins) {
    int sum = 0;
    for (int i = 0; i < coins.length; i++) {
      sum += coins[i];
    }
    return sum;
  }

}

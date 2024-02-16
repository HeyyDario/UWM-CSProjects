//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P04: ChangemakerTester
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
 * Contains methods for testing the Changemaker class.
 *
 * @author Chengtao Dai
 */
public class ChangemakerTester {
  /**
   * This method is provided for you to test the base case of count() method.
   *
   * This method tests three scenarios:
   *
   * 1. count() returns 0 when value is negative.
   *
   * 2. count() returns 0 when value is positive but there is no way to make change.
   *
   * 3. count() returns 1 when value = 0.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testCountBase() {

    int[] denominations = new int[] {1, 2, 5};
    int[] coinsRemaining = new int[] {1, 1, 1};
    // scenario 1: count() returns 0 when value is negative.
    {
      int value = -21;
      int actual = Changemaker.count(denominations, coinsRemaining, value);
      int expected = 0;
      if (actual != expected)
        return false;
    }

    // scenario 2: count() returns 0 when value is positive but there is no way to make change.
    // You can create such a scenario by choosing the sum total of all the coins in the register
    // to be smaller than value.
    {
      int value = 9; // 9 = 1*1 + 2*1 + 5*1 + 1
      int actual = Changemaker.count(denominations, coinsRemaining, value);
      int expected = 0;
      if (actual != expected)
        return false;
    }

    // scenario 3: count() returns 1 when value = 0.
    {
      int value = 0;
      int actual = Changemaker.count(denominations, coinsRemaining, value);
      int expected = 1;
      if (actual != expected)
        return false;
    }
    return true;
  }

  /**
   * This method is provided for you to test the recursive step of count() method.
   *
   * This method tests three scenarios:
   *
   * 1. count() returns the correct result in a scenario in which at least three different coins can
   * be used to make change.
   *
   * 2. count() returns the correct result in a scenario in which there are at least two different
   * optimal ways to make change using the same number of coins.
   *
   * 3.count() returns the correct result in a scenario in which always greedily choosing the
   * largest coin first does not produce a result with the minimum number of coins used.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testCountRecursive() {

    // scenario 1: count() returns the correct result in a scenario in which at least three
    // different coins can be used to make change.
    {
      int[] denominations = new int[] {1, 5, 10, 25};
      int[] coinsRemaining = new int[] {1, 1, 1, 1};
      int value = 36;
      int actual = Changemaker.count(denominations, coinsRemaining, value);
      int expected = 6;
      if (actual != expected)
        return false;
    }
    // scenario 2: count() returns the correct result in a scenario in which there are at least
    // two different optimal ways to make change using the same number of coins.
    {
      int[] denominations = new int[] {2, 5, 7, 10};
      int[] coinsRemaining = new int[] {1, 1, 1, 1};
      int value = 12;
      int actual = Changemaker.count(denominations, coinsRemaining, value);
      int expected = 4;
      if (actual != expected)
        return false;
    }
    // scenario 3: count() returns the correct result in a scenario in which always greedily
    // choosing the largest coin first does not produce a result with the minimum number of coins
    // used.
    {
      int[] denominations = new int[] {1, 5, 6, 9};
      int[] coinsRemaining = new int[] {2, 1, 1, 1};
      int value = 11;
      int actual = Changemaker.count(denominations, coinsRemaining, value);
      int expected = 5;
      if (actual != expected)
        return false;
    }

    return true;
  }

  /**
   * This method is provided for you to test the base case of minCoins() method.
   *
   * This method tests three scenarios:
   *
   * 1. minCoins() returns -1 when value is negative.
   *
   * 2. minCoins() returns -1 when value is positive but there is no way to make change.
   *
   * 3. minCoins() returns 0 when value = 0.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testMinCoinsBase() {
    int[] denominations = new int[] {1, 2, 5};
    int[] coinsRemaining = new int[] {1, 1, 1};
    // scenario 1: minCoins() returns -1 when value is negative.
    {
      int value = -21;
      int actual = Changemaker.minCoins(denominations, coinsRemaining, value);
      int expected = -1;
      if (actual != expected)
        return false;
    }

    // scenario 2: minCoins() returns -1 when value is positive but there is no way to make change.
    // You can create such a scenario by choosing the sum total of all the coins in the register
    // to be smaller than value.
    {
      int value = 9; // 81 = 1*10 + 2*10 + 5*10 + 1
      int actual = Changemaker.minCoins(denominations, coinsRemaining, value); //2
      int expected = -1;
      if (actual != expected)
        return false;
    }

    // scenario 3: minCoins() returns 0 when value = 0.
    {
      int value = 0;
      int actual = Changemaker.minCoins(denominations, coinsRemaining, value);
      int expected = 0;
      if (actual != expected)
        return false;
    }
    return true;
  }

  /**
   * This method is provided for you to test the recursive step of minCoins() method.
   *
   * This method tests three scenarios:
   *
   * 1. minCoins() returns the correct result in a scenario in which at least three different coins
   * can be used to make change.
   *
   * 2. minCoins() returns the correct result in a scenario in which there are at least two
   * different optimal ways to make change using the same number of coins.
   *
   * 3. minCoins() returns the correct result in a scenario in which always greedily choosing the
   * largest coin first does not produce a result with the minimum number of coins used.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testMinCoinsRecursive() {

    // scenario 1: minCoins() returns the correct result in a scenario in which at least three
    // different coins can be used to make change.
    {
      int[] denominations = new int[] {1, 5, 10, 25};
      int[] coinsRemaining = new int[] {1, 1, 1, 1};
      int value = 36;
      int actual = Changemaker.minCoins(denominations, coinsRemaining, value);
      int expected = 3;
      if (actual != expected)
        return false;
    }
    // scenario 2: minCoins() returns the correct result in a scenario in which there are at least
    // two different optimal ways to make change using the same number of coins.
    {
      int[] denominations = new int[] {2, 5, 7, 10};
      int[] coinsRemaining = new int[] {1, 1, 1, 1};
      int value = 12;
      int actual = Changemaker.minCoins(denominations, coinsRemaining, value);
      int expected = 2;
      if (actual != expected)
        return false;
    }
    // scenario 3: minCoins() returns the correct result in a scenario in which always greedily
    // choosing the largest coin first does not produce a result with the minimum number of coins
    // used.
    {
      int[] denominations = new int[] {1, 5, 6, 9};
      int[] coinsRemaining = new int[] {2, 1, 1, 1};
      int value = 11;
      int actual = Changemaker.minCoins(denominations, coinsRemaining, value);
      int expected = 2;
      if (actual != expected)
        return false;
    }

    return true;
  }

  /**
   * This method is provided for you to test the base case of makeChange() method.
   *
   * This method tests three scenarios:
   *
   * 1. makeChange() returns null when value is negative.
   *
   * 2. makeChange() returns null when value is positive but there is no way to make change.
   *
   * 3. makeChange() returns an array of all 0 when value = 0.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testMakeChangeBase() {
    int[] denominations = new int[] {1, 2, 5};
    int[] coinsRemaining = new int[] {1, 1, 1};
    // scenario 1: makeChange() returns null when value is negative.
    {
      int value = -21;
      int[] actual = Changemaker.makeChange(denominations, coinsRemaining, value);
      int[] expected = null;
      if (!Arrays.equals(actual, expected))
        return false;
    }

    // scenario 2: makeChange() returns null when value is positive but there is no way to make
    // change.
    // You can create such a scenario by choosing the sum total of all the coins in the register
    // to be smaller than value.
    {
      int value = 9; // 9 = 1*1 + 2*1 + 5*1 + 1
      int[] actual = Changemaker.makeChange(denominations, coinsRemaining, value);
      int[] expected = null;
      if (!Arrays.equals(actual, expected))
        return false;
    }

    // scenario 3: makeChange() returns an array of all 0 when value = 0.
    {
      int value = 0;
      int[] actual = Changemaker.makeChange(denominations, coinsRemaining, value);
      int[] expected = new int[denominations.length];
      if (!Arrays.equals(actual, expected))
        return false;
    }
    return true;
  }

  /**
   * This method is provided for you to test the recursive step of makeChange() method.
   *
   * This method tests three scenarios:
   *
   * 1. makeChange() returns the correct result in a scenario in which at least three different
   * coins can be used to make change.
   *
   * 2. makeChange() returns the correct result in a scenario in which there are at least two
   * different ways to make change using the same optimal number of coins.
   *
   * 3. makeChange() returns the correct result in a scenario in which always greedily choosing the
   * largest coin first does not produce a result with the minimum number of coins used.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testMakeChangeRecursive() {

    // scenario 1: makeChange() returns the correct result in a scenario in which at least three
    // different coins can be used to make change.
    {
      int[] denominations = new int[] {1, 5, 10, 21};
      int[] coinsRemaining = new int[] {1, 1, 1, 1};
      int value = 36;
      int[] actual = Changemaker.makeChange(denominations, coinsRemaining, value);
      int[] expected = {0, 1, 1, 1};
      if (!Arrays.equals(expected, actual))
        return false;
    }

    // scenario 2: makeChange() returns the correct result in a scenario in which there are at least
    // two different ways to make change using the same optimal number of coins.
    {
      int[] denominations = new int[] {2, 5, 7, 10};
      int[] coinsRemaining = new int[] {1, 1, 1, 1};
      int value = 12;
      int[] actual = Changemaker.makeChange(denominations, coinsRemaining, value);
      int[][] expected = {{1, 0, 0, 1}, {0, 1, 1, 0}};

      boolean isTheSame = false;
      for (int[] coins : expected) {
        if (Arrays.equals(coins, actual)) {
          isTheSame = true;
        }
      }
      if (!isTheSame)
        return false;
    }

    // scenario 3: makeChange() returns the correct result in a scenario in which always greedily
    // choosing the largest coin first does not produce a result with the minimum number of coins
    // used.
    {
      int[] denominations = new int[] {1, 5, 6, 9};
      int[] coinsRemaining = new int[] {2, 1, 1, 1};
      int value = 11;
      int[] actual = Changemaker.makeChange(denominations, coinsRemaining, value);
      int[] expected = {0, 1, 1, 0};
      if (!Arrays.equals(actual, expected))
        return false;
    }

    return true;
  }

  /**
   * Runs each of the tester methods and displays their result.
   *
   * @param args - unused
   */
  public static void main(String[] args) {

    System.out.println("Test for count() base is: " + testCountBase());
    System.out.println("Test for count() recursive is: " + testCountRecursive());
    System.out.println("Test for minCoins() base is: " + testMinCoinsBase());
    System.out.println("Test for minCoins() recursive is: " + testMinCoinsRecursive());
    System.out.println("Test for makeChange() base is: " + testMakeChangeBase());
    System.out.println("Test for makeChange() recursive is: " + testMakeChangeRecursive());

  }
}

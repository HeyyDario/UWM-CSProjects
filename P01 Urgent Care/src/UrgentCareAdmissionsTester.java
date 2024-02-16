//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P01: UrgentCareAdmissionsTester
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
import java.util.Arrays; // consider using Arrays.deepEquals() to test the contents of a 2D array

/**
 * Contains methods for testing the UrgentCareAdmissions class.
 *
 * @author Chengtao Dai & hobbes
 */
public class UrgentCareAdmissionsTester {

  /**
   * This test method is provided for you in its entirety, to give you a model for the rest of this
   * class. This method tests the getAdmissionIndex() method on a non-empty, non-full array of
   * patient records which we create and maintain here.
   *
   * This method tests three scenarios:
   *
   * 1. Adding a patient with a HIGHER triage priority than any currently present in the array. To
   * do this, we create an array with no RED priority patients and get the index to add a RED
   * priority patient. We expect this to be 0, so if we get any other value, the test fails.
   *
   * 2. Adding a patient with a LOWER triage priority than any currently present in the array. To do
   * this, we create an array with no GREEN priority patients and get the index to add a GREEN
   * priority patient. We expect this to be the current size of the oversize array, so if we get any
   * other value, the test fails.
   *
   * 3. Adding a patient with the SAME triage priority as existing patients. New patients at the
   * same priority should be added AFTER any existing patients. We test this for all three triage
   * levels on an array containing patients at all three levels.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   * @author hobbes
   */
  public static boolean testGetIndex() {

    // The non-empty, non-full oversize arrays to use in this test.
    // Note that we're using the UrgentCareAdmissions named constants to create these test records,
    // rather than their corresponding literal int values. 
    // This way if the numbers were to change in UrgentCareAdmissions, our test will still be valid.
    int[][] patientsListAllLevels =
        new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {21801, 2, UrgentCareAdmissions.YELLOW},
            {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
            {31501, 1, UrgentCareAdmissions.GREEN}, null, null, null};
    int allLevelsSize = 5;

    int[][] patientsListOnlyYellow = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
        {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}, null,
        null, null, null, null};
    int onlyYellowSize = 3;

    // scenario 1: add a patient with a higher priority than any existing patient
    {
      int expected = 0;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListOnlyYellow,
              onlyYellowSize);
      if (expected != actual)
        return false;
    }

    // scenario 2: add a patient with a lower priority than any existing patient
    {
      int expected = onlyYellowSize;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListOnlyYellow,
              onlyYellowSize);
      if (expected != actual)
        return false;
    }

    // scenario 3: verify that a patient with the same priority as existing patients gets
    // added after all of those patients
    {
      int expected = 1;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListAllLevels,
              allLevelsSize);
      if (expected != actual)
        return false;

      expected = 4;
      actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.YELLOW, patientsListAllLevels,
              allLevelsSize);
      if (expected != actual)
        return false;

      expected = allLevelsSize;
      actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListAllLevels,
              allLevelsSize);
      if (expected != actual)
        return false;
    }

    // and finally, verify that the arrays were not changed at all
    {
      int[][] allLevelsCopy = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      if (!Arrays.deepEquals(patientsListAllLevels, allLevelsCopy))
        return false;

      int[][] onlyYellowCopy = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
          {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}, null,
          null, null, null, null};
      if (!Arrays.deepEquals(patientsListOnlyYellow, onlyYellowCopy))
        return false;
    }

    return true;
  }

  /**
   * This method tests the addPatient() method on a non-empty, non-full array of patient records
   * which we create and maintain here. Each test should verify that the returned size is as
   * expected and that the array has been updated (or not) appropriately.
   *
   * This method tests four scenarios:
   *
   * 1. Adding a patient to the end of an array. To do this, we create an array with one RED
   * priority patient, three YELLOW priority patients and one GREEN priority patient and get the
   * index to add a GREEN priority patient. We expect size to be 6 and the patient to be added to
   * the sixth element in the array (i.e. index is 5). So if we get any value of size other than 6
   * and the patient list is not updated as expected, the test fails.
   *
   * 2. Adding a patient to the end of an array. To do this, we create an array with one RED
   * priority patient, three YELLOW priority patients and one GREEN priority patient and get the
   * index to add a RED priority patient. We expect size to be 6 and the patient to be added to the
   * second element in the array (i.e. index is 1). So if we get any value of size other than 6 and
   * the patient list is not updated as expected, the test fails.
   *
   * 3. Adding a patient using an invalid (out-of-bounds) index. To do this, we create an array only
   * with three YELLOW priority patients and get the index to add a RED priority patient. We expect
   * size to be 3 and the patient list holds. So if we get any value of size other than 3 and the
   * patient list doesn't hold as expected, the test fails.
   *
   * 4. Adding a patient to the front of an array. To do this, we create an array only with three
   * YELLOW priority patients and get the index to add a RED priority patient. We expect size to be
   * 4 and the patient to be added to the first element in the array (i.e. index is 0). So if we get
   * any value of size other than 4 and the patient list is not updated as expected, the test
   * fails.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testAddPatient() {
    // (1) add a patient to the END of the patientsList
    {
      int[][] patientsListAllLevels = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int allLevelsSize = 5;
      int[][] expectedArr = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN},
          {20710, 6, UrgentCareAdmissions.GREEN}, null, null};
      int expected = allLevelsSize + 1;
      int[] record = new int[] {20710, 6, UrgentCareAdmissions.GREEN};
      int index =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListAllLevels,
              allLevelsSize);
      int actual =
          UrgentCareAdmissions.addPatient(record, index, patientsListAllLevels, allLevelsSize);
      if (expected != actual || !Arrays.deepEquals(expectedArr, patientsListAllLevels))
        return false;
    }

    // (2) add a patient to the MIDDLE of the patientsList
    {
      int[][] patientsListAllLevels = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int allLevelsSize = 5;

      int[][] expectedArr =
          new int[][] {{32702, 3, UrgentCareAdmissions.RED}, {20710, 6, UrgentCareAdmissions.RED},
              {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
              {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
              null};
      int expected = allLevelsSize + 1;
      int[] record = new int[] {20710, 6, UrgentCareAdmissions.RED};
      int index =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListAllLevels,
              allLevelsSize);
      int actual =
          UrgentCareAdmissions.addPatient(record, index, patientsListAllLevels, allLevelsSize);
      if (expected != actual || !Arrays.deepEquals(expectedArr, patientsListAllLevels))
        return false;
    }

    // (3) add a patient using an invalid (out-of-bounds) index
    {
      int[][] patientsListOnlyYellow = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
          {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}};
      int onlyYellowSize = 3;

      int[][] expectedArr = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
          {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}};
      int expected = onlyYellowSize;
      int[] record = new int[] {20710, 4, UrgentCareAdmissions.RED};
      int index =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListOnlyYellow,
              onlyYellowSize);
      int actual =
          UrgentCareAdmissions.addPatient(record, index, patientsListOnlyYellow, onlyYellowSize);
      if (expected != actual || !Arrays.deepEquals(expectedArr, patientsListOnlyYellow))
        return false;
    }

    // (4) add a patient to the front of the patientsList
    {
      int[][] patientsListOnlyYellow = new int[][] {{21801, 1, UrgentCareAdmissions.YELLOW},
          {22002, 2, UrgentCareAdmissions.YELLOW}, {11901, 3, UrgentCareAdmissions.YELLOW}, null};
      int onlyYellowSize = 3;

      int[][] expectedArr = new int[][] {{32703, 4, UrgentCareAdmissions.RED},
          {21801, 1, UrgentCareAdmissions.YELLOW}, {22002, 2, UrgentCareAdmissions.YELLOW},
          {11901, 3, UrgentCareAdmissions.YELLOW}};
      int expected = onlyYellowSize + 1;
      int[] record = new int[] {32703, 4, UrgentCareAdmissions.RED};
      int index =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsListOnlyYellow,
              onlyYellowSize);
      int actual =
          UrgentCareAdmissions.addPatient(record, index, patientsListOnlyYellow, onlyYellowSize);
      if (expected != actual || !Arrays.deepEquals(expectedArr, patientsListOnlyYellow))
        return false;
    }

    return true;
  }

  /**
   * This method tests the removeNextPatient() method on a non-empty, non-full array of patient
   * records which we create and maintain here. Each test should verify that the returned size is as
   * expected and that the array has been updated (or not) appropriately.
   *
   * This method tests two scenarios:
   *
   * 1. Removing a patient from a patientsList containing more than one record. To do this, we
   * create an array with one RED priority patient, three YELLOW priority patients and one GREEN
   * priority patient and remove the first element from the array. We expect size to be 4 and the
   * first patient is removed from the array. So if we get any value of size other than 4 and the
   * patient list is not updated as expected, the test fails.
   *
   * 2. Removing a patient from a patientsList containing only one record. To do this, we create an
   * array with only one RED priority patient and remove it from the array. We expect size to be 0
   * and the only existing patient is removed from the array. So if we get any value of size other
   * than 0 and the patient list is not updated as expected, the test fails.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testRemovePatient() {
    // (1) remove a patient from a patientsList containing more than one record
    {
      int[][] patientsListAllLevels = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int allLevelsSize = 5;

      int[][] expectedArr = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
          {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW},
          {31501, 1, UrgentCareAdmissions.GREEN}, null, null, null, null};
      int expected = 4;
      int actual = UrgentCareAdmissions.removeNextPatient(patientsListAllLevels, allLevelsSize);
      if (actual != expected || !Arrays.deepEquals(expectedArr, patientsListAllLevels))
        return false;
    }

    // (2) remove a patient from a patientsList containing only one record
    {
      int[][] patientsList = new int[][] {{32702, 1, UrgentCareAdmissions.RED}};
      int size = 1;

      int[][] expectedArr = new int[][] {null};
      int expected = 0;
      int actual = UrgentCareAdmissions.removeNextPatient(patientsList, size);
      if (expected != actual || !Arrays.deepEquals(patientsList, expectedArr))
        return false;
    }

    return true;
  }

  /**
   * The method tests the behavior of the getPatientIndex method using a non-empty, non-full array.
   * Each test should verify that the returned size is as expected.
   *
   * This method tests three scenarios:
   *
   * 1. Looking for a patient at the end of the list. To do this, we create an array with one RED
   * priority patient, three YELLOW priority patients and one GREEN priority patient and get the
   * index of the last element in the array. We expect index to be 4. So if we get any value of size
   * other than 4, the test fails.
   *
   * 2. Looking for a patient in the middle of the list. To do this, we create an array with one RED
   * priority patient, three YELLOW priority patients and one GREEN priority patient and get the
   * index of a given element in the array. We expect index to be 2. So if we get any value of size
   * other than 2, the test fails.
   *
   * 3. Looking for a patient that doesn't exist in the list. To do this, we create an array with
   * one RED priority patient, three YELLOW priority patients and one GREEN priority patient and get
   * the index of an element that is not in the array. We expect index to be -1. So if we get any
   * value of size other than -1, the test fails.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testGetPatientIndex() {
    // (1) look for a patient at the end of the list
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int size = 5;
      int caseID = 31501;

      int expected = 4;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual)
        return false;
    }

    // (2) look for a patient in the middle of the list
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int size = 5;
      int caseID = 22002;
      int expected = 2;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual)
        return false;
    }

    // (3) look for a patient not present in the list
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int size = 5;
      int caseID = 22008;
      int expected = -1;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual)
        return false;
    }
    return true;
  }

  // Tests the getLongestWaitingPatientIndex method using a non-empty, non-full array. When
  // designing these tests, recall that arrivalOrder values will all be unique!

  /**
   * The method tests the getLongestWaitingPatientIndex method using a non-empty, non-full array.
   *
   * The method tests two scenarios:
   *
   * 1. Calling the method on a patientsList with only one patient. To do this, we create an array
   * with only one RED triage level patient. We expect the index to be 0. So if we get any value of
   * index other than 0, the test fails.
   *
   * 2. Calling the method on a patientsList with at least three patients. To do this, we create an
   * array with one RED priority patient, three YELLOW priority patients and one GREEN priority
   * patient. We expect the index to be 4. So if we get any value of index other than 4, the test
   * fails.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testLongestWaitingPatient() {
    // (1) call the method on a patientsList with only one patient
    {
      int[][] patientsList = new int[][] {{32702, 1, UrgentCareAdmissions.RED}};
      int size = 1;

      int expected = 0;
      int actual = UrgentCareAdmissions.getLongestWaitingPatientIndex(patientsList, size);
      if (expected != actual)
        return false;
    }

    // (2) call the method on a patientsList with at least three patients
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int size = 5;

      int expected = 4;
      int actual = UrgentCareAdmissions.getLongestWaitingPatientIndex(patientsList, size);
      if (expected != actual)
        return false;
    }
    return true;
  }

  // Tests the edge case behavior of the UrgentCareAdmissions methods using empty and full arrays

  /**
   * The method tests the edge case behavior of the UrgentCareAdmissions methods using empty and
   * full arrays.
   *
   * The method tests seven scenarios:
   *
   * 1. Testing getAdmissionIndex using an empty patientsList array and any triage level.
   *
   * 2. Testing getAdmissionIndex using a full patientsList array and any triage level.
   *
   * 3. Testing addPatient using a full patientsList array.
   *
   * 4. Testing addPatient using an empty patientsList array.
   *
   * 5. Testing removeNextPatient using an empty patientsList array.
   *
   * 6. Testing getPatientIndex using an empty patientsList array.
   *
   * 7. Testing getLongestWaitingPatientIndex using an empty patientsList array
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testEmptyAndFullArrays() {
    // (1) test getAdmissionIndex using an empty patientsList array and any triage level
    {
      int[][] patientsList = new int[][] {};
      int size = 0;

      int expected = -1;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsList, size);
      if (expected != actual)
        return false;
    }

    // (2) test getAdmissionIndex using a full patientsList array and any triage level
    {
      int[][] patientsList = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}};
      int size = 5;

      int expected = -1;
      int actual =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.RED, patientsList, size);
      if (expected != actual)
        return false;
    }

    // (3) test addPatient using a full patientsList array
    {
      int[][] patientsListAllLevels = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}};
      int allLevelsSize = 5;
      int[][] expectedArr = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}};
      int expected = allLevelsSize;
      int[] record = new int[] {20710, 6, UrgentCareAdmissions.GREEN};
      int index =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListAllLevels,
              allLevelsSize);
      int actual =
          UrgentCareAdmissions.addPatient(record, index, patientsListAllLevels, allLevelsSize);
      if (expected != actual || !Arrays.deepEquals(expectedArr, patientsListAllLevels))
        return false;
    }

    // (4) test addPatient using an empty patientsList array
    {
      int[][] patientsListAllLevels = new int[][] {null, null};
      int allLevelsSize = 0;
      int[][] expectedArr = new int[][] {{20710, 6, UrgentCareAdmissions.GREEN}, null};
      int expected = 1;
      int[] record = new int[] {20710, 6, UrgentCareAdmissions.GREEN};
      int index =
          UrgentCareAdmissions.getAdmissionIndex(UrgentCareAdmissions.GREEN, patientsListAllLevels,
              allLevelsSize);
      int actual =
          UrgentCareAdmissions.addPatient(record, index, patientsListAllLevels, allLevelsSize);
      if (expected != actual || !Arrays.deepEquals(expectedArr, patientsListAllLevels))
        return false;
    }

    // (5) test removeNextPatient using an empty patientsList array
    {
      int[][] patientsList = new int[][] {};
      int size = 0;

      int[][] expectedArr = new int[][] {};

      int expected = 0;
      int actual = UrgentCareAdmissions.removeNextPatient(patientsList, size);
      if (actual != expected || !Arrays.deepEquals(expectedArr, patientsList))
        return false;
    }

    // (6) test getPatientIndex using an empty patientsList array
    {
      int[][] patientsList = new int[][] {};
      int size = 0;
      int caseID = 31501;

      int expected = -1;
      int actual = UrgentCareAdmissions.getPatientIndex(caseID, patientsList, size);
      if (expected != actual)
        return false;
    }

    // (7) test getLongestWaitingPatientIndex using an empty patientsList array
    {
      int[][] patientsList = new int[][] {};
      int size = 0;

      int expected = -1;
      int actual = UrgentCareAdmissions.getLongestWaitingPatientIndex(patientsList, size);
      if (expected != actual)
        return false;
    }

    return true;
  }

  /**
   * The method tests the getSummary method using arrays in various states.
   *
   * The method tests three scenarios:
   *
   * 1. Testing getSummary using an empty patientsList. To do this, we create an empty array and
   * verifies if the output string is as expected. So if the output string is not as expected, the
   * test fails.
   *
   * 2. Testing getSummary using a patientsList with multiple patients at a single triage level. To
   * do this, we create an array with only three YELLOW triage levels and verifies if the output
   * string is as expected. So if the output string is not as expected, the test fails.
   *
   * 3. Testing getSummary using a patientsList with patients at all triage levels. To do this, we
   * create an array with one RED priority patient, three YELLOW priority patients and one GREEN
   * priority patient and verifies if the output string is as expected. So if the output string is
   * not as expected, the test fails.
   *
   * @return true if and only if all test scenarios pass, false otherwise
   */
  public static boolean testGetSummary() {
    // (1) test getSummary using an empty patientsList
    {
      int[][] patientsList = new int[][] {};
      int size = 0;

      String expected =
          "Total number of patients: 0" + "\n" + "RED: 0" + "\n" + "YELLOW: 0" + "\n" + "GREEN: " + "0" + "\n";
      String actual = UrgentCareAdmissions.getSummary(patientsList, size);
      if (!expected.equals(actual))
        return false;
    }

    // (2) test getSummary using a patientsList with multiple patients at a single triage level
    {
      int[][] patientsListOnlyYellow = new int[][] {{21801, 2, UrgentCareAdmissions.YELLOW},
          {22002, 4, UrgentCareAdmissions.YELLOW}, {11901, 5, UrgentCareAdmissions.YELLOW}, null,
          null, null, null, null};
      int onlyYellowSize = 3;

      String expected =
          "Total number of patients: 3" + "\n" + "RED: 0" + "\n" + "YELLOW: 3" + "\n" + "GREEN: " + "0" + "\n";
      String actual = UrgentCareAdmissions.getSummary(patientsListOnlyYellow, onlyYellowSize);

      if (!expected.equals(actual))
        return false;
    }

    // (3) test getSummary using a patientsList with patients at all triage levels
    {
      int[][] patientsListAllLevels = new int[][] {{32702, 3, UrgentCareAdmissions.RED},
          {21801, 2, UrgentCareAdmissions.YELLOW}, {22002, 4, UrgentCareAdmissions.YELLOW},
          {11901, 5, UrgentCareAdmissions.YELLOW}, {31501, 1, UrgentCareAdmissions.GREEN}, null,
          null, null};
      int allLevelsSize = 5;
      String expected =
          "Total number of patients: 5" + "\n" + "RED: 1" + "\n" + "YELLOW: 3" + "\n" + "GREEN: " + "1" + "\n";
      String actual = UrgentCareAdmissions.getSummary(patientsListAllLevels, allLevelsSize);
      if (!expected.equals(actual))
        return false;

    }

    return true;
  }

  /**
   * Runs each of the tester methods and displays their result
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("get index: " + testGetIndex());
    System.out.println("add patient: " + testAddPatient());
    System.out.println("remove patient: " + testRemovePatient());
    System.out.println("get patient: " + testGetPatientIndex());
    System.out.println("longest wait: " + testLongestWaitingPatient());
    System.out.println("empty/full: " + testEmptyAndFullArrays());
    System.out.println("get summary: " + testGetSummary());
  }

}

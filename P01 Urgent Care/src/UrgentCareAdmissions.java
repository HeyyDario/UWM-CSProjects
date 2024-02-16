//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P01: UrgentCareAdmissions
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
 * Contains methods for automating a clinic's case management protocols.
 *
 * @author Chengtao Dai
 */
public class UrgentCareAdmissions {

  public static final int RED = 0;
  public static final int YELLOW = 1;
  public static final int GREEN = 2;

  /**
   * A helper method to find the correct index to insert a new patient at the given triage level.
   * This should be the index AFTER the last index currently occupied by a patient at that level.
   * For example, if the list currently contains: [RED, RED, GREEN, GREEN, GREEN, GREEN, null, null,
   * null, null] with a size of 6, a RED patient would be inserted at index 2, a YELLOW patient
   * would also be inserted at index 2, and a GREEN patient would be inserted at index 6. If the
   * list is FULL, the method should return -1 regardless of the triage value. This method must not
   * modify patientsList in any way.
   *
   * @param triage       the urgency level of the next patient's issue, RED YELLOW or GREEN
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the index at which the patient should be inserted into the list, or -1 if the list is
   * full
   */
  public static int getAdmissionIndex(int triage, int[][] patientsList, int size) {
    // if the list is full, return -1
    if (patientsList.length <= size) {
      return -1;
    }


    // compare the triage level one by one to determine the place where the patient information
    // should be inserted;
    int returnIndex = -1;
    for (int i = 0; i < size; i++) {
      if (triage < patientsList[i][2]) {
        returnIndex = i;
        break;
      }
    }
    // if the returnIndex is -1, it means that returnIndex is larger than any element in the
    // array, so it should be inserted at the end of the array.
    if (returnIndex == -1) {
      return size;
    }
    return returnIndex;
  }

  /**
   * Adds the patient record, a three-element perfect size array of ints, to the patients list in
   * the given position. This method must maintain the ordering of the rest of the array, so any
   * patients in higher index positions must be shifted out of the way. If there is no room to add a
   * new patient to the array or the index provided is not a valid index into the oversize array
   * (for adding, valid indexes are 0 through size), the method should not modify the patientsList
   * array in any way.
   *
   * @param patientRecord a three-element perfect size array of ints, containing the patient's
   *                      5-digit case ID, their admission order number, and their triage level
   * @param index         the index at which the patientRecord should be added to patientsList,
   *                      assumed to correctly follow the requirements of getAdmissionIndex()
   * @param patientsList  the current, active list of patient records
   * @param size          the number of patients currently in the list
   * @return the number of patients in patientsList after this method has finished running
   */

  public static int addPatient(int[] patientRecord, int index, int[][] patientsList, int size) {
    // if no room for a new patient (i.e. the array is full)
    if (patientsList.length <= size) {
      return size;
    }

    if (index == 0) {
      int[] temp = patientsList[index];
      patientsList[index] = patientRecord;
      // in case the arraycopy process crash
      if (patientsList.length > 2) {
        System.arraycopy(patientsList, index + 1, patientsList, index + 2, patientsList.length - 2);
      }
      patientsList[index + 1] = temp;
    } else {
      int[] temp = patientsList[index];
      System.arraycopy(patientsList, 0, patientsList, 0, index - 1);
      patientsList[index] = patientRecord;
      System.arraycopy(patientsList, index + 1, patientsList, index + 2, size - index);
      patientsList[index + 1] = temp;
    }

    return size + 1;
  }

  /**
   * Removes the patient record at index 0 of the patientsList, if there is one, and updates the
   * rest of the list to maintain the oversized array in its current ordering.
   *
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the number of patients in patientsList after this method has finished running
   */
  public static int removeNextPatient(int[][] patientsList, int size) {
    if (size == 0) {
      return 0;
    }

    System.arraycopy(patientsList, 1, patientsList, 0, size - 1);
    patientsList[size - 1] = null;

    return size - 1;
  }

  /**
   * Finds the index of a patient given their caseID number. This method must not modify
   * patientsList in any way.
   *
   * @param caseID       the five-digit case number assigned to the patient record to find
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the index of the patient record matching the given caseID number, or -1 if the list is
   * empty or the caseID is not found
   */
  public static int getPatientIndex(int caseID, int[][] patientsList, int size) {
    if (patientsList == null || patientsList.length == 0)
      return -1;

    // check one by one
    for (int i = 0; i < size; i++) {
      if (patientsList[i][0] == caseID) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Finds the patient who arrived earliest still currently present in the patientsList, and returns
   * the index of their patient record within the patientsList. The arrival value is strictly
   * increasing for each new patient, so you will not need to handle the case where two values are
   * equal.
   *
   * That is, for all patient records [ caseID, arrivalOrder, triage ], this method should find and
   * return the one with the minimum arrivalOrder value. This method must not modify patientsList in
   * any way.
   *
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return the index of the patient record with the smallest value in their arrival integer, or -1
   * if the list is empty
   */
  public static int getLongestWaitingPatientIndex(int[][] patientsList, int size) {
    if (patientsList.length == 0)
      return -1;

    int returnMinIndex = 0;
    for (int i = 1; i < size; i++) {
      if (patientsList[i][1] < patientsList[returnMinIndex][1]) {
        returnMinIndex = i;
      }
    }
    return returnMinIndex;
  }

  /**
   * Creates a formatted String summary of the current state of the patientsList array, as follows:
   * Total number of patients: 5 RED: 1 YELLOW: 3 GREEN: 1
   *
   * The first line displays the current size of the array. The next three lines display counts of
   * patients at each of the three triage levels currently in the patientsList. Any or all of these
   * numbers may be 0.
   *
   * This method must not modify the patientsList array in any way.
   *
   * @param patientsList the current, active list of patient records
   * @param size         the number of patients currently in the list
   * @return a String summarizing the patientsList as shown in this comment
   */
  public static String getSummary(int[][] patientsList, int size) {
    int countR = 0;
    int countY = 0;
    int countG = 0;
    String returnString = "Total number of patients: " + size + "\n";

    for (int i = 0; i < size; i++) {
      if (patientsList[i][2] == 0) {
        countR++;
      }
      if (patientsList[i][2] == 1) {
        countY++;
      }
      if (patientsList[i][2] == 2) {
        countG++;
      }
    }

    returnString += "RED: " + countR + "\n";
    returnString += "YELLOW: " + countY + "\n";
    returnString += "GREEN: " + countG + "\n";

    return returnString;
  }
}

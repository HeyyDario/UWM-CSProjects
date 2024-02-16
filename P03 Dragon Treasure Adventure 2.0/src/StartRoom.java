//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P03: DragonTreasureAdventure2.0 StartRoom
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
import processing.core.PImage;

public class StartRoom extends Room {

  /**
   * Constructor for a StartRoom object. Initializes all instance data fields.
   *
   * @param ID    the ID that this Room should have
   * @param image the image that should be used as a background when drawing this Room.
   */
  public StartRoom(int ID, PImage image) {
    super(ID, "You find yourself in the entrance to a cave holding treasure", image);
    //String intro = "You find yourself in the entrance to a cave holding treasure";
    //questions here
  }
}

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P03: DragonTreasureAdventure2.0 Room
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
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Room {
  private String description; //verbal description of the room
  private ArrayList<Room> adjRooms; //list of all rooms directly connect
  private final int ID; // a "unique" identifier for each room
  protected static PApplet processing; //PApplet object which the rooms will use to
  //draw stuff to the GUI
  private PImage image; //stores the image that corresponds to the background of a room

  /**
   * Constructor for a Room object. Initializes all instance data fields.
   *
   * @param ID          the ID that this Room should have
   * @param description the verbal description this Room should have
   * @param image       the image that should be used as a background when drawing this Room.
   */
  public Room(int ID, String description, processing.core.PImage image) {
    this.ID = ID;
    this.description = description;
    this.adjRooms = new ArrayList<>(); // don't forget to instantiate the arraylist
    this.image = image;
  }

  /**
   * Getter for ID.
   *
   * @return the ID of this Room
   */
  public int getID() {
    return this.ID;
  }

  /**
   * Getter for description.
   *
   * @return the verbal description of this Room
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Getter for the list of adjacentRooms.
   *
   * @return the list of adjacent rooms
   */
  public ArrayList<Room> getAdjacentRooms() {
    return adjRooms;
  }

  /**
   * Sets the processing for the class.
   *
   * @param processing the PApplet that this room will use to draw to the window
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Room.processing = processing;
  }

  /**
   * Adds the given room to the list of rooms adjacent to this room.
   *
   * @param toAdd the room to be added
   */
  public void addToAdjacentRooms(Room toAdd) {
    this.getAdjacentRooms().add(toAdd);
  }

  /**
   * Checks whether or not the given room is adjacent to this room.
   *
   * @param r the room to check for adjacency
   * @return true if it is adjacent, false otherwise
   */
  public boolean isAdjacent(Room r) {
    return this.adjRooms.contains(r);
  }

  /**
   * Overrides Object.equals(). Determines if two objects are equal.
   *
   * @param other the object to check against this Room
   * @return true if other is of type Room and has the same ID, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Room && ((Room) other).getID() == this.getID()) {
      return true;
    }
    return false;
  }

  /**
   * Overrides Object.toString(). Returns a string representation of a Room object.
   *
   * @return Returns a string in the form of "<ID>: <description>\n Adjacent Rooms: <r1's ID> <r2's
   * ID>" list of adjacent room IDs continues for all rooms adjacent to this Room.
   */
  @Override
  public String toString() {
    String toReturn = "" + this.getID() + ": " + this.getDescription() + "\n" + "Adjacent Rooms: ";
    for (Room room : adjRooms) {
      toReturn += room.getID() + " ";
    }
    return toReturn;
  }

  /**
   * Draws this Room to the window by drawing the background image, a rectangle, and some text.
   */
  public void draw() {

    // 4.0.1 Use the PApplet’s image() instance method to draw the image at (0, 0).
    processing.image(image, 0, 0);
    // 4.0.2 Use the PApplet’s fill() instance method to change the draw color to giving it a value
    // of −7028. This will change it to Flavescent a light yellow-brown color.
    processing.fill(-7028);
    // 4.0.3 Use the PApplet’s rect() instance method to draw a rectangle. The first two arguments
    // are the xy-coordinates of the top left corner respectively. The third and fourth arguments
    // are the xy-coordinates of the bottom right corner respectively. Place the upper left corner
    // at (0, 500) and the other at (800, 600).
    processing.rect(0, 500, 800, 600);
    // 4.0.4 Use the PApplet’s fill() instance method again to change the draw color to giving it a
    // value of 0. This will change it to black.
    processing.fill(0);
    // 4.0.5 Use the PApplet’s text() instance method To draw the Room’s toString() at(300,525).
    processing.text(toString(), 300, 525);
  }

}

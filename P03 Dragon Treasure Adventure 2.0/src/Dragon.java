//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P03: DragonTreasureAdventure2.0 Dragon
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
import java.util.Random;

public class Dragon extends Character implements Moveable {
  private Random randGen; //random num generator used for moving
  private static final String DRAGON_WARNING = "You hear a fire breathing nearby!\n";
  private static final String DRAGON_ENCOUNTER = "Oh no! You ran into the fire breathing dragon!\n";

  /**
   * Constructor for a Dragon object. Initializes all instance fields. The label should be "DRAGON"
   * by default.
   *
   * @param currentRoom the room that the Dragon starts in
   */
  public Dragon(Room currentRoom) {
    super(currentRoom, "DRAGON");
    randGen = new Random();
  }

  /**
   * Moves the Dragon to the destination room.
   *
   * @param destination the Room to change it to
   * @return true if the change was successful, false otherwise
   */
  @Override
  public boolean changeRoom(Room destination) {
    if (this.getCurrentRoom().isAdjacent(destination)) {
      this.setCurrentRoom(destination);
      return true;
    }
    return false;
  }

  /**
   * Checks if the dragon can move to the given destination. A valid move is the destination not a
   * PortalRoom.
   *
   * @param destination the room to check if the dragon can move towards
   * @return true if they can, false otherwise
   */
  @Override
  public boolean canMoveTo(Room destination) {
    return !(destination instanceof PortalRoom); //&& this.getAdjacentRooms().contains(destination)
  }

  /**
   * Picks randomly ONCE an adjacent room to move into.
   *
   * @return the room that this Dragon should try to move into
   */
  public Room pickRoom() {
    int randIndex = randGen.nextInt(this.getAdjacentRooms().size());
    return this.getAdjacentRooms().get(randIndex);
  }

  /**
   * Getter for DRAGON_WARNING.
   *
   * @return the string for warning about a dragon being nearby.
   */
  public static String getDragonWarning() {
    return DRAGON_WARNING;
  }

  /**
   * Getter for DRAGON_ENCOUNTER.
   *
   * @return the string for letting the player know they ran into the dragon.
   */
  public static String getDragonEncounter() {
    return DRAGON_ENCOUNTER;
  }
}

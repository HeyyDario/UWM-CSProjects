//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P03: DragonTreasureAdventure2.0 Player
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
public class Player extends Character implements Moveable {
  // 5.1 Player Class
  private boolean hasKey;

  /**
   * Constructor for player object. The label should be "PLAYER" and not have a key by default.
   *
   * @param currentRoom the room that the player should start in
   */
  public Player(Room currentRoom) {
    super(currentRoom, "PLAYER");
    hasKey = false; // Initializes all instance fields.
  }

  /**
   * Determines if the player has the key.
   *
   * @return true if the player has the key, false otherwise
   */
  public boolean hasKey() {
    return this.hasKey;
  }

  /**
   * Gives player the key.
   */
  public void obtainKey() {
    this.hasKey = true;
  }

  /**
   * Moves the Player to the destination room.
   *
   * @param destination the Room to change it to
   * @return true if the change was successful, false otherwise
   */
  @Override
  public boolean changeRoom(Room destination) {
    if (canMoveTo(destination)) {
      this.setCurrentRoom(destination);
      return true;
    }
    return false;
  }

  /**
   * Checks if the player can move to the given destination. A valid move is the destination is a
   * room adjacent to the player.
   *
   * @param destination the room to check if the player can move towards
   * @return true if they can, false otherwise
   */
  @Override
  public boolean canMoveTo(Room destination) {
    // The player can only move to adjacent rooms, which means if he tries to move into a room
    // that is not adjacent, changeRoom would return false;
    //return this.getCurrentRoom().isAdjacent(destination);
    return getAdjacentRooms().contains(destination);
  }

  /**
   * Checks if the player needs to teleport and move them if needed.
   *
   * @return true if a teleport occurred, false otherwise
   */
  public boolean teleport() {
    // first we need to check if the player needs to teleport

    if (getCurrentRoom() instanceof PortalRoom) {
      Room destination = ((PortalRoom) getCurrentRoom()).getTeleportLocation();
      boolean canTeleport = changeRoom(destination);

      if (canTeleport) {
        System.out.println(PortalRoom.getTeleportMessage());
        setCurrentRoom(destination);
        return true;
      }
    }
    return false;
  }

  /**
   * Determines whether or not the given dragon is nearby. A dragon is considered nearby if it is in
   * one of the adjacent rooms.
   *
   * @param d the dragon to check if nearby
   * @return true if the dragon is nearby, false otherwise
   */
  public boolean isDragonNearby(Dragon d) {
    return this.getAdjacentRooms().contains(d.getCurrentRoom());
  }

  /**
   * Determines whether or not a portal room is nearby. A portal room is considered nearby if it is
   * one of the adjacent rooms.
   *
   * @return true if a portal room is nearby, false otherwise
   */
  public boolean isPortalNearby() {
    for (Room room : this.getAdjacentRooms()) {
      if (room instanceof PortalRoom) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines whether or not the treasure room is nearby. The treasure room is considered nearby
   * if it is one of the adjacent rooms.
   *
   * @return true if the treasure room is nearby, false otherwise
   */
  public boolean isTreasureNearby() {
    for (Room room : this.getAdjacentRooms()) {
      if (room instanceof TreasureRoom) {
        return true;
      }
    }
    return false;
  }

}

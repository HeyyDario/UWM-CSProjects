//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P03: DragonTreasureAdventure2.0 DragonTreasureGame
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
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

import java.util.ArrayList;

public class DragonTreasureGame extends PApplet {
  private ArrayList<Room> roomList;
  // 4.4 add two private instance data fields of type File named roomInfo and mapInfo
  private File roomInfo;
  private File mapInfo;
  private ArrayList<Character> characters;
  // 6.1 add new data field, which is isDragonTurn and gameState
  private boolean isDragonTurn;
  private int gameState;

  // 3.6 Next, add and override the public void setup() method. Add the following code to the
  // beginning of the method:
  @Override
  public void setup() {
    this.getSurface().setTitle("Dragon Treasure Adventure"); // sets the title of the window
    this.imageMode(PApplet.CORNER); //Images are drawn using the x,y-coordinate
    //as the top-left corner
    this.rectMode(PApplet.CORNERS); //When drawing rectangles interprets args
    //as top-left corner and bottom-right corner respectively
    this.focused = true; // window will be active upon running program
    this.textAlign(CENTER); // sets the text alignment to center
    this.textSize(20); //sets the font size for the text

    Room.setProcessing(this); // setProcessing() takes a PApplet as its argument. ???FIXME

    this.isDragonTurn = false;
    this.gameState = 0;// 6.1 If the value is 0 the game should continue, if the value is 1 that
    // means the player won, and if the value is 2 the player lost.

    roomList = new ArrayList<>(); // 4 Building rooms
    characters = new ArrayList<>();

    // initialize those file objects to their respective text files; then call loadRoomInfo and
    // loadMap()
    roomInfo = new File("roominfo.txt");
    mapInfo = new File("map.txt");
    loadRoomInfo();
    loadMap();

    loadCharacters(); // 5.3 call the loadCharacters() method from setup()

    // NOTE: load images here !!!
    PImage ptBg = loadImage("images/portal.png");
    PImage trBg = loadImage("images/treasure.jpg");
    PortalRoom.setPortalImage(ptBg);
    TreasureRoom.setTreasureBackground(trBg);



    // [CHECKPOINT] In setup() use PApplet's loadImage() instance method to load an image from
    // the "image" folder
    //PImage bgImg = loadImage("p03images/1.jpg");
    // Make a new room object and add it to the ArrayList
    //Room room1 = new Room(1, "this is a test room", bgImg);
    //roomList.add(room1);

    // [CHECKPOINT] Create a StartRoom object and add it to the rooms ArrayList
    //StartRoom stRoom = new StartRoom(1, bgImg);
    //roomList.add(stRoom); // here polymorphism in action!!!

    /*
    // Create an instance of Treasure room and draw it.
    PImage trImg = loadImage("p03images/treasure.jpg");
    TreasureRoom.setTreasureBackground(trImg);// You must do this step by step. Or there will be
    // a NullPointerException!
    TreasureRoom trRoom = new TreasureRoom(0);
    roomList.add(trRoom);
    // [CHECKPOINT] Create an instance of Portal room and draw it
    PImage ptImg = loadImage("p03images/portal.png");
    PortalRoom.setPortalImage(ptImg);
    PortalRoom ptRoom = new PortalRoom(1, "test portal room", bgImg);
    roomList.add(ptRoom);

     */
  }

  // Add and override the public void settings() method.
  @Override
  public void settings() {
    // 3.5 In the settings() method add a call to size(800,600). This will set the window to have a
    // width of 800 and height of 600.
    size(800, 600);
  }

  public void draw() {
    //roomList.get(0).draw();
    //roomList.get(1).draw();
    //roomList.get(2).draw();
    //roomList.get(3).draw();

    // 6.3.a draw the currentRoom of the Player
    //characters.get(1).getCurrentRoom().draw();
    // 6.3.b Search for and find the instance of the Dragon, Player, an key holder in the
    // ArrayList of characters. Save them into local variables to use later.
    Dragon dragon = null;
    Player player = null;
    Character keyHolder = null;

    for (Character character : characters) {
      if (character instanceof Dragon) {
        dragon = (Dragon) character;
      } else if (character instanceof Player) {
        player = (Player) character;
      } else {
        keyHolder = character;
      }
    }
    player.getCurrentRoom().draw();

    // 6.3.c Check for if the player has a dragon, portal, or treasure nearby. If so, print the
    // corresponding message String to the console.
    if (player.isDragonNearby(dragon)) {
      System.out.println(Dragon.getDragonWarning());
    }

    if (player.isPortalNearby()) {
      System.out.println(PortalRoom.getPortalWarning());
    }

    if (player.isTreasureNearby()) {
      System.out.println(TreasureRoom.getTreasureWarning());
    }

    // 6.4.d Check that the player is in the same room as the key holder and the player does not
    // have the key. If this is true when have the player obtain the key. Then print "KEY
    // OBTAINED" to the console.
    if (keyHolder.getCurrentRoom().equals(player.getCurrentRoom()) && !player.hasKey()) {
      player.obtainKey();
      System.out.println("KEY OBTAINED");
    }

    // 6.4.e Have the player teleport. If the teleport was successful, then the teleport message
    // String should be printed to the console.
    if (player.teleport()) { //FIXME teleport()
      System.out.println(PortalRoom.getTeleportMessage());
    }

    // 6.4.f If it is the dragon’s turn to move and the gameState is continue, have it pick a
    // room to move into. Then call changeRoom(). If the change is successful, make it that is no
    // longer the dragon’s turn.
    if (isDragonTurn && gameState == 0) {
      Room destination = dragon.pickRoom();
      boolean canChange = dragon.changeRoom(destination); // FIXME idk how to represent the
      // dragon's turn
      if (canChange) {
        isDragonTurn = false;
      }
    }

    //6.4.g If the player’s current room is a treasure room, they have the key, and gameState is
    // continue, then change the gameSate to win. Print a message to the console telling the user
    // that they won.
    if (player.getCurrentRoom() instanceof TreasureRoom && player.hasKey() && gameState == 0) {
      gameState = 1;
      System.out.println("You made it!");
    }

    //6.4.h If the dragon and player are in the same room then the player loses and the gameState
    // is continue, then change the gameState to lose. Print the dragon encounter message to the
    // console.
    if (dragon.getCurrentRoom().equals(player.getCurrentRoom()) && gameState == 0) {
      gameState = 2;
      System.out.println(Dragon.getDragonEncounter());
    }

  }

  /**
   * The method is used to let the user move the player.
   */
  @Override
  public void keyPressed() {
    // 6.4.a If the gameState is not continue then return.
    if (gameState != 0) {
      return;
    }
    // 6.4.b Search for and find the instance of the Player in the ArrayList of characters. Save it
    // into local variables to use later.
    Player player = null;
    for (Character character : characters) {
      if (character instanceof Player) {
        player = (Player) character;
        break;
      }
    }
    // 6.4.c Use the key pressed value (provided automatically by PApplet as a variable called
    // key) as the ID of the room the player wants to move into.
    // For example, if the user wants to move to room ID 2, they would press key 2. You do not
    // need to consider cases where the ID is more than one digit, not a digit, or an ID for an
    // room that doesn’t exist.
    // NOTE: change the key into integer
    //int roomID = java.lang.Character.getNumericValue(key);
    int roomID = Integer.parseInt(String.valueOf(key));
    Room destination = getRoomByID(roomID);
    // 6.4.d Move them into that room by calling changeRoom(). HINT: You will want to get the
    // Room to pass into it by using the provided getRoomByID() method.
    boolean canChange = player.changeRoom(destination);
    // 6.4.e If the move was successful, update isDragonTurn variable it so that it is now the
    // Dragon’s turn to move.
    if (canChange) {
      isDragonTurn = true;
    } else {
      System.out.println("This is not a valid room to move. You need to pick a valid one.");
    }
    // 6.4.f If the move was not successful, print out a message to the console telling the user
    // that it is not a valid room to move to and they must pick a valid one.
  }

  /**
   * Loads in room info using the file stored in roomInfo
   *
   * @author Michelle
   */
  private void loadRoomInfo() {
    System.out.println("Loading rooms...");
    Scanner fileReader = null;
    try {

      //scanner to read from file
      fileReader = new Scanner(roomInfo);

      //read line by line until none left
      while (fileReader.hasNext()) {
        String nextLine = fileReader.nextLine();

        //parse info and create new room
        String[] parts = nextLine.split(" \\| ");
        int ID = Integer.parseInt(parts[1].trim()); //get the room id
        String imageName = null;
        String description = null;
        PImage image = null;
        Room newRoom = null;

        if (parts.length >= 3) {
          imageName = parts[2].trim();
          image = this.loadImage("images" + File.separator + imageName);

        }

        if (parts.length == 4) {
          description = parts[3].trim(); //get the room description
        }

        switch (parts[0].trim()) {
          case "S":
            newRoom = new StartRoom(ID, image);
            break;
          case "R":
            newRoom = new Room(ID, description, image);
            break;
          case "P":
            newRoom = new PortalRoom(ID, description, image);
            break;
          case "T":
            newRoom = new TreasureRoom(ID);
            break;
          default:
            break;
        }

        if (newRoom != null) {
          roomList.add(newRoom);
        }
      }
    } catch (IOException e) { //handle checked exception
      e.printStackTrace();
    } finally {
      if (fileReader != null)
        fileReader.close(); //close scanner regardless of what happened for security reasons :)
    }
  }

  /**
   * Loads in room connections using the file stored in mapInfo
   *
   * @author Michelle
   */
  private void loadMap() {
    System.out.println("Loading map...");
    Scanner fileReader = null;
    try {
      //scanner to read from file
      fileReader = new Scanner(mapInfo);

      //read line by line until none left
      while (fileReader.hasNext()) {

        //parse info
        String nextLine = fileReader.nextLine();
        String parts[] = nextLine.split(" ");
        int id = Integer.parseInt(parts[0]);

        Room toEdit = getRoomByID(id); //get the room we need to update info for adjacent rooms

        //add all the rooms to the adj room list of toEdit
        for (int i = 1; i < parts.length; i++) {
          Room toAdjAdd = getRoomByID(Integer.parseInt(parts[i]));
          toEdit.addToAdjacentRooms(toAdjAdd);
        }
      }
    } catch (IOException e) { //handle checked exception
      e.printStackTrace();
    } finally {    //close scanner regardless of what happened for security reasons :)
      if (fileReader != null)
        fileReader.close();
    }
  }

  private void loadCharacters() {
    System.out.println("Adding characters...");
    characters.add(new Character(getRoomByID(5), "KEYHOLDER"));
    characters.add(new Player(getRoomByID(1)));
    characters.add(new Dragon(getRoomByID(9)));
  }

  /**
   * Get the room objected associated with the given ID.
   *
   * @param id the ID of the room to retrieve
   * @return the Room that corresponds to that id
   * @author Michelle
   */
  private Room getRoomByID(int id) {
    /*
    int indexToEdit = roomList.indexOf(new Room(id,"dummy", null));
    Room toEdit = roomList.get(indexToEdit);
    return toEdit;

     */
    for (Room room : roomList) {
      if (room.getID() == id) {
        return room;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    PApplet.main("DragonTreasureGame"); // 3.2 call PApplet.main
  }
}

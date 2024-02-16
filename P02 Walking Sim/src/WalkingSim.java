import processing.core.PImage;
import java.io.File;
import java.util.Random;

public class WalkingSim {
  private static Random randGen;
  private static int bgColor;
  private static PImage[] frames;
  // 4.2.1
  private static Walker[] walkers;

  public static void setup() {
    //System.out.println("You are all set.");
    randGen = new Random();
    bgColor = randGen.nextInt();

    frames = new PImage[Walker.NUM_FRAMES];
    for (int i = 0; i < frames.length; i++) {
      frames[i] = Utility.loadImage("images" + File.separator + "walk-" + i + ".png");
    }

    walkers = new Walker[8];
    int randNum = randGen.nextInt(walkers.length) + 1;

    for (int i = 0; i < randNum; i++) {
      float x = randGen.nextFloat(Utility.width());
      float y = randGen.nextFloat(Utility.height());
      walkers[i] = new Walker(x, y);
    }

  }

  public static void draw() {
    //System.out.println("draw() method ends");
    // set the background color
    Utility.background(bgColor);

    for (Walker walker : walkers) {
      if (walker != null) {
        int frameIndex = walker.getCurrentFrame();
        //4.2.3
        float x = walker.getPositionX();
        float y = walker.getPositionY();

        if (walker.isWalking()) {
          walker.setPositionX((x + 3) % Utility.width());
          x = walker.getPositionX();
          /*
          if (x > Utility.width()) {
            walker.setPositionX(0);
            x = walker.getPositionX();
          }
           */
        }
        // draw each of the non-null walker
        Utility.image(frames[frameIndex], x, y);

        // 4.4.6 test isMouseOver() method
        /*
        if (isMouseOver(walker)) {
          System.out.println("Mouse is over a walker");
        }
         */

        // 4.5.4 check if each of the non-null walkers isWalking - if it is, call its update()
        // method
        if (walker.isWalking()) {
          walker.update();
        }
      }
    }
  }

  // 4.4.5 Where's the mouse?

  /**
   * The method checks if the mouse is currently hovering over any part of one of the Walker images
   * (not including its edges). If so, it returns true.
   *
   * @param walker an object of a walker used to simulate a walker
   * @return Returns true if the mouse is hovering over any part of one of the Walker images
   */
  public static boolean isMouseOver(Walker walker) {
    int frameIndex = walker.getCurrentFrame();
    // lower bound and upper bound of x and y for the walker
    float lbX = walker.getPositionX() - (frames[frameIndex].width / 2); // how to ensure that lbX is
    // positive?
    float ubX = walker.getPositionX() + (frames[frameIndex].width / 2);
    float lbY = walker.getPositionY() - (frames[frameIndex].height / 2);
    float ubY = walker.getPositionY() + (frames[frameIndex].height / 2);

    // variable showing if the mouse position is within the boundary
    boolean signX = Utility.mouseX() < ubX && Utility.mouseX() > lbX;
    boolean signY = Utility.mouseY() < ubY && Utility.mouseY() > lbY;

    if (signX && signY) {
      return true;
    }

    return false;
  }

  /**
   * This method will be called automatically whenever the user clicks the mouse.
   */
  public static void mousePressed() {
    for (Walker walker : walkers) {
      if (walker != null && isMouseOver(walker)) {
        walker.setWalking(true);
        break;
      }
    }
  }

  /**
   * The method will be called automatically whenever the user types in 's' or 'a', where s
   * stands for stoping walking and a stands for adding person.
   *
   * @param key the letter you typed on your keyboard
   */
  public static void keyPressed(char key) {
    if (key == 'a' || key == 'A') {
      for (int i = 0; i < walkers.length; i++) {
        if (walkers[i] == null) {
          float x = randGen.nextFloat() * (Utility.width() - frames[0].width);
          float y = randGen.nextFloat() * (Utility.height() - frames[0].height);
          walkers[i] = new Walker(x, y);
          break; // Stop iterating once a null element is found and a new Walker is added
        }
      }
    } else if (key == 's' || key == 'S') {
      for (Walker walker : walkers) {
        if (walker != null) {
          walker.setWalking(false);
        }
      }
    }
  }

  public static void main(String[] args) {
    Utility.runApplication();
  }
}

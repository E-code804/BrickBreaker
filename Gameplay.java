//Game play class 28:41
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;//must implement in code to compile.
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Scanner;

public class Gameplay extends JPanel implements KeyListener, ActionListener//key is for arrow keys, action for ball movement
{
  private boolean play = false;
  private int score = 0;
  private int totalBricks;
  private Timer time;
  private int delay = 1;
  private int playerX = 310;//starting position of slider
  private int ballPosX, ballPosY; //ball starting pos
  private int ballXDir = -1; // direction of ball
  private int ballYDir = -2;
  private String opt;

  private MapGen map;

  public Gameplay()
  {
    System.out.println("Hello and Welcome to my brick breaker game. Would you like the normal mode or extreme mode:");
    System.out.println("In extreme mode, the speed of the ball changes sporadically, and you move at half the speed!");
    Scanner in = new Scanner(System.in);
    opt = in.nextLine();
    if(opt.toLowerCase().equals("extreme"))
    {
      map = new MapGen(8,9);
      totalBricks = 72;
    }
    else
    {
      map = new MapGen(3,7);
      totalBricks = 21;
    }

    //sets a random position for ball
    Random rand = new Random();
    int int_random = rand.nextInt(600-200)+200;
    ballPosX = int_random;

    Random rand3 = new Random();
    int int_random3 = rand3.nextInt(450-300) + 300;
    ballPosY = int_random3;


    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    time = new Timer(delay, this);
    time.start(); //starts the timer
  }

  public void paint(Graphics g)
  {
    //background for window
    g.setColor(Color.black);
    //Rectangle for background
    g.fillRect(1,1,692,592);

    //drawing the map
    map.draw((Graphics2D) g);

    //borders
    g.setColor(Color.yellow);
    g.fillRect(0,0,3,592);
    g.fillRect(0,0,692,3);
    g.fillRect(691,0,3,592);

    //score display
    g.setColor(Color.white);
    //g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString("SCORE: " +score, 590,30);


    // create the paddle
    g.setColor(Color.green);
    g.fillRect(playerX,550,100, 8);

    //creating the ball
    g.setColor(Color.yellow);

    g.fillOval(ballPosX,ballPosY, 20, 20);

    //if we finished
    if(totalBricks <= 0)
    {
      play = false;
      ballXDir = 0;
      ballYDir = 0;
      g.setColor(Color.green);
      g.drawString("You Won!", 230, 300);

      g.drawString("Please press enter to restart", 230, 350);
    }

    //game Over
    if(ballPosY > 570)
    {
      play = false;
      ballXDir = 0;
      ballYDir = 0;
      g.setColor(Color.red);
      g.drawString("GAME OVER", 230, 300);

      g.drawString("Please press enter to restart", 230, 350);
    }

    g.dispose();
  }
  public void moveRight()//for paddle
  {
    play = true;
    if(opt.toLowerCase().equals("extreme"))
    {
      playerX+=10; // if pressed right, will move 20 pixels to the right
    }
    else
    {
      playerX+=20; // if pressed right, will move 10 pixels to the right
    }
  }
  public void moveLeft()
  {
    play=true;
    if(opt.toLowerCase().equals("extreme"))
    {
      playerX-=10; // if pressed left, will move 10 pixels to the left
    }
    else
    {
      playerX-=20; // if pressed left, will move 20 pixels to the left
    }
  }
  //implementing all required methods for code to compile
  @Override
  public void actionPerformed(ActionEvent e)
  {
    time.start();
    if(play)//we pressed an arrow key
    {
      //intersection with paddle
      // random for hard mode
      Random rand2 = new Random();
      int int_random2 = rand2.nextInt(20-10)+10;
      if(opt.toLowerCase().equals("extreme")==false && new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX, 550,100,8)))
      {
        ballYDir = -ballYDir;
      }
      if(opt.toLowerCase().equals("extreme") && new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX, 550,100,8)))
      {
        ballYDir = -int_random2;
      }


      for(int x = 0; x < map.map.length; x++) // intersect w brick
      {
        for(int y = 0; y < map.map[0].length; y++)
        {
          if(map.map[x][y] > 0)// for the intersect
          {
            int brickX = y * map.brickWidth + 80;
            int brickY = x * map.brickHeight + 50;
            int brickWidth = map.brickWidth;
            int brickHeight = map. brickHeight;

            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight); // rect around brick
            Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20); //ball intersect
            Rectangle brickRect = rect;

            if(ballRect.intersects(brickRect))
            {

              map.setBrickValue(0,x,y);
              totalBricks --;
              score += 5;
              if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width)
              {
                ballXDir = -ballXDir;
              }
              else{ballYDir = -ballYDir;}

            }
          }
        }
      }

      ballPosX += ballXDir;
      ballPosY += ballYDir;
      if(ballPosX < 0){ballXDir = -ballXDir;}//left border, all changes direction
      if(ballPosY < 0){ballYDir = -ballYDir;}//top of window
      if(ballPosX > 670){ballXDir = -ballXDir;}// right border of window
    }
    repaint();//recall paint method and draw everything again. need it cause we increment/decrement the playerX
  }
  @Override
  public void keyTyped(KeyEvent e)
  {
    if(play == false)//when game over and want to restart
    {
      if(opt.toLowerCase().equals("extreme"))
      {
        map = new MapGen(8,9);
        totalBricks = 72;
      }
      else
      {
        map = new MapGen(3,7);
        totalBricks = 21;
      }
      play = true;
      ballPosX = 120;
      ballPosY = 350;
      ballXDir = -1;
      ballYDir = -2;
      playerX = 310;
      score = 0;




      repaint();
    }
  }
  @Override
  public void keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT)//if right arrow key was clicked
    {
      if(playerX >= 600){playerX=600;}//makes sure we are within window
      else{moveRight();}
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT)//if left arrow key was clicked
    {
      if(playerX < 10){playerX=10;}//makes sure we are within window
      else{moveLeft();}
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {

  }
}

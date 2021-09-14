//Brick Breaker in java
import javax.swing.JFrame;

public class brickBreaker
{
  public static void main(String[] args)
  {
    JFrame obj = new JFrame();
    Gameplay play = new Gameplay();
    obj.setBounds(10,10,700,600);//dimensions
    obj.setTitle("Breakout Ball");//set title of window
    obj.setResizable(false);
    obj.setVisible(true);//in order to see the new window
    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when you click X, program closes
    obj.add(play);
  }
}

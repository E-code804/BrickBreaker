//Map Generator for brick
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Rectangle;
public class MapGen
{
  public int map[][];//will contain the bricks
  public int brickWidth, brickHeight;


  public MapGen(int row, int col)// constructor revieves num of row and cols for num of bricks
  {
    map = new int[row][col];
    for(int x = 0; x < map.length; x++)
    {
      for(int y = 0; y < map[0].length; y++)
      {
        map[x][y] = 1;//one will detect the this brick has not been intersected, i.e its health
      }
    }
    brickWidth = 540/col;
    brickHeight = 150/row;
  }
  public void draw(Graphics2D g)//draw the bricks where theres a value of one
  {
    for(int x = 0; x < map.length; x++)
    {
      for(int y = 0; y < map[0].length; y++)
      {
        if(map[x][y] > 0)//if there is the one value, we can draw the brick
        {
          g.setColor(Color.blue);
          g.fillRect(y * brickWidth + 80, x * brickHeight + 50, brickWidth, brickHeight);

          g.setStroke(new BasicStroke(3));
          g.setColor(Color.black);
          g.drawRect(y * brickWidth + 80, x * brickHeight + 50, brickWidth, brickHeight);
        }
      }
    }
  }
  public void setBrickValue(int value, int row, int col)
  {
    map[row][col] = value;
  }

}

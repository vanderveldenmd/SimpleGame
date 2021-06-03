import java.util.Random;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
/**

@author Justine Horst, Benjamin Lowe, Michael Vander Velden
*/
public class Targets extends PFigure
{

   private Image img;
   private Image img2;

   private static int enemySize = 40;
   private static int enemyStart = 0;

   private final static int scoreTarget = 100;
   private final static int targetPriority = 3;

   private final static int maxX = 660;
   private final static int minXY = 30;
   private final static int maxY = 460;

   boolean rotated = false;
   Random randInt = new Random();

   private int xVel;
   private int yVel;
   private int totalVel;

   private boolean directionRight;

   public Targets(int xStart, int yStart, Panel p)
   {
      super(xStart, yStart, enemySize, enemySize, targetPriority, p);
      try
      {
         File file = new File("enemy.png");
         img = ImageIO.read(file);
      }
      catch (Exception e)
      {
         System.out.println("Crashing: " + e);
         // Whatever???
      }
      xVel = randInt.nextInt(2) + 1;
      directionRight = true;
      totalVel = xVel;
      if (x >= maxX)
      {
         xVel = -xVel;
         directionRight = false;
      }
      yVel = 0;
   }

   @Override
   public void draw()
   {
      if (img != null)
      {
         Graphics g = panel.getGraphics();
         g.drawImage(img, x, y, width, height, null);
      }
   }

   @Override
   public void move()
   {
      if (xVel == 0 && yVel == 0)
      {
         directionRight = true;
         xVel = 3;
      }
      if (this.x > maxX && directionRight)
      {
         xVel = -xVel;
         directionRight = false;
      }

      if (x < minXY && !directionRight)
      {
         xVel = -(xVel);
         directionRight = true;
      }
      x = x + this.xVel;

      if (y > maxY)
      {
         yVel = -Math.abs(yVel);
      }

      if (y < minXY)
      {
         yVel = Math.abs(yVel);
      }

      y = y + this.yVel;

   }

   @Override
   public void hide()
   {
      Graphics g = panel.getGraphics();
      Color oldColor = g.getColor();
      g.setColor(panel.getBackground());
      g.fillRect(x, y, width, height);
      g.setColor(oldColor);
   }

   @Override
   public int destroy()
   {
      try
      {
         img2 = ImageIO.read(new File("explosion1.png"));

      }
      catch (Exception e)
      {
         e.printStackTrace();
         System.out.println("Crashing: " + e);
      }
      Graphics g = panel.getGraphics();
      g.drawImage(img2, x, y, width, height, null);
      try
      {
         Thread.sleep(100);
      }
      catch (Exception e)
      {
         // do nothing
      }
      this.hide();
      return scoreTarget;
   }

   @Override
   public void changeVel(PFigure s)
   {
      if (s == null)
      {
         if (xVel < 0 && directionRight)
         {
            xVel = -totalVel;
         }

         yVel = 0;
         return;
      }
      double dX = (double) (s.getX() - this.x);
      double dY = (double) (s.getY() - this.y);
      double hyp = Math.pow(Math.pow(dX, 2) + Math.pow(dY, 2), .5);

      double newxVel = (dX / hyp) * totalVel;
      double newyVel = (dX / hyp) * totalVel;

      newxVel = Math.abs(newxVel);
      if (dX < 0)
      {
         directionRight = false;
         newxVel = -newxVel;
      }

      newyVel = Math.abs(newyVel);
      if (dY < 0)
      {
         directionRight = true;
         newyVel = -newyVel;
      }

      xVel = (int) Math.rint(newxVel);
      yVel = (int) Math.rint(newyVel);
   }

}

import java.awt.*;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
/**
This class sets up the friendly satellites and their movements
*/
public class Friendly extends PFigure
{

   static int sizeSprite = 40;
   private Image img1;
   private Image img2;
   Random randInt = new Random();
   private int xVel;
   private int yVel;
   
   static int xHighEscape = 660;
   static int yHighEscape = 460;
   static int xyLowEscape = 40;
   
   static final int sleepLength = 80;
   
   static final int square = 2;
   static final double sqRt = .5;

   private final static int friendlyScore = -10;

   public Friendly(int startX, int startY, Panel p)
   {
      super(startX, startY, sizeSprite, sizeSprite, 1, p);
      try
      {
         File file1 = new File("happySatellite.png");
         img1 = ImageIO.read(file1);
      }
      catch (Exception e)
      {
         System.out.println("Crashing: " + e);
         // Whatever???
      }
      yVel = randInt.nextInt(1) + 1;
      xVel = randInt.nextInt(yVel);
   }

   @Override
   public void draw()
   {
      if (img1 != null)
      {
         Graphics g = panel.getGraphics();
         g.drawImage(img1, x, y, width, height, null);
      }
   }

   /**
    Moves the given friendly by a certain amount across the screen when this
    is called.

    @param xVel the number of pixels to move the object
    */
   @Override
   public void move()
   {
      if (xVel == 0 && yVel == 0)
      {
         yVel = 2;
      }
      x = x + this.xVel;
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
      if (x > xHighEscape || x < xyLowEscape || 
          y < xyLowEscape || y > yHighEscape)
      {
         hide();
         return 0;
      }
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
         Thread.sleep(sleepLength);
      }
      catch (Exception e)
      {
         // do nothing
      }
      this.hide();
      return friendlyScore;
   }

   /**

    @param s
    */
   @Override
   public void changeVel(PFigure s)
   {
      int maxVel = 4;
      if (s == null)
      {
         return;
      }
      double totalVel = Math.pow(Math.pow((double) xVel, square) + 
                                 Math.pow((double) yVel, square), sqRt);

      double dX = (double) (s.getX() - this.x);
      double dY = (double) (s.getY() - this.y);
      double hyp = Math.pow(Math.pow(dX, square) + 
                            Math.pow(dY, square), sqRt);

      if(totalVel > maxVel)
         totalVel = maxVel;
      
      double newxVel = (dX / hyp) * totalVel;
      double newyVel = (dX / hyp) * totalVel;

      newxVel = Math.abs(newxVel);
      if (dX > 0)
      {
         newxVel = -newxVel;
      }

      newyVel = Math.abs(newyVel);
      if (dY > 0)
      {
         newyVel = -newyVel;
      }

      xVel = (int) Math.rint(newxVel);
      yVel = (int) Math.rint(newyVel);
   }
}

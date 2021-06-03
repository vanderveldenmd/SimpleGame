import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
/**
*/
public class Gun extends PFigure
{

   public static int sizeGun = 40;
   public static int xSpawn = 350;
   public static int ySpawn = 478;
   
   public static int gunPriority = 2;
   public static int gunVelocity = 20;

   public Gun(Panel p)
   {
      super(xSpawn, ySpawn, sizeGun, sizeGun, gunPriority, p);
   }

   public void moveRight()
   {
      super.move(gunVelocity, 0);
      if (x < -width / 2)
      {
         x = panel.getSize().width - width / 2;
      }
      else if ((x + width / 2) > panel.getSize().width)
      {
         x = -width / 2;
      }
   }
   
   public void moveLeft()
   {
            super.move(-gunVelocity, 0);
      if (x < -width / 2)
      {
         x = panel.getSize().width - width / 2;
      }
      else if ((x + width / 2) > panel.getSize().width)
      {
         x = -width / 2;
      }
   }

   public void draw()
   {
      Graphics g = panel.getGraphics();

      g.setColor(Color.black);
      g.drawLine(x + 2, y - 20, x - 2, y - 20);

      g.drawLine(x + 2, y - 20, x + 4, y + 8);
      g.drawLine(x - 2, y - 20, x - 4, y + 8);

      g.drawLine(x + 8, y + 10, x + 18, y + 10);
      g.drawLine(x - 8, y + 10, x - 18, y + 10);

      g.drawLine(x + 18, y + 10, x + 18, y - 10);
      g.drawLine(x - 18, y + 10, x - 18, y - 10);

      g.drawLine(x + 18, y + 10, x + 18, y - 10);
      g.drawLine(x - 18, y + 10, x - 18, y - 10);

      g.drawLine(x + 18, y - 10, x + 20, y - 10);
      g.drawLine(x - 18, y - 10, x - 20, y - 10);

      g.drawLine(x + 20, y - 10, x + 20, y + 16);
      g.drawLine(x - 20, y - 10, x - 20, y + 16);

      g.drawLine(x + 20, y + 16, x + 8, y + 18);
      g.drawLine(x - 20, y + 16, x - 8, y + 18);

      g.drawLine(x + 4, y + 18, x - 4, y + 18);

      g.fillRect(x - 1, y - 6, 3, 6);

      g.setColor(Color.gray);
      g.drawLine(x + 4, y + 8, x + 8, y + 8);
      g.drawLine(x - 4, y + 8, x - 8, y + 8);

      g.drawLine(x + 8, y + 8, x + 8, y + 20);
      g.drawLine(x - 8, y + 8, x - 8, y + 20);

      g.drawLine(x + 8, y + 20, x + 4, y + 20);
      g.drawLine(x - 8, y + 20, x - 4, y + 20);

      g.drawLine(x + 4, y + 20, x + 4, y + 8);
      g.drawLine(x - 4, y + 20, x - 4, y + 8);

      g.fillRect(x - 19, y - 10, 2, 26);
      g.fillRect(x + 18, y - 10, 2, 26);

      g.setColor(Color.red);
      g.fillRect(x + 14, y + 10, 4, 4);
      g.fillRect(x - 18, y + 10, 4, 4);
      
      g.setColor(Color.blue);
      g.fillOval(x - 2, y + 6, 5, 5);
   }

   public void hide()
   {
      Graphics g = panel.getGraphics();
      Color oldColor = g.getColor();
      g.setColor(panel.getBackground());
      g.fillRect(x - sizeGun / 2, y - sizeGun / 2, width + 2, height + 2);
      g.setColor(oldColor);
   }
      
   public int destroy()
   {
      return 0;
   }

}

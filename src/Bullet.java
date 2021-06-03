import java.awt.*;
/**
*/
public class Bullet extends PFigure
{

   protected final static int sizeSprite = 5;
   protected final static int startY = 450;
   protected final static int bulVel = -30;
   private final static int bulletPriority = 4;

   public Bullet(int startX, Panel p)
   {
      super(startX, startY, sizeSprite, sizeSprite, bulletPriority, p);
   }

   @Override
   public void draw()
   {
      Graphics g = panel.getGraphics();
      g.setColor(Color.black);
      g.drawOval(x, y, width, height);
      g.fillOval(x, y, width, height);
   }

   /**
    Moves the given friendly by a certain amount across the screen when this
    is called.

    
    */
   @Override
   public void move()
   {
      y = y + bulVel;
   }

   @Override
   public void hide()
   {
      Graphics g = panel.getGraphics();
      Color oldColor = g.getColor();
      g.setColor(panel.getBackground());
      g.fillRect(x, y, width + sizeSprite, height + sizeSprite);
      g.setColor(oldColor);
   }

   @Override
   public int destroy()
   {
      hide();
      return 0;
   }
}

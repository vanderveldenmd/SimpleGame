import java.awt.*;
/**
This is the PFigure method which is the superclass from which all other
sprites derive from. It controls the various sprite's size, location, and
visibility status.
*/
public abstract class PFigure implements Comparable
{
   protected int x, y;           // Current position of the figure
   protected int width, height;  // Drawn (displayed) this size
   protected int priority;       // Can use to determine "winner"
   protected Panel panel;        // Panel the figure lives on
   private final int rectSize = 20;
   
   private static int square = 2;
   private static double sqRt = .5;
   
   /**
   The constructor class for PFigure tells Java where to put a sprite and how
   big it should be.
   @param startX where the PFigure will be located in x-direction
   @param startY where the PFigure will be located in Y-direction
   @param _width how wide the figure will spawn
   @param _height how tall the figure will spawn
   @param pr the priority which is used for collision detection
   @param p the panel on which the figure will live
   */
   public PFigure ( int startX, int startY, int _width, int _height, 
                    int pr, Panel p )
   {
       x = startX;
       y = startY;
       width = _width;
       height = _height;
       priority = pr;
       panel = p;
   }
   
   /**
   Checks to see the winner in a collision by returning which priority is
   the largest.
   @param o an object we wish to compare to
   @return the higher priority integer number.
   */
   public int compareTo(Object o)
   {
      if( o instanceof PFigure )
         return priority - ((PFigure)o).priority;
      return Integer.MAX_VALUE;
   }
      
   /**
   The collision method takes a PFigure and checks it's location and size
   against another PFigure's location and size. If the two overlap, we have
   some kind of collision.
   @param p a PFigure we will check against collision.
   @return true if the PFigures are close enough that they collide, false if
   otherwise
   */
   public boolean collidedWith ( PFigure p )
   {
      if (  p == null || this.compareTo(p) == 0)
         return false;

      return ( x + width ) >= p.x && ( p.x + p.width ) >= x &&
             ( y + height ) >= p.y && ( p.y + p.height ) >= y;
   }
   
   /**
   The method which moves PFigures around on the screen. It takes two inputs
   and moves the sprites based on what those inputs are.
   @param deltaX how far to move in the x-direction
   @param deltaY how far to move in the y-direction
   */
   public void move ( int deltaX, int deltaY )
   {
      x = x + deltaX;
      y = y + deltaY;
   }
   
   /**
   The hide method draws a rectangle the color of the background over a sprite
   to "hide" it from the user's view in the panel.
   */
   public void hide()
   {
      Graphics g = panel.getGraphics();
      Color oldColor = g.getColor();
      g.setColor(panel.getBackground() );
      g.fillRect(x - rectSize, y - rectSize, width + 2, height);
      g.setColor(oldColor);
   }
   
   /**
   Our parameter-less move method has no code because it is overriden by all
   the subclasses except gun. This should never be called by an object of 
   class PFigure.
   */
   public void move()
   {
   }

   /**
   This is a bit more complicated than just hide. We needed the destroy 
   method because we needed something to determine whether an object was
   destroyed in collision or simply "escaped" the panel. It creates a
   separate image which shows a figure has been destroyed rather than just
   hidden.
   @return and integer which is a score for the GameOver class.
   */
   abstract public int destroy();

   /**
   Since there are no PFigures in the game, we can make this an abstract
   method.
   */
   abstract public void draw();
   
   /**
   For changing the velocities of the targets and the friendlies, we needed a 
   way to know the location of the x and y coordinates of the friendlies and
   targets. Since this is protected data, we return a copy of the data without
   accessing it directly.
   @return integer of the figure's x location
   */
   public int getX()
   {
      return x;
   }
   
   /**
   For changing the velocities of the targets and the friendlies, we needed a 
   way to know the location of the x and y coordinates of the friendlies and
   targets. Since this is protected data, we return a copy of the data without
   accessing it directly.
   @return integer of the figure's y location
   */   
   public int getY()
   {
      return y;
   }
   
   /**
   We also needed a way to check the priority of a figure without accessing
   the protected data directly. We return a copy.
   @return integer of the figure's priority
   */   
   public int getPriority()
   {
      return priority;
   }
   
   /**
   Returns an integer which is the distance between two PFigures. It does this
   by simple pythagorean theorem.
   @param s a PFigure we want to find the distance from
   @return an integer which is the distance between the two
   */
   public int distanceTo(PFigure s)
   {
      double distance = Math.pow((Math.pow(this.x - s.x, square)) + 
                                 (Math.pow(this.y - s.y, square)),sqRt);
      int dist = (int)distance;
      return dist;
   }
   
   /**
   A method which is overriden by friendly and targets. This adjusts those
   figure's velocity so that they either move towards or away from certain
   targets.
   @param s a PFigure we wish to move away from or towards.
   */
   public void changeVel(PFigure s)
   {
   }
   
   /**
   Checks to see if the PFigures are at the same location by checking their
   locations, sizes, and priorities.
   @param p the PFigure we wish to check
   @return true if they are the same, false if otherwise.
   */
   public boolean equals(PFigure p)
   {
      return (x == p.x && y == p.y && width == p.width && 
              height == p.height && priority == p.priority);
   }
}
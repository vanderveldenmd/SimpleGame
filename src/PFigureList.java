
import java.awt.*;

/**
This is the PFigureList which is where all PFigures shown on the panel will be
stored. It was functions which control the movement, hiding, drawing, and
the number of PFigures on screen at any given time. It will create and remove
PFigures based on whatever is necessary.
@author Justine Horst, Benjamin Lowe, Michael Vander Velden
*/
public class PFigureList
{

   int satelliteNo;
   int targetNo;
   int total;
   private final int maxTargets = 5;
   private final int maxSatellites = 3;
   PFigure[] list;

   /**
   The constructor class creates a constructor with the size given and will
   be the list which contains all bullets.

   @param arraySize the number of bullets we wish to be on screen at once
   @param p is the panel on which everything will spawn
   */
   public PFigureList(int arraySize, Panel p)
   {
      list = new PFigure[arraySize];
      list[0] = new Gun(p);
      satelliteNo = 0;
      targetNo = 0;
   }

   /**
   This method removes a sprite from the list by changing it's location in
   memory to null. It calls the destroy method in PFigure and returns an
   integer number which is used to calculate the score.

   @param location the location of the sprite in the array to be removed
   @return an integer value which is used to update the score
   */
   public int removeSprite(int location)
   {
      if (location == 0)
      {
         return 0;
      }
      total--;
      int deltaScore = list[location].destroy();
      list[location] = null;
      return deltaScore;
   }

   /**
    The new sprite adds a new PFigure (sprite) to the array at the first
   available location. Since sprites may be removed out of order, this can't
   be done in a circular array therefore must check every available spot
   for a null character before placing the new sprite in that slot.
   
   @param fig the new PFigure to be placed into the array
   @param p the panel which the game is drawn on, telling where to place
   the new PFigure
   @return true if the new sprite was placed in the array, false if not
   */
   public boolean newSprite(PFigure fig, Panel p)
   {
      if (isFull())
      {
         return false;
      }

      int position = 1;
      while (position < list.length)
      {
         if (list[position] == null)
         {
            list[position] = fig;
            total++;
            return true;
         }
         position++;
      }
      return false;
   }

   /**
   This method checks to see if the array is full by comparing the total
   sprites to the length of the array to return whether the array is full
   or if a new sprite can be added.

   @return true if the list is full, false if otherwise
   */
   private boolean isFull()
   {
      return list.length == total;
   }

   /**
   This is the method that will be called if there is a game over. This will
   clear the entire list of PFigures for the next game.
   */
   public void clearList()
   {
      for (int i = 0; i < list.length; i++)
      {
         list[i] = null;
      }
   }

   /**
   This method returns a copy of the sprite at a given location in the array.
   This is used to check collision, movement, and score.
   @param location the index in the array we wish to check
   @return a copy of the PFigure at the given index or null
   */
   public PFigure getSprite(int location)
   {
      return list[location];
   }

   /**
   This method moves all the sprites based on their own movement patterns and
   velocities. It calls all the places in the array, except where the array
   has a null value.
   */
   public void moveAll()
   {
      for (int i = 1; i < list.length; i++)
      {
         PFigure p = list[i];
         if (!(p == null))
         {
            p.move();
         }
      }
   }

   /**
   This moves the gun to the right on the screen given it's velocity. Since
   the gun is always in position 0, this is not subject to the moveAll()
   method and considering the gun is only controlled by the user.
   */
   public void movegunRight()
   {
      ((Gun) list[0]).moveRight();
   }

   /**
   This moves the gun to the left on the screen given it's velocity. Since
   the gun is always in position 0, this is not subject to the moveAll()
   method and considering the gun is only controlled by the user.
   */
   public void movegunLeft()
   {
      ((Gun) list[0]).moveLeft();
   }

   /**
   This method hides all figures on the list by calling all of their hide()
   methods, one after the other.
   */
   public void hideAll()
   {
      for (int i = 0; i < list.length; i++)
      {
         PFigure p = list[i];
         if (!(p == null))
         {
            p.hide();
         }
      }
   }

   /**
   This method goes through the whole array and draws each PFigure one at a
   time by calling their respective draw methods.
   */
   public void drawAll()
   {
      for (int i = 0; i < list.length; i++)
      {
         PFigure p = list[i];
         if (!(p == null))
         {
            p.draw();
         }
      }
   }

   /**
   This checks to see if there are currently any satellites within the array.
   @return true if there is a friendly in the array, false if otherwise.
   */
   public boolean hasSatellite()
   {
      return (satelliteNo != 0);
   }

   /**
   This method keeps track of how many enemies are on screen at a given time.
   It takes the parameter num and uses it to update the total number of
   enemies.
   @param num the integer which is used to update the number.
   */
   public void updateEnemy(int num)
   {
      targetNo += num;
   }

   /**
   Changes the number of satellites with the given parameter num. We keep
   track of the number of satellites so that too many don't spawn on screen
   at a given time.
   @param num 
   */
   public void updateSatellite(int num)
   {
      satelliteNo += num;
   }

   /**
   Checks to see if the enemy number equals or is greater than the maximum
   number of enemies allowed on screen at once.
   @return true if the number of enemies exceeds the maximum number, false if
   otherwise.
   */
   public boolean enemyFull()
   {
      return targetNo >= maxTargets;
   }

   /**
   Checks to see if the friendly number equals or is greater than the maximum
   number of friendlies allowed on screen at once.
   @return true if the number of friendlies exceeds the maximum number, false
   if otherwise.
   */
   public boolean satelliteFull()
   {
      return satelliteNo >= maxSatellites;
   }

   /**
   This is the method which determines how the enemies should move. It checks
   to see if there are any satellites in the array. Than it checks to see
   which one is closest and adjusts it's velocity so that it moves towards
   the closest satellite.
   */
   public void seesSatellite()
   {
      checkSatellite();
      int minDist = 700;
      int dist = 0;
      int index = -1;
      boolean hasSatellite;
      for (int i = 1; i < list.length; i++)
      {
         hasSatellite = false;
         PFigure t = list[i];
         if (t != null && t.getPriority() == 3)
         {
            for (int j = 1; j < list.length; j++)
            {
               if (i == j)
               {
                  continue;
               }
               PFigure s = list[j];
               if (s != null && s.getPriority() == 1)
               {
                  hasSatellite = true;
                  dist = t.distanceTo(s);
                  if (dist < minDist)
                  {
                     index = j;
                  }
               }
            }
            if (hasSatellite && index != -1)
            {
               ((Targets) list[i]).changeVel(list[index]);
            }
            else
            {
               ((Targets) list[i]).changeVel(null);
            }
         }
      }
   }

   /**
   This method adjusts a satellite's velocity based on the location of the
   enemies moving towards it. Unlike the enemies who move to the closest 
   friendly, the satellites will move away from the closest enemy only if they
   are a maximum distance away.
   */
   public void seesEnemy()
   {
      int minDist = 200;
      int closest = 700;
      int dist = 0;
      int index = -1;
      boolean hasEnemy;
      for (int i = 1; i < list.length; i++)
      {
         hasEnemy = false;
         PFigure s = list[i];
         if (s != null && s.getPriority() == 1)
         {
            for (int j = 1; j < list.length; j++)
            {
               if (i == j)
               {
                  continue;
               }
               PFigure t = list[j];
               if (t != null && t.getPriority() == 3)
               {
                  hasEnemy = true;
                  dist = t.distanceTo(s);
                  if (dist < closest)
                  {
                     index = j;
                     closest = dist;
                  }
               }
            }
            if (hasEnemy && dist < minDist)
            {
               ((Friendly) list[i]).changeVel(list[index]);
            }
            else
            {
               ((Friendly) list[i]).changeVel(null);
            }
         }
      }
   }

   /**
   This method updates the number of satellites by looking through the entire
   list for satellites and updates the number of satellites based on how many
   are found.
   */
   private void checkSatellite()
   {
      for (int i = 1; i < list.length; i++)
      {
         satelliteNo = 0;
         if (list[i] != null && list[i].getPriority() == 1)
         {
               satelliteNo++;
         }
      }
   }
}

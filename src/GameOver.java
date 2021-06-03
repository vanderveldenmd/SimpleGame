/**
*/
public class GameOver
{

   private int lives, score;
   private static int startLives = 3;
   
   public GameOver()
   {
      lives = startLives;
      score = 0;
   }

   public boolean gameOverCondition()
   {
      if (lives == 0)
      {
         return true;
      }

      if (score < 0)
      {
         return true;
      }

      return false;
   }

   public int score(int deltaScore)
   {
      score += deltaScore;
      return score;
   }

   public void removeLives()
   {
      lives--;
   }
   
   public int getLives()
   {
      return lives;
   }

   public int getScore()
   {
      return score;
   }
}

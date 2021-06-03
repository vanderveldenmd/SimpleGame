import java.awt.Graphics;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.util.Random;
/**
*/
public class FigApplet extends java.applet.Applet implements java.awt.event.ActionListener
{

   final int maxSprites = 15;
   public PFigureList list;
   public GameOver newGame;
   Random randInt = new Random();
   private javax.swing.Timer moveTimer = new javax.swing.Timer(17, this);
   
   final int ySpawn = 100;
   final int ySpawn2 = 450;
   final int xSpawn = 650;
   final int xSpawn2 = 50;
   
   private int lastEnemy = 0;
   private int lastFriendly = 0;
     
   private final int spawnEnemy = 10;
   private final int spawnFriendly = 995;
   private final int forceEnemySpawn = 100;
   private final int forceFriendlySpawn = 200;
   private final int friendlySpawnRate = 20;
   
   public static int xyLowEscape = 40;
   public static int xHighEscape = 660;
   public static int yHighEscape = 460;
   
   private final int spawnRange = 200;
   private final int spawnSide= 2;
   private final int rngRoll = 1000;
   
   public final int bulletPriority = 4;
   public final int friendlyPriority = 1;
   public final int targetPriority = 3;
   public final int gunPriority = 2;
   
   public final int targetScore = 100;
   public final int friendlyScore = -10;
   
   
   /**
    Initializes the applet FigApplet
    */
   public void init()
   {
      setSize(700, 500);
      initComponents();
      newGame = new GameOver();
      jButton1.setVisible(false);
      jButton2.setVisible(false);
      list = new PFigureList(maxSprites, figPanel);
      moveTimer.start();
      figPanel.requestFocus();
      jLabel1.setText("Lives: " + newGame.getLives());
      jLabel2.setText("Score: " + newGame.getScore());
   }

   public void reset()
   {
      newGame = new GameOver();
      jButton1.setVisible(false);
      jButton2.setVisible(false);
      list = new PFigureList(maxSprites, figPanel);
      moveTimer.start();
      figPanel.requestFocus();
      jLabel1.setText("Lives: " + newGame.getLives());
      jLabel2.setText("Score: " + newGame.getScore());
   }

   @Override
   public void actionPerformed(ActionEvent ae)
   {
      spawn();
      list.hideAll();
      list.moveAll();
      list.drawAll();
      allCollision();
      list.seesSatellite();
      list.seesEnemy();
      jLabel1.setText("Lives: " + newGame.getLives());

      if (newGame.gameOverCondition())
      {
         list.hideAll();
         moveTimer.stop();

         jButton1.setVisible(true);
         jButton2.setVisible(true);
         jButton1.addActionListener(ke
               -> 
               {
                  reset();
         });
         jButton2.addActionListener(ke
               -> 
               {
                  System.exit(0);
         });
      }
   }

   private void allCollision()
   {
      for (int i = 0; i < maxSprites; i++)
      {
         PFigure a = list.getSprite(i);
         if (a == null)
            continue;
         for (int j = i + 1; j < maxSprites; j++)
         {
            PFigure b = list.getSprite(j);
            if (b == null)
               continue;
            try
            {
               if (a.collidedWith(b))
               {
                  int changeScore1 = list.removeSprite(i);
                  int changeScore2 = list.removeSprite(j);
                  newGame.score(changeScore1);
                  newGame.score(changeScore2);
                  if (changeScore1 == targetScore || 
                      changeScore2 == targetScore)
                     list.updateEnemy(-1);
                  if (changeScore1 == friendlyScore || 
                      changeScore2 == friendlyScore)
                  {
                     list.updateSatellite(-1);
                     newGame.removeLives();
                  }
                  jLabel1.setText("Lives: " + newGame.getLives());
                  jLabel2.setText("Score: " + newGame.getScore());
               }
            }
            catch (Exception ex)
            {
               System.out.println(ex);
            }
         }
         spriteEscaped( a, i);
      }
   }
   
   private void spriteEscaped( PFigure s, int index )
   {
         if (s.getPriority() == friendlyPriority)
         {
            int xLocation = s.getX();
            int yLocation = s.getY();
            if (xLocation < xyLowEscape || xLocation > xHighEscape || 
                yLocation < xyLowEscape || yLocation > yHighEscape)
            {
               list.removeSprite(index);
               list.updateSatellite(-1);
            }
         }
         if (s.getPriority() == bulletPriority)
         {
            if (s.getY() < 0)
            {
               list.removeSprite(index);
            }
         }
   }

   public void spawn()
   {
      int result = randInt.nextInt(rngRoll);
      if ((result <= spawnEnemy || lastEnemy == forceEnemySpawn) && 
           !(list.enemyFull()))
      {
         list.updateEnemy(1);
         int y = randInt.nextInt(spawnRange) + spawnRange;
         int x = randInt.nextInt(spawnSide);
         if (x == 1)
         {
            x = xSpawn;
         }
         Targets t = new Targets(x, y, figPanel);
         list.newSprite(t, figPanel);
         lastEnemy = 0;
      }

      else if ((result > spawnFriendly || lastFriendly == forceFriendlySpawn) 
                && !(list.satelliteFull()) 
                && lastFriendly > friendlySpawnRate)
      {
         list.updateSatellite(1);
         int x = randInt.nextInt(spawnRange) + spawnRange;
         Friendly s = new Friendly(x, ySpawn, figPanel);
         list.newSprite(s, figPanel);
         lastFriendly = 0;
      }
      lastEnemy++;
      lastFriendly++;
   }

// Variables declaration - do not modify                     
// End of variables declaration                   
   /**
    This method is called from within the init() method to initialize the
    form. WARNING: Do NOT modify this code. The content of this method is
    always regenerated by the Form Editor.
    */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        button3 = new java.awt.Button();
        figPanel = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        button1.setLabel("button1");

        button2.setLabel("button2");

        button3.setLabel("button3");

        setLayout(new java.awt.BorderLayout());

        figPanel.setBackground(new java.awt.Color(255, 255, 255));
        figPanel.setMaximumSize(new java.awt.Dimension(700, 400));
        figPanel.setMinimumSize(new java.awt.Dimension(700, 400));
        figPanel.setName(""); // NOI18N
        figPanel.setPreferredSize(new java.awt.Dimension(700, 400));
        figPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                figPanelKeyPressed(evt);
            }
        });

        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(34, 25));
        jLabel1.setMinimumSize(new java.awt.Dimension(34, 25));
        figPanel.add(jLabel1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("jLabel2");
        figPanel.add(jLabel2);

        jButton1.setText("Try Again?");
        jButton1.setActionCommand("StartButton");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        figPanel.add(jButton1);

        jButton2.setText("Quit");
        jButton2.setActionCommand("StopButton");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        figPanel.add(jButton2);

        add(figPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

   private void figPanelKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_figPanelKeyPressed
   {//GEN-HEADEREND:event_figPanelKeyPressed
      // TODO add your handling code here:
      if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT)
      {
         list.hideAll();
         list.movegunLeft();
      }
      else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT)
      {
         list.hideAll();
         list.movegunRight();
      }
      else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE)
      {
         Bullet b = new Bullet(list.getSprite(0).getX(), figPanel);
         list.newSprite(b, figPanel);
         list.drawAll();
      }
   }//GEN-LAST:event_figPanelKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button2;
    private java.awt.Button button3;
    private java.awt.Panel figPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}

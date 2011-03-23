/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.karta;

import java.awt.Graphics;
import bullethell.game.Entity;
import java.util.ArrayList;
import bullethell.game.Collidable;
import bullethell.game.Game;
import bullethell.platforms.PlatformContainer;
import bullethell.platforms.PlatformNode;
import org.duncan.Library2D.Math2D;
import org.duncan.Library2D.Point;


/**
 *
 * @author Oliver & Simon
 * @author Duncan
 */
public class Karta extends Entity {

    private ArrayList<Collidable> result = new ArrayList<Collidable>();
    private Collidable lastCollision = null;

    public Karta(String ref, int x, int y) {
        //Låt Entity hantera konstruktorn.
	super (ref, x, y);
    }


    
      public void generateWorld(int width, int height) {
          // Töm result-listan
          result.clear();

          // Slumpa fram hur många landskapskedjor som skall genereras.
          int chaincount = (int)(Math.random() * 10 + 5);

          // Skapa en ny platform.
          PlatformContainer c = new PlatformContainer();


          // Gå igenom alla kedjor.
            for (int i = 0; i < chaincount; i++){

                // Skapa ett origin för den nya kedjan
                int ox = (int)(Math.random() * width );
                int oy = (int)(Math.random() * height);
                
                // Slumpa fram hur många bitar kedjan skall ha.
                int chainparts = (int)(Math.random() * 10 + 5);

                // Gå igenom alla bitar.
                for (int j = 0; j < chainparts; j++){
                    // Slumpa fram en slutpunkt för kedjebiten.
                    double v = Math.PI * 2 * Math.random();
                    int nx = ox + (int)(Math2D.lengthDir(64, v)).getX();
                    int ny = oy + (int) (Math.random() * 48d - 24); //+ (int)(Math2D.lengthDir(64, v)).getY();

                    // Skapa kedjebiten och lägg till den.
                    c.add(new PlatformNode(new Point(ox, oy), new Point(nx, ny)));

                    // Slutpunkten blir den nya startpunkten.
                    ox = nx;
                    oy = ny;
                }
            }

          c.add(new PlatformNode(new Point(0, Game.GAME_HEIGHT), new Point(Game.GAME_WIDTH, Game.GAME_HEIGHT)));
          c.add(new PlatformNode(new Point(0, 0), new Point(0, Game.GAME_HEIGHT)));
          c.add(new PlatformNode(new Point(Game.GAME_WIDTH, 0), new Point(Game.GAME_WIDTH, Game.GAME_HEIGHT)));
          // Lägg till platformen till resultatet.
          result.add(c.toEntity());
      }

    @Override
    public void collidedWith(Entity other) {
    }

    @Override
    public boolean collidesWith(Collidable other)
    {
        // Gå igenom allt som finns i världen
        for (Collidable c : result) {
            // Ifall den kolliderar med other,
            if (c.collidesWith(other))
            {
                lastCollision = c;
                // returnera true.
                return true;
            }
        }
        // Ingen kollision skedde, returnera false.
        return false;
    }

    public Collidable getLastCollision()
    {
        return lastCollision;
    }

    @Override
    public void draw(Graphics g) {
        // Gå igenom allt som finns i världen
        for (Collidable c : result) {
            // Ifall saken är en Entity
            if (c instanceof Entity)
                // Måla ut den.
                ((Entity) c).draw(g);
            // Någonting finns i världen som inte stöds. Meddela användaren.
            else throw new Error("Denna Collidable-typ stöds inte av Karta.java.");
        }
    }
}

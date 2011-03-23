
package bullethell.obstacle;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.character.Character;

/**
 * @author Kenth Gunnarsson
 */
public class ObstacleEntity extends Entity {

    // A heap of variables that are useful for the class.

    private Game game = Game.getInstance();
    private double moveSpeed = 200;
    private boolean activated = false;
    private double area = 20;


    /*
     * The Constructor...
     * Here the obstacle is given it's appearance and in which speed it should
     * move in, both x and y.
     */
    public ObstacleEntity(int x,int y) {
        super("sprites/Obstacle_1.png",x,y,true);
		dx = 50;
                dy = 50;
    }

/*
 * Checks the characters position whenever it moves and if the character position is
 * within the "hot zone" the doLogic-method will run; if the character tries to move
 * away the obstacle will still run the doLogic-method.
*/
 public void move(long delta)
        {
            if (activated == true)
            {
                doLogic();
                super.move(delta);
            }
            if(game.getCharacter().getX() > this.x - area)
            {
                activated = true;
                doLogic();
                super.move(delta);
            }
            if (y > Game.getInstance().getHeight()){
                y = Game.getInstance().getHeight() - Game.getInstance().getHeight() - 50;
                x = Game.GAME_WIDTH / (4 * Math.random());
            }
        }

 /*
  * Sets the speed the obstacle moves in (y).
  */

	public void doLogic()
        {
            dy = moveSpeed+Math.random()*100;
        }

/*
 * If the obstacle collides with the character, the character will lose one (1)
 * health due to the hurt-method.
 */

    public void collidedWith(Entity other) {
            if (other instanceof Character) {
                ((Character)other).hurt();
            }
	}



}

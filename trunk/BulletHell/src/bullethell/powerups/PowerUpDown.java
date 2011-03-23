package bullethell.powerups;

import bullethell.game.Entity;
import bullethell.character.CharReimu;
import bullethell.game.Game;
/**
 *
 * @author Kantee
 */
public class PowerUpDown extends Entity{
    private final int life;
    private final int addSpeed;

    public PowerUpDown(int x, int y, int life, int addSpeed) {
        super("sprites/Powerup.gif", x, y, false);
        this.addSpeed = addSpeed;
        this.life = life;
    }

    public void powerUp(CharReimu character) {
            character.addLives(life);
            character.setMaximalSpeed(addSpeed);

    }

    @Override
    public void collidedWith(Entity other) {
       if (other instanceof CharReimu){
           powerUp((CharReimu) other);
          Game.getInstance().removeEntity(this);
        }
    }

}
package bullethell.game.bulletpatterns;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;

/**
 * An entity representing a shot fired by the player's ship
 * 
 * @author Daniel
 */
public class ReimuBullet extends Entity {
	/** The vertical speed at which the players shot moves */
	private double moveSpeed;
	/** The game in which this entity exists */
	private Game game = Game.getInstance();
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;
	
	/**
	 * Create a new shot from the player
	 * 
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
         * @param direction The direction of the shot
         * @param speed The speed of the shot
	 */
	public ReimuBullet(String sprite, int x,int y, int direction, int speed) {
            super(sprite,(x - (SpriteStore.get().getSprite("sprites/reimuShot1.jpg").getWidth() / 2)),y);
            moveSpeed = speed;
            dy = moveSpeed;
            dx = Math.sin(Math.toRadians(-direction)) * moveSpeed;
	}

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);
		// if we shot off the screen or if the bullet has hit something, remove ourselfs
		if (y < (game.getHeight() - game.getHeight()) || used) {
			game.removeEntity(this);
		}
	}

        public void used(){
            used = true;
        }
	
	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
            /**
             * Enemy collision code here
             */
	}
}
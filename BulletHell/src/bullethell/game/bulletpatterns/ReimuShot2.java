package bullethell.game.bulletpatterns;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;

/**
 * An entity representing a shot fired by the player's ship
 *
 * @author Daniel
 */
public class ReimuShot2 extends Entity {
	/** The vertical speed at which the players shot moves */
	private double moveSpeed = -800;
	/** The game in which this entity exists */
	private Game game = Game.getInstance();
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;

	/**
	 * Create a new shot from the player
	 *
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 */
	public ReimuShot2(int x,int y) {
            super("sprites/reimuShot1.jpg",(x - (SpriteStore.get().getSprite("sprites/reimuShot1.jpg").getWidth() / 2)),y);
            dy = moveSpeed;
	}

	/**
	 * Request that this shot moved based on time elapsed
	 *
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);

		// if we shot off the screen, remove ourselfs
		if (y < (game.getInstance().getHeight() - game.getInstance().getHeight())) {
			game.removeEntity(this);
		}
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
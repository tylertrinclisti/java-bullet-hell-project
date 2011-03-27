package bullethell.game.bulletpatterns;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;

/**
 * An entity representing a shot fired by the player's ship
 *
 * @author Daniel
 */
public class CharHitBox extends Entity {
	/** The vertical speed at which the players shot moves */
	private double moveSpeed = 1;
	/** The game in which this entity exists */
	private Game game = Game.getInstance();
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;

	/**
	 * Create a new hitbox for the player
	 *
	 */
	public CharHitBox() {
            super("sprites/charHitBoxempty.png",
                 (Game.getInstance().getCharacter().getX() + (Game.getInstance().getCharacter().getSprite().getWidth() / 2) - 4),
                 (Game.getInstance().getCharacter().getY() + (Game.getInstance().getCharacter().getSprite().getHeight() / 2) - 1));
	}

	/**
	 * Request that this shot moved based on time elapsed
	 *
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);
                x = Game.getInstance().getCharacter().getX() + (Game.getInstance().getCharacter().getSprite().getWidth() / 2) - 4;
                y = Game.getInstance().getCharacter().getY() + (Game.getInstance().getCharacter().getSprite().getHeight() / 2) - 1;
                if(game.getKeyPressed(5)){
                    sprite = SpriteStore.get().getSprite("sprites/CharHitBox.png");
                }else{
                    sprite = SpriteStore.get().getSprite("sprites/CharHitBoxempty.png");
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
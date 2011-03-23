package bullethell.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Image;

/**
 * An entity represents any element that appears in the game. The
 * entity is responsible for resolving collisions and movement
 * based on a set of properties defined either by subclass or externally.
 * 
 * Note that doubles are used for positions. This may seem strange
 * given that pixels locations are integers. However, using double means
 * that an entity can move a partial pixel. It doesn't of course mean that
 * they will be display half way through a pixel but allows us not lose
 * accuracy as we move.
 * 
 * @author Kevin Glass
 */
public abstract class Entity implements Collidable{
	/** The current x location of this entity */ 
	protected double x;
	/** The current y location of this entity */
	protected double y;
	/** The sprite that represents this entity */
	protected Sprite sprite;
	/** The current speed of this entity horizontally (pixels/sec) */
	protected double dx;
	/** The current speed of this entity vertically (pixels/sec) */
	protected double dy;
	/** The rectangle used for this entity during collisions  resolution */
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();

	// Whether or not this should be affected by gravity. Default off.
	private boolean hasGravity = false;

	// Thou shall not change this.
	private long lastdelta;

	/**
	 * Empty constructor. Places the entity at 0, 0 without a sprite.
	 */
	public Entity()
	{
		sprite = null;
		x = 0;
		y = 0;
	}

	/**
	 * Construct a entity based on a sprite image and a location.
	 * 
	 * @param ref The reference to the image to be displayed for this entity
 	 * @param x The initial x location of this entity
	 * @param y The initial y location of this entity
	 */
	public Entity(String ref,int x,int y) {
		if (ref != null)
			this.sprite = SpriteStore.get().getSprite(ref);

		this.x = x;
		this.y = y;
	}

	/**
	 * Construct a entity based on a sprite image and a location.
	 *
	 * @param ref The reference to the image to be displayed for this entity
 	 * @param x The initial x location of this entity
	 * @param y The initial y location of this entity
	 * @param hasGravity Whether the object should have gravity.
	 */
	public Entity(String ref,int x,int y, boolean hasGravity) {
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
		this.hasGravity = hasGravity;
	}
	
	/**
	 * Request that this entity move itself based on a certain ammount
	 * of time passing.
	 * 
	 * @param delta The ammount of time that has passed in milliseconds
	 */
	public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;

		lastdelta = delta;
	}
	
	/**
	 * Set the horizontal speed of this entity
	 * 
	 * @param dx The horizontal speed of this entity (pixels/sec)
	 */
	public void setHorizontalMovement(double dx) {
		this.dx = dx;
	}

	/**
	 * Set the vertical speed of this entity
	 * 
	 * @param dy The vertical speed of this entity (pixels/sec)
	 */
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}
	
	/**
	 * Get the horizontal speed of this entity
	 * 
	 * @return The horizontal speed of this entity (pixels/sec)
	 */
	public double getHorizontalMovement() {
		return dx;
	}

	/**
	 * Get the vertical speed of this entity
	 * 
	 * @return The vertical speed of this entity (pixels/sec)
	 */
	public double getVerticalMovement() {
		return dy;
	}
	
	/**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		if (getSprite() != null)
			getSprite().draw(g,(int) x,(int) y);
	}
	
	/**
	 * Do the logic associated with this entity. This method
	 * will be called periodically based on game events
	 * @deprecated Use move(delta) instead.
	 */
	public void doLogic() {
	}

	/**
	 * Mirrors the image (provided that ref is the mirrored image)
	 * @param ref The image to load.
	 * @deprecated Use setImage(String ref) instead
	 */
	public void mirror(String ref) {
			setImage(ref);
	}

	/**
	 * Sets the sprite to the new ref.
	 * @param ref The new image
	 */
	public void setImage(String ref)
	{
		this.sprite = SpriteStore.get().getSprite(ref);
	}
        
	/**
	 * Get the x location of this entity
	 * 
	 * @return The x location of this entity
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Get the y location of this entity
	 * 
	 * @return The y location of this entity
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * Sets this entities X.
	 * @param x The x
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Sets this entities Y.
	 * @param y The y
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * Check if this entity collised with another.
	 * 
	 * @param other The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(Collidable other)
	{
		if (other == null)
			return false;

		if (sprite == null)
			return false;
		
		if (other instanceof Entity)
		{
			Entity o = (Entity) other;

			if (o.getSprite() == null)
				return false;

			me.setBounds((int) x,(int) y,getSprite().getWidth(),getSprite().getHeight());
			him.setBounds((int) o.x,(int) o.y, o.getSprite().getWidth(),o.getSprite().getHeight());

			return me.intersects(him);
		}
		else
		{
			return other.collidesWith(this);
		}
	}
	
	/**
	 * Notification that this entity collided with another.
	 * 
	 * @param other The entity with which this entity collided.
	 */
	public void collidedWith(Entity other) {}

	/**
	 * @return the lastdelta
	 */
	public long getLastDelta() {
		return lastdelta;
	}

	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

}
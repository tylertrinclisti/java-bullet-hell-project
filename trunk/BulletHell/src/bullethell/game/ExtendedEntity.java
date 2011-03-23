/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.game;

/**
 *
 * @author patrik
 */
public abstract class ExtendedEntity extends Entity
{

	public ExtendedEntity(String ref, int x, int y)
	{
		this(ref, x, y, false);
	}

	public ExtendedEntity(String ref, int x, int y, boolean hasGravity) {
		super(ref, x, y, hasGravity);
	}


	@Override
	public abstract void collidedWith(Entity other);

	/**
	 * Callced when any enemy is killed.
	 * @param killed The entity that was killed (can be null if people use the old method for notifying death)
	 */
	public abstract void notifyEnemyKilled(Entity killed);
}

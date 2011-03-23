/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.game;

/**
 *
 * @author patrik
 */
public class PlaceholderPlatform extends Entity
{
	public PlaceholderPlatform(int x, int y)
	{
		super("sprites/placeplatform.png", x, y);
	}

	@Override
	public void collidedWith(Entity other) {

	}

}

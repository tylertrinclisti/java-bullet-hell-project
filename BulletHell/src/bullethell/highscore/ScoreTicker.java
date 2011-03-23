/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.highscore;

import bullethell.game.Collidable;
import bullethell.game.Entity;
import bullethell.game.Game;

/**
 *
 * @author patrik
 */
public class ScoreTicker extends Entity
{
	private final static long SCORE_TIME = 1000;

	long added;

	@Override
	public void move(long delta)
	{
		while (Game.getInstance().getGameTime() > added + SCORE_TIME)
		{
			added += SCORE_TIME;
			Game.getInstance().getScore().addScore(1);
		}
	}
}

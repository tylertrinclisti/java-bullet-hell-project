/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.game.internalgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;
import bullethell.game.Game;
import bullethell.game.GuiItem;

/**
 *
 * @author patrik
 */
public class LifeGui extends GuiItem
{
	@Override
	public void draw(Graphics2D g)
	{
		if (Game.getInstance().isCheater())
			g.setColor(Color.BLUE);
		else
			g.setColor(Color.RED);

		Font oldfont = g.getFont();
		g.setFont(oldfont.deriveFont((float) oldfont.getSize() * 3.0f));

		char[] chr;
		if (Game.getInstance().getCharacter().getLives() >= 0)
		{
			chr = new char[Game.getInstance().getCharacter().getLives()];
		
			char symbol = '♥';

			for (int i = 0; i < chr.length; i++)
			{
				if (Game.getInstance().isCheater())
				{
					symbol = (char) (new Random(i).nextInt(999 - 32) + 32);
				}
				chr[i] = symbol;
			}
		}
		else
		{
			chr = new char[] {'D', 'E', 'A', 'D'};
		}

		g.drawString(new String(chr), 50, Game.GAME_HEIGHT - g.getFontMetrics().getHeight());

		g.setFont(oldfont);
	}

}

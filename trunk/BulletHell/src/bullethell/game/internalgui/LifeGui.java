package bullethell.game.internalgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import bullethell.game.Game;
import bullethell.game.GuiItem;

/**
 *
 * @author patrik & Daniel
 */
public class LifeGui extends GuiItem
{
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.RED);

		Font oldfont = g.getFont();
		g.setFont(oldfont.deriveFont((float) oldfont.getSize()));

		char[] chr;
		if (Game.getInstance().getCharacter().getLives() >= 0)
		{
			chr = new char[Game.getInstance().getCharacter().getLives()];
		
			char symbol = '♥';

			for (int i = 0; i < chr.length; i++)
			{
				chr[i] = symbol;
			}
		}
		else
		{
			chr = new char[] {'D', 'E', 'A', 'D'};
		}

		g.drawString(new String(chr), Game.GAME_WIDTH / 15, Game.GAME_HEIGHT - (g.getFontMetrics().getHeight() * 3));

		g.setFont(oldfont);
	}

}

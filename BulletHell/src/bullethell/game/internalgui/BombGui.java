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
public class BombGui extends GuiItem
{
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.GREEN);

		Font oldfont = g.getFont();
		g.setFont(oldfont.deriveFont((float) oldfont.getSize() * 3.0f));

		char[] chr;
		if (Game.getInstance().getCharacter().getBombs() >= 0)
		{
			chr = new char[Game.getInstance().getCharacter().getBombs()];

			char symbol = '♠';

			for (int i = 0; i < chr.length; i++)
			{
				chr[i] = symbol;
			}
		}
		else
		{
			chr = new char[] {'N', 'O', 'B', 'O', 'M', 'B', 'S'};
		}

		g.drawString(new String(chr), Game.GAME_WIDTH / 15, Game.GAME_HEIGHT - (g.getFontMetrics().getHeight() * 2));

		g.setFont(oldfont);
	}

}
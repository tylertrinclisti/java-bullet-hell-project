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
public class PowerGui extends GuiItem
{
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.WHITE);

		Font oldfont = g.getFont();
		g.setFont(oldfont.deriveFont((float) oldfont.getSize()));

		char[] chr;
		if (Game.getInstance().getCharacter().getPower() < 40)
		{
                        String first = Integer.toString((Game.getInstance().getCharacter().getPower() / 10));
                        String last = Integer.toString((Game.getInstance().getCharacter().getPower() - ((Integer.parseInt(first) * 10))));
			chr = new char[] {'P', 'o', 'w', 'e', 'r', ':', ' ', new Character(first.charAt(0)), '.', new Character(last.charAt(0))};
		}
		else
		{
			chr = new char[] {'P', 'o', 'w', 'e', 'r', ':', ' ', 'M', 'A', 'X'};
		}

		g.drawString(new String(chr), Game.GAME_WIDTH - Game.GAME_WIDTH + (g.getFontMetrics().getHeight()), Game.GAME_HEIGHT - g.getFontMetrics().getHeight());

		g.setFont(oldfont);
	}

}

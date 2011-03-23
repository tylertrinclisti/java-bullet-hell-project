/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.game;

import java.awt.Graphics2D;

/**
 * Denna klassen skapades för att göra det enklare att rita ut saker på skärmen.
 * Vissa saker var hårdkodade in i Game och andra använde Entity, båda väldigt opraktiska för sitt syfte
 * @author patrik
 */
public abstract class GuiItem
{
	public abstract void draw(Graphics2D g);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.highscore;

import java.awt.Color;
import java.awt.Graphics2D;
import bullethell.game.GuiItem;

public class ScoreEntity extends GuiItem{
    private final Score Score;
    public ScoreEntity(Score Score){
    this.Score=Score;
    }
    public void draw(Graphics2D g){

        g.setColor(Color.LIGHT_GRAY);
    }
}

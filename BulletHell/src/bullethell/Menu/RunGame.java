/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.Menu;

import javax.swing.JOptionPane;
import bullethell.game.Game;
import bullethell.highscore.Score;

/**
 *
 * @author Scymoon
 */
public class RunGame implements  Runnable {
    String name = null;
    int Height = 800;
    int Width = 1000;
    public void run() {
            name = JOptionPane.showInputDialog("Whats your name");
            Height = Integer.parseInt(JOptionPane.showInputDialog("Height?"));
            Width = Integer.parseInt(JOptionPane.showInputDialog("Width?"));
            try {
                Game g = new Game(Width,Height);
                Game.getInstance().getScore().setNamn(name);


                // Start the main game loop, note: this method will not
                // return until the game has finished running. Hence we are
                // using the actual main thread to run the game.
                g.gameLoop();
            } catch (Exception ex) {
                System.err.println("*** THE CRASH ***");
                ex.printStackTrace();
            }

    }

}

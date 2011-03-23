package bullethell.Menu;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import bullethell.game.Game;

public class Menu extends JFrame implements ActionListener {
    /*
     * @param s
     * @param b
     * @Author  Rami Jabor
     * @TODO
     * Fixa designen, Fixa 2 till menys för Options och Cheats.
     * Kunna ange spelarinfo efter New Game
     * Avsluta processen vid Exit.
     */

    private JPanel pan = new JPanel();
    private JButton[] b = new JButton[5];
    private String[] s = {"New game", "Pause/Continue", "Options", "Highscore", "Exit"};
    private Thread runGame = new Thread(new RunGame());

    public Menu() {
        /**
         * @param lolol
         */
        setTitle("Meny");
        pan.setLayout(new FlowLayout());
        for (int i = 0; i < b.length; i++) { //skapar alla knappar på panelen
            b[i] = new JButton();
            b[i].setText(s[i]);
            b[i].addActionListener(this);
            pan.add(b[i]);
        }
        Container c = getContentPane();
        c.add(pan, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == b[0]) { //newGame
           runGame.start();


        } else if (e.getSource() == b[1]) { //pause/play
            //Game.getInstance().....
        } else if (e.getSource() == b[2]) { //options
            //Options(); //Ska skapa en ny submeny med knappar( och kanske mätare)?

        } else if (e.getSource() == b[3]) {//highscore
            //Highscore(); //Ska skapa ny panel med Highscores Highscore
            Highscore score = new Highscore();
            score.setVisible(true);

        } else if (e.getSource() == b[4]) {
            System.exit(0); // Måste även kunna avsluta processen och spelfönstret
        }
    }

    public static void main(String[] arg) {
        Menu s = new Menu();
    }
}

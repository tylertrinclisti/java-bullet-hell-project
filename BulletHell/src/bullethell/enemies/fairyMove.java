/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.enemies;

/**
 *
 * @author Jollepoker
 */
public class fairyMove {

    /**
     * Returns the starting position of the fairy based on the movePattern chosed
     * for either X or Y
     * @param x True if to send X, false if to send Y
     * @param movePattern Number of the movePattern the fairy will use
     * movePatterns:
     * 1 =
     * 2 = 
     * 3 =
     * 4 =
     * 5 =
     * @return The start cordinate for the fairy for either X or Y in a double.
     */
    public static int getStartPos (boolean x, int movePattern){
        if(movePattern == 1){
            if(x){
                return 200;
            }else{
                return 100;
            }
        }else if(movePattern == 2){
            if(x){
                return 200;
            }else{
                return 100;
            }
        }else if(movePattern == 3){
            if(x){
                return 200;
            }else{
                return 100;
            }
        }else if(movePattern == 4){
            if(x){
                return 200;
            }else{
                return 100;
            }
        }else if(movePattern == 5){
            if(x){
                return 200;
            }else{
                return 100;
            }
        }
        return 0;
    }

    /**
     * Returns the speed the fairy will have on either the vertical or horizontal
     * plane based on the movePattern choosed.
     * @param dx True if to send X, false if to send Y
     * @param movePattern Number of the movePattern the fairy will use
     * movePatterns:
     * 1 =
     * 2 =
     * 3 =
     * 4 =
     * 5 =
     * @param startTime The time the pattern started
     * @return The speed for the fairy for either X or Y in a int.
     */
    public static int getMove (boolean dx, int movePattern, long startTime){
        if(movePattern == 1){
            if(dx){
                return 0;
            }else{
                return 0;
            }
        }else if(movePattern == 2){
            if(dx){
                return 0;
            }else{
                return 0;
            }
        }else if(movePattern == 3){
            if(dx){
                return 0;
            }else{
                return 0;
            }
        }else if(movePattern == 4){
            if(dx){
                return 0;
            }else{
                return 0;
            }
        }else if(movePattern == 5){
            if(dx){
                return 0;
            }else{
                return 0;
            }
        }
        return 0;
    }
}

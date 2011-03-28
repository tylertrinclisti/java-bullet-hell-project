package bullethell.enemies;
import bullethell.game.Game;
import bullethell.game.SpriteStore;

/**
 *
 * @author Daniel
 */
public class fairyMove {

    /**
     * Returns the starting position of the fairy based on the movePattern chosed
     * for either X or Y
     * @param x True if to send X, false if to send Y
     * @param movePattern Number of the movePattern the fairy will use
     * movePatterns:
     * 1 = Start from the top stop at 1/8 of the screen from the top and shot bullets
     * in all directions once, then continue down the screen.
     * 2 = 
     * 3 =
     * 4 =
     * 5 =
     * @param side Which side of the screen the pattern should start at, it is
     * not always relevant.
     * side:
     * true = left
     * false = right
     * @return The start cordinate for the fairy for either X or Y in a int.
     * If movePattern is a number not specified in getStartPos return will be 0.
     */
    public static int getStartPos (boolean x, int movePattern, boolean side){
        if(movePattern == 1){
            if(x){
                int value = (int) (Game.getInstance().getWidth() * Math.random());
                if(value < SpriteStore.get().getSprite("sprites/fairyG_1.png").getWidth() * 2){
                    value = SpriteStore.get().getSprite("sprites/fairyG_1.png").getWidth() * 2;
                }
                System.out.println(value);
                return value;
            }else{
                System.out.println(-SpriteStore.get().getSprite("sprites/fairyG_1.png").getHeight());
                return -SpriteStore.get().getSprite("sprites/fairyG_1.png").getHeight();
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
     * @param side Which side of the screen the pattern started at, it is
     * not always relevant.
     * @return The speed for the fairy for either X or Y in a int.
     * If movePattern is a number not specified in getStartPos return will be 0.
     */
    public static int getMove (boolean dx, int movePattern, long startTime, boolean side){
        if(movePattern == 1){
            if(dx){
                return 0;
            }else{
                return 100;
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

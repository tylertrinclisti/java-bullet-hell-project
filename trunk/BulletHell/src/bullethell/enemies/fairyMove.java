package bullethell.enemies;
import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;
import bullethell.game.bulletpatterns.*;

/**
 *
 * @author Daniel
 */
public class fairyMove {

    private long waitTime = 0L;
    private boolean done = false;
    private fairyBulletPattern bulletPattern = new fairyBulletPattern();

    /**
     * Returns the starting position of the fairy based on the movePattern chosed
     * for either X or Y
     * @param x True if to send X, false if to send Y
     * @param movePattern Number of the movePattern the fairy will use
     * movePatterns:
     * 1 = From top, stop near top, release 360 degree 20 bullets, continue down screen.
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
    public int getStartPos (boolean x, int movePattern, boolean side){
        if(movePattern == 1){
            if(x){
                int value = (int) (Game.getInstance().getWidth() * Math.random());
                if(value < SpriteStore.get().getSprite("sprites/fairyG_1.png").getWidth() * 2){
                    value = SpriteStore.get().getSprite("sprites/fairyG_1.png").getWidth() * 2;
                }else if(value > Game.getInstance().getWidth() - (SpriteStore.get().getSprite("sprites/fairyG_1.png").getWidth() * 2)){
                    value = Game.getInstance().getWidth() - (SpriteStore.get().getSprite("sprites/fairyG_1.png").getWidth() * 2);
                }
                return value;
            }else{
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
     * 1 = From top, stop near top, release 360 degree 20 bullets, continue down screen.
     * 2 =
     * 3 =
     * 4 =
     * 5 =
     * @param bulletPattern Number of the bulletPattern the fairy will use,
     * more info in fairyBulletPattern.java
     * @param startTime The time the pattern started, it is not always relevant.
     * @param side Which side of the screen the pattern started at, it is
     * not always relevant.
     * @param x the current X the fairy is position at.
     * @param y the current Y the fairy is position at.
     * @param color Fairy colors: 1=Green 2=Blue 3=Yelow 4=Red
     * @param bullets The number of bullets the fairy will shoot.
     * @param bulletSpeed The speed the bullets will travel in.
     * @param direction The direction the bullet will travel in, in degrees (from 0 to x), 0 is top
     * @return The speed for the fairy for either X or Y in a int.
     * If movePattern is a number not specified in getStartPos return will be 0.
     */
    public int getMove (boolean dx, int movePattern, int bulletPattern, long startTime, boolean side, int x, int y, int color, int bullets, int bulletSpeed, int direction){
        if(movePattern == 1){
            if(dx){
                return 0;
            }else{
                if(y < Game.getInstance().getHeight() / 8){
                    if((Game.getInstance().getHeight() / 7) - y > 100){
                        return 100;
                    }else{
                        return (Game.getInstance().getHeight() / 7) - (y / 2);
                    }
                }else if(y < Game.getInstance().getHeight() / 7 && y > (Game.getInstance().getHeight() / 7) - SpriteStore.get().getSprite("sprites/fairyG_1.png").getHeight() && !done){
                    if (waitTime == 0L){
                        waitTime = Game.getInstance().getGameTime();
                    }
                    if (waitTime < Game.getInstance().getGameTime() - 1000){
                        /** Don't send out bullets if the player just lost a life */
                        if(Game.getInstance().getGameTime() > Game.getInstance().getCharacter().getInvincibility() - 2000){
                            this.bulletPattern.BulletPattern(bulletPattern, x, y, side, bullets, color, bulletSpeed, direction);
                        }
                        done = true;
                    }else{
                        return 0;
                    }
                }else if(done){
                    return 100;
                }
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

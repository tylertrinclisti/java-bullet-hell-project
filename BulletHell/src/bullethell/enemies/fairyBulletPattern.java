package bullethell.enemies;
import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;
import bullethell.game.bulletpatterns.*;

/**
 *
 * @author Daniel
 */
public class fairyBulletPattern {

    private long waitTime = 0L;
    private boolean done = false;

    /**
     * Spawns bullets in a specific pattern at the fairys location.
     * @param bulletPattern Number of the bulletPattern the fairy will use
     * bulletPatterns:
     * 1 = Releases bullets in a round circle.
     * 2 =
     * 3 =
     * 4 =
     * 5 =
     * @param x The X cordinate of the fairy
     * @param y The Y cordinate of the fairy
     * @param side Which side of the screen the pattern should start at, it is
     * not always relevant.
     * side:
     * true = left
     * false = right
     * @param bullets The number of bullets that should be used.
     * @param color Fairy colors: 1=Green 2=Blue 3=Yelow 4=Red
     * @param bulletSpeed The speed the bullets will use.
     * @param direction The direction the bullet will travel in, in degrees (from 0 to x), 0 is top.
     * 360 means that the bullets will go all around the fairy
     * 180 means that
     * @return The start cordinate for the fairy for either X or Y in a int.
     * If movePattern is a number not specified in getStartPos return will be 0.
     */
    public int BulletPattern (int bulletPattern, int x, int y, boolean side, int bullets, int color, int bulletSpeed, int direction){
        if(bulletPattern == 1){
            String bulletSprite = "sprites/fairyGBullet_1.png";
            if(color == 2){
                bulletSprite = "sprites/fairyBBullet_1.png";
            }else if(color == 3){
                bulletSprite = "sprites/fairyYBullet_1.png";
            }else if(color == 4){
                bulletSprite = "sprites/fairyRBullet_1,png";
            }
            double dxValue;
            double dyValue;
            for(int i = 0; i < bullets; i++){
                if(!side){
                    dxValue = bulletSpeed * Math.cos(Math.toRadians(((90 / bullets) + (direction / bullets)) * i));
                    dyValue = bulletSpeed * Math.sin(Math.toRadians(((90 / bullets) + (direction / bullets)) * i));
                }else{
                    dxValue = bulletSpeed * Math.cos(Math.toRadians(((90 / bullets) - (direction / bullets)) * i));
                    dyValue = bulletSpeed * Math.sin(Math.toRadians(((90 / bullets) - (direction / bullets)) * i));
                }
                Game.getInstance().addEntity(new FairyBullet1(bulletSprite, x, y, dxValue, dyValue));
            }
        }else if(bulletPattern == 2){

        }else if(bulletPattern == 3){

        }else if(bulletPattern == 4){

        }else if(bulletPattern == 5){

        }
        return 0;
    }
}

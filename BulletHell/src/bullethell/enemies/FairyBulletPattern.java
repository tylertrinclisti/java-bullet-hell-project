package bullethell.enemies;
import bullethell.game.AePlayWave;
import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;
import bullethell.game.bulletpatterns.*;
import org.duncan.Library2D.Math2D;
import org.duncan.Library2D.Point;

/**
 *
 * @author Daniel
 */
public class FairyBulletPattern {

    private long waitTime = 0L;
    private boolean done = false;

    /**
     * Spawns bullets in a specific pattern at the fairys location.
     * @param bulletPattern Number of the bulletPattern the fairy will use
     * bulletPatterns:
     * 1 = Releases bullets in a round circle.
     * 2 = Releases a single bullet down the screen
     * 3 = Releases a single bullet towards the player
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
     * @param direction The direction the bullet will travel in, in degrees (from 0 to x), 0 is right.
     * 360 means that the bullets will go all around the fairy
     * 180 means that the bullets will go all around the bottom of the fairy from right to left
     * -180 means that the bullets will go all around the top of the fairy from right to left
     * @return The start cordinate for the fairy for either X or Y in a int.
     * If movePattern is a number not specified in getStartPos return will be 0.
     */
    public int BulletPattern (int bulletPattern, int x, int y, boolean side, int bullets, int color, int bulletSpeed, double direction){
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
                dxValue = bulletSpeed * Math.cos(Math.toRadians((double) (direction / bullets) * i));
                dyValue = bulletSpeed * Math.sin(Math.toRadians((double) (direction / bullets) * i));
                Game.getInstance().addEntity(new FairyBullet1(bulletSprite, x + (SpriteStore.get().getSprite(bulletSprite).getWidth() / 3), y, dxValue, dyValue));
            }
            new AePlayWave(Game.getInstance().getPath() + "/src/sounds/se_option.wav").start();
        }else if(bulletPattern == 2){
            String bulletSprite = "sprites/fairyGBullet_1.png";
            if(color == 2){
                bulletSprite = "sprites/fairyBBullet_1.png";
            }else if(color == 3){
                bulletSprite = "sprites/fairyYBullet_1.png";
            }else if(color == 4){
                bulletSprite = "sprites/fairyRBullet_1,png";
            }
            Game.getInstance().addEntity(new FairyBullet1(bulletSprite, x + (SpriteStore.get().getSprite(bulletSprite).getWidth() / 3), y, 0, bulletSpeed));
            new AePlayWave(Game.getInstance().getPath() + "/src/sounds/se_kira00.wav").start();
        }else if(bulletPattern == 3){
            String bulletSprite = "sprites/fairyGBullet_1.png";
            if(color == 2){
                bulletSprite = "sprites/fairyBBullet_1.png";
            }else if(color == 3){
                bulletSprite = "sprites/fairyYBullet_1.png";
            }else if(color == 4){
                bulletSprite = "sprites/fairyRBullet_1,png";
            }
            //Emils library är awesome! Kommer använda det mycket mer efter att jag nu upptäckt hur bra det är! :D
            direction = Math2D.direction(new Point(x, y), new Point(Game.getInstance().getHitBox().getX(), Game.getInstance().getHitBox().getY()));
            double dxValue = bulletSpeed * Math.cos((double) direction);
            double dyValue = bulletSpeed * Math.sin((double) direction);
            Game.getInstance().addEntity(new FairyBullet1(bulletSprite, x + (SpriteStore.get().getSprite(bulletSprite).getWidth() / 3), y, dxValue, dyValue));
            new AePlayWave(Game.getInstance().getPath() + "/src/sounds/se_kira00.wav").start();
        }else if(bulletPattern == 4){

        }else if(bulletPattern == 5){

        }
        return 0;
    }
}
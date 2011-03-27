package bullethell.enemies;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;

/**
 *
 * @author Jollepoker
 */
public class GreenFairy1 extends Entity {
    private double moveSpeed = 0;
    private Game game = Game.getInstance();
    private boolean alive = true;
    private long currentTime = 0;
    private long nextSprite = 0L;
    /** Keeps track of which way the sprites will change */
    private boolean up = false;

    /**
     * Creates new fairy
     *
     * @param x Where the fairy spawns on the horizontal plane
     * @param y Where the fairy spawns on the vertical plane
     * @param right If the fairy will start from the right or left
     */
    public GreenFairy1(int x, int y, boolean right) {
        super("sprites/fairyG_1.png",x,y, true);
        if(!right){
            dx = moveSpeed;
        }else{
            dx = -moveSpeed;
        }
    }

    public void move(long delta) {
        // swap over horizontal movement
        super.move(delta);
        if(Game.getInstance().getGameTime() > nextSprite){
            nextSprite = Game.getInstance().getGameTime() + 80;
            if(this.sprite == SpriteStore.get().getSprite("sprites/fairyG_1.png")){
                setImage("sprites/fairyG_2.png");
                up = false;
            }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyG_2.png")){
                if(!up){
                    setImage("sprites/fairyG_3.png");
                }else{
                    setImage("sprites/fairyG_1.png");
                }
            }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyG_3.png")){
                if(!up){
                    setImage("sprites/fairyG_4.png");
                }else{
                    setImage("sprites/fairyG_3.png");
                }
            }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyG_4.png")){
                setImage("sprites/fairyG_3.png");
                up = true;
            }
        }
    }

    /**
     * Collisionlogic
     *
     * @param other The other entity,
     */
    public void collidedWith(Entity other) {

    }
}
package bullethell.enemies;

import bullethell.character.CharHitBox;
import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;
import bullethell.game.bulletpatterns.ReimuBullet;
import bullethell.powerups.SmallPowerUp;
import bullethell.enemies.FairyMovePattern;
import bullethell.highscore.Score;

/**
 *
 * @author Jollepoker
 */
public class Fairy1 extends Entity {
    private Game game = Game.getInstance();
    private long startTime = 0L;
    private long nextSprite = 0L;
    /** The fairys life */
    private int health = 9;
    /** Keeps track of which way the sprites will change */
    private boolean up = false;
    private boolean inside = false;
    /** Color of the fairy, default green */
    private int color = 1;
    /** Move pattern for the fairy. See fairyMove.java */
    private int movePattern = 1;
    /** Bullet pattern for the fairy. See fairyBullet.java */
    private int bulletPattern = 1;
    /** Number of bullets the fairy will shoot out */
    private int bullets = 0;
    /** Direction around the fairy the bullets will take */
    private double direction = 1;
    /** Speed of the bullets the fairy will shoot out */
    private int bulletSpeed = 0;
    /** Which side the fairy will shot and move in */
    private boolean side = false;
    private FairyMovePattern fairyMove = new FairyMovePattern();

    /**
     * Creates new fairy
     *
     * @param x Where the fairy spawns on the horizontal plane
     * @param y Where the fairy spawns on the vertical plane
     * @param right If the fairy will start from the right or left
     */
    public Fairy1(boolean side, int color, int movePattern, int bulletPattern, int bullets, double direction, int bulletSpeed) {
        super("sprites/fairyG_1.png");
        if(color > 4 || color < 1){
            color = 1;
        }
        if(color == 2){
            setImage("sprites/fairyB_1.png");
        }else if(color == 3){
            setImage("sprites/fairyY_1.png");
        }else if(color == 4){
            setImage("sprites/fairyR_1.png");
        }
        this.color = color;
        this.movePattern = movePattern;
        this.bulletPattern = bulletPattern;
        if(bullets < 0){
           this.bullets = 0;
        }else{
            this.bullets = bullets;
        }
        this.direction = direction;
        this.bulletSpeed = bulletSpeed;
        this.side = side;
        this.x = fairyMove.getStartPos(true, movePattern, side);
        this.y = fairyMove.getStartPos(false, movePattern, side);
        startTime = Game.getInstance().getGameTime();
    }

    public void move(long delta) {
        // swap over horizontal movement
        super.move(delta);

        dx = fairyMove.getMove(true, movePattern, bulletPattern, startTime, side, (int) x, (int) y, color, bullets, bulletSpeed, direction);
        dy = fairyMove.getMove(false, movePattern, bulletPattern, startTime, side, (int) x, (int) y, color, bullets, bulletSpeed, direction);

        /**
         * When the fairy is defeated, it will drop a random number of powerups
         */
        if (health <= 0) {
            for(int i = 0; i < 5; i++){
                game.addEntity(new SmallPowerUp((int) x, (int) y));
                if(Math.random() >= 0.3 && i == 0){
                    i = 3;
                }
            }
            game.addScore(1000);
            game.removeEntity(this);
        }

        /**
         * Fairy sprite change
         */
        if(game.getGameTime() > nextSprite){
            nextSprite = game.getGameTime() + 80;
            /**
             * Fairy colors:
             * 1=Green 2=Blue 3=Yelow 4=Red
             */
            if(color == 1){
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
                        setImage("sprites/fairyG_2.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyG_4.png")){
                    setImage("sprites/fairyG_3.png");
                    up = true;
                }
            }else if(color == 2){
                if(this.sprite == SpriteStore.get().getSprite("sprites/fairyB_1.png")){
                    setImage("sprites/fairyB_2.png");
                    up = false;
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyB_2.png")){
                    if(!up){
                        setImage("sprites/fairyB_3.png");
                    }else{
                        setImage("sprites/fairyB_1.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyB_3.png")){
                    if(!up){
                        setImage("sprites/fairyB_4.png");
                    }else{
                        setImage("sprites/fairyB_2.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyB_4.png")){
                    setImage("sprites/fairyB_3.png");
                    up = true;
                }
            }else if(color == 3){
                if(this.sprite == SpriteStore.get().getSprite("sprites/fairyY_1.png")){
                    setImage("sprites/fairyY_2.png");
                    up = false;
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyY_2.png")){
                    if(!up){
                        setImage("sprites/fairyY_3.png");
                    }else{
                        setImage("sprites/fairyY_1.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyY_3.png")){
                    if(!up){
                        setImage("sprites/fairyY_4.png");
                    }else{
                        setImage("sprites/fairyY_2.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyY_4.png")){
                    setImage("sprites/fairyY_3.png");
                    up = true;
                }
            }else if(color == 4){
                if(this.sprite == SpriteStore.get().getSprite("sprites/fairyR_1.png")){
                    setImage("sprites/fairyR_2.png");
                    up = false;
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyR_2.png")){
                    if(!up){
                        setImage("sprites/fairyR_3.png");
                    }else{
                        setImage("sprites/fairyR_1.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyR_3.png")){
                    if(!up){
                        setImage("sprites/fairyR_4.png");
                    }else{
                        setImage("sprites/fairyR_2.png");
                    }
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/fairyR_4.png")){
                    setImage("sprites/fairyR_3.png");
                    up = true;
                }
            }
        }

        /**
         * Checks if the fairy has entered the screen. If it has, remove it
         * if it leaves the screen again
         */
        if((x < game.getWidth() + sprite.getWidth() + 1 && y < game.getHeight() + sprite.getHeight() + 1) && (x >= -sprite.getWidth() - 1 && y >= -sprite.getHeight() - 1) && !inside){
            inside = true;
        }
        if((x > game.getWidth() + sprite.getWidth() + 1 || x <= -sprite.getWidth() - 1 || y > game.getHeight() + sprite.getHeight() + 1 || y <= -sprite.getHeight() - 1) && inside){
            game.removeEntity(this);
        }
    }

    /**
     * Collisionlogic
     *
     * @param other The other entity,
     */
    public void collidedWith(Entity other) {
        /** If the fairy collides with the players hitbox, make the player lose one life */
        if(Game.getInstance().getGameTime() > Game.getInstance().getCharacter().getInvincibility()){
            if (other instanceof CharHitBox) {
                game.getCharacter().loseLife();
            }
        }
        /** If the fairy collides with the player projectile, make the fairy lose health */
        if (other instanceof ReimuBullet) {
            if(inside){
                health -= 1;
                Game.getInstance().addScore(3);
                ((ReimuBullet)other).used();
            }
        }
    }
}
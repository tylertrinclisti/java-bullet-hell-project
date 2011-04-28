package bullethell.powerups;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.character.CharHitBox;
import bullethell.game.AePlayWave;
import org.duncan.Library2D.Math2D;
import org.duncan.Library2D.Point;
/**
 *
 * @author Daniel
 */
public class FullPower extends Entity{

    private int direction;
    private double playerDirection;
    private double moveSpeed = -200;
    private long startTime = 0L;
    /** If this powerup has been collected or not. 0=no 1=close 2=top of screen */
    private int collected = 0;
    private boolean startUp = true;

    public FullPower(int x, int y) {
        super("sprites/FullPower.png", x, y, false);

        dy = moveSpeed + (Math.random() * 40);
        startTime = Game.getInstance().getGameTime();

        /**
         * Poweruppen rör sig efter att ha blivit utmålad på skärmen i en
         * slumpvis vald riktning uppåt på skärmen.
         */
        if(x <= (Game.getInstance().getWidth() / 7)){
            direction = -30;
        }else if (x >= Game.getInstance().getWidth() - (Game.getInstance().getWidth() / 5)){
            direction = 30;
        }else{
            if(Math.random() >= 0.5){
                direction = -30;
            }else{
                direction = 30;
            }
        }
        dx = Math.sin(Math.toRadians((Math.random()) * direction)) * moveSpeed;
    }

    public void move(long delta) {
        super.move(delta);

        /**
         * Om karaktären dör så skall poweruppen inte längre sugas till karaktären.
         */
        if(Game.getInstance().getCharacter().getInvincibility() - Game.getInstance().getGameTime() - 2000 > 0){
            collected = 0;
        }
        if(collected == 2){
            playerDirection = Math2D.direction(new Point(x, y), new Point(Game.getInstance().getHitBox().getX(), Game.getInstance().getHitBox().getY()));
            dx = 1500 * Math.cos((double) playerDirection);
            dy = 1500 * Math.sin((double) playerDirection);
        }else if(collected == 1 && !startUp){
            playerDirection = Math2D.direction(new Point(x, y), new Point(Game.getInstance().getHitBox().getX(), Game.getInstance().getHitBox().getY()));
            dx = 500 * Math.cos((double) playerDirection);
            dy = 500 * Math.sin((double) playerDirection);

            /**
             * Om karaktären har full power och befinner sig 1/5 ner på skärmen från
             * toppen, så kommer denna powerup att sugas till karaktären snabbt istället.
             * för långsamt.
             */
            if(Game.getInstance().getHitBox().getY() <= Game.getInstance().getHeight() / 5 &&
               Game.getInstance().getCharacter().getPower() == 400){
                collected = 2;
            }
        }else{
            /**
             * Om karaktären är i närheten av poweruppen så ska den
             * sugas till karaktären
             */
            if((x - Game.getInstance().getHitBox().getX() <= 40 &&
                x - Game.getInstance().getHitBox().getX() >= -40) &&
               (y - Game.getInstance().getHitBox().getY() <= 40 &&
                y - Game.getInstance().getHitBox().getY() >= -40)){
                collected = 1;
            }

            /**
             * Om karaktären har full power och befinner sig 1/5 ner på skärmen från
             * toppen, så kommer denna powerup att sugas till karaktären.
             */
            if(Game.getInstance().getHitBox().getY() <= Game.getInstance().getHeight() / 5 &&
               Game.getInstance().getCharacter().getPower() == 400){
                collected = 2;
            }

            /**
             * Efter att poweruppen har åkt ifrån sin ursprungs punkt såpass att dy ungefär
             * når 0 så skall den itne längre ha en hastighet på det horisontella planet.
             */
            if(dy > -3 && dy < 3){
                dx = 0;
                startUp = false;
            }

            /**
             * Poweruppen skall hela tiden bli snabbare i en neråtgående riktning på skärmen
             * tills dess att dy är ungefär 200
             */
            if(dy < 200){
                dy += 3;
            }

            /**
             * Poweruppen måste hålla sig innanför spelets ramar på x axeln
             */
            if (x > Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2)){
                    x = Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2);
            }else if(x < Game.getInstance().getWidth() - Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2)){
                    x = Game.getInstance().getWidth() - Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2);
            }

            /**
             * Om poweruppen är utanför banan längst ner på y-axeln så skall den tas bort.
             */
            if (y > (Game.getInstance().getHeight() + this.sprite.getHeight())) {
                    Game.getInstance().removeEntity(this);
            }
        }
    }

    @Override
    public void collidedWith(Entity other) {
        if (other instanceof CharHitBox){
            if(Game.getInstance().getGameTime() > Game.getInstance().getCharacter().getInvincibility() - 2000){
                if(startTime < Game.getInstance().getGameTime() - 200){
                    if(Game.getInstance().getCharacter().getPower() == 400){
                        Game.getInstance().addScore(1000);
                    }else{
                        Game.getInstance().getCharacter().addPower(400);
                        Game.getInstance().addScore(300);
                    }
                    new AePlayWave(Game.getInstance().getPath() + "/src/sounds/se_item01.wav").start();
                    Game.getInstance().removeEntity(this);
                }
            }
        }
    }
}
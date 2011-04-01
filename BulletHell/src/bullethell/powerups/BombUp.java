package bullethell.powerups;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.character.CharHitBox;
/**
 *
 * @author Daniel
 */
public class BombUp extends Entity{

    private int direction;
    private double moveSpeed = -200;
    private long startTime = 0L;
    private boolean collected = false;
    
    public BombUp(int x, int y) {
        super("sprites/BombUp.png", x, y, false);

        dy = moveSpeed + (Math.random() * 80);
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

        if(collected){
            if(x < Game.getInstance().getHitBox().getX()){
                x += 4;
            }else if(x > Game.getInstance().getHitBox().getX()){
                x -= 4;
            }
            if(y < Game.getInstance().getHitBox().getY()){
                y += 4;
            }else if(y > Game.getInstance().getHitBox().getY()){
                y -= 4;
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
                collected = true;
            }

            /**
             * Om karaktären har full power och befinner sig 1/5 ner på skärmen från
             * toppen, så kommer denna powerup att sugas till karaktären.
             */
            if(Game.getInstance().getHitBox().getY() >= Game.getInstance().getHeight() &&
               Game.getInstance().getCharacter().getPower() == 400){
                collected = true;
            }
            
            /**
             * Efter att poweruppen har åkt ifrån sin ursprungs punkt såpass att dy ungefär
             * når 0 så skall den itne längre ha en hastighet på det horisontella planet.
             */
            if(dy > -3 && dy < 3){
                dx = 0;
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
                    Game.getInstance().getCharacter().addBombs(1);
                    Game.getInstance().addScore(500);
                    Game.getInstance().removeEntity(this);
                }
            }
        }
    }
}
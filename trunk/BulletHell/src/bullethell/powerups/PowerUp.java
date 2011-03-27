package bullethell.powerups;

import bullethell.game.Entity;
import bullethell.character.CharReimu;
import bullethell.game.Game;
/**
 *
 * @author Daniel
 */
public class PowerUp extends Entity{
    private int direction;
    private double moveSpeed = -200;

    public PowerUp(int x, int y) {
        super("sprites/PowerUp.png", x, y, false);

        dy = moveSpeed + (Math.random() * 40);

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
        if (y < (Game.getInstance().getHeight() - Game.getInstance().getHeight())) {
                Game.getInstance().removeEntity(this);
        }
    }

    @Override
    public void collidedWith(Entity other) {
       if (other instanceof CharReimu){
          Game.getInstance().getCharacter().addPower(5);
          Game.getInstance().removeEntity(this);
        }
    }
}
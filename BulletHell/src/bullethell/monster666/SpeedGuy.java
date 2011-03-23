package bullethell.monster666;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.ShotEntity;
import bullethell.character.CharReimu;
import bullethell.game.SpriteStore;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Erik Grönlund & Daniel Vedin (Original from Kevin Glass)
 */
public class SpeedGuy extends Entity {
	/** Hur fort den rör sig på ett horisontellt plan*/
	private double moveSpeed = -200;
	private Game game = Game.getInstance();
        private boolean alive = true;
        private long currentTime;
	
	/**
	 * Skapar ett nytt monster
	 *
         * @param x Där monstret börjar på x axeln
	 * @param y Där monstret börjar på y axeln
         * @param right Om monstret rör sig åt höger eller inte från början
	 */
	public SpeedGuy(int x, int y, boolean right) {
            super("sprites/shyguy.gif",x,y, true);
            if(!right){
		dx = moveSpeed;
            }else{
		dx = -moveSpeed;
                setImage("sprites/shyguy2.gif");
            }
	}

	/**
	 * SpeedGuy rör sig snabbare och snabbare ju fler gånger han krockar
         * Efter ett visst antal gånger så kommer han gå tillbaka till sin
         * ursprungliga hastighet.
         * När SpeedGuy kommer slutet av skärmen byter han håll.
	 * 
	 * @param delta Tiden som gått sedan förra gången move kördes.
	 */
	public void move(long delta) {
		// swap over horizontal movement
                super.move(delta);
            if(this.x <= game.GAME_WIDTH - game.GAME_WIDTH + 15 || this.x >= game.GAME_WIDTH - 60){
		movement(true);
            }
            if (y > Game.getInstance().getHeight()){
                game.notifyEnemyKilled(this);
            }
	}

        /**
         *
         * @param mirror Om monstret ska byta håll på sin sprite eller inte
         */
        public void movement(boolean mirror){
            // För att undvika att gubben inte byter håll alls/byter håll hela
            // tiden har vi en currentTime variabel som ser till att den inte
            // kan byta om inte en viss tid har gått.
            if(currentTime + 500 <= Game.getInstance().getGameTime()){
                currentTime = Game.getInstance().getGameTime();
                dx = -dx;
                if (dx <= 0){
                    dx -= 100;
                } else {
                    dx += 100;
                }
                if (dx <= -700){
                    dx = moveSpeed;
                }else if(dx >= 700){
                    dx = -moveSpeed;
                }
                if(mirror){
                    // mirrors the image so it seems the monster goes the other way
                    if(this.sprite == SpriteStore.get().getSprite("sprites/shyguy.gif")){
                        setImage("sprites/shyguy2.gif");
                    } else {
                        setImage("sprites/shyguy.gif");
                    }
                }
            }
        }

	/**
	 * Kollisionslogik
	 *
	 * @param other Den andra entiteten,
	 */
	public void collidedWith(Entity other) {
            if (other instanceof CharReimu) {
                if(Game.getInstance().getCharacter().getY() + 25 <= y){
                    alive = false;
                    Game.getInstance().notifyEnemyKilled(this);
                    Game.getInstance().getScore().addScore(100);
                }else{
                    if(alive){
                        ((CharReimu)other).loseLife();
                        movement(true);
                    }
                }
            }
            
            /**
             * @todo Gör så att om Entity krockar med en annan entity som rör sig
             * åt samma håll, så kommer bara den som springer in i den andra att vända håll.
             * Detta för att motverka buggar som inträffar när detta händer.
             */
            if (other instanceof SpeedGuy || other instanceof RushGuy){
                if(true){
                    movement(true);
                }else{
                    movement(false);
                }
            }
	}
}
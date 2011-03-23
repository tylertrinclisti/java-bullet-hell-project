package bullethell.monster666;

import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.ShotEntity;
import bullethell.character.Character;
import bullethell.game.SpriteStore;

/**
 * An entity which represents one of our space invader aliens.
 *
 * @author Erik Grönlund & Daniel Vedin (Original from Kevin Glass)
 */
public class SinusGuy extends Entity {
	/** Hur fort den rör sig på ett horisontellt plan*/
	private double moveSpeed = -200;
	private Game game = Game.getInstance();
        private int i = 0;
        private boolean alive = true;
        private double startx = this.x;
	
	/**
	 * Skapar ett nytt monster
	 *
         * @param x Där monstret börjar på x axeln
	 * @param y Där monstret börjar på y axeln
         * @param right Om monstret rör sig åt höger eller inte från början
	 */
	public SinusGuy(int x, int y, boolean right) {
            super("sprites/FlyMario2.png",x,y, true);
            if(!right){
                dx = moveSpeed;
            }else{
                dx = -moveSpeed;
                setImage("sprites/FlyMario.png");
            }
	}

	/**
	 * SinusGuy rör sig efter en sinus kruva
         * När SinusGuy har kommit till borte änden av skärmen så byter han
         * antingen håll eller så teleporterar han till andra sidan av skärmen
         * och börjar om. Varje gång han byter håll/teleporterar så sätts han på
         * en random höjd.
	 *
	 * @param delta Tiden som gått sedan förra gången move kördes.
	 */
	public void move(long delta) {
		super.move(delta);
                i = i + 2;
		dy = (Math.sin(x + i * 0.05) * Math.cos(x + i * 0.1)) * 500;
                if(x <= game.GAME_WIDTH - game.GAME_WIDTH - 50 && this.sprite == SpriteStore.get().getSprite("sprites/FlyMario2.png")){
                    if(Math.random() >= 0.5){
                        dx = -moveSpeed;
                        setImage("sprites/FlyMario.png");
                        x = game.GAME_WIDTH - game.GAME_WIDTH - 50;
                        y = game.GAME_HEIGHT / (4 * Math.random());
                    }else{
                        x = game.GAME_WIDTH + 50;
                        y = game.GAME_HEIGHT / (4 * Math.random());
                    }
                }else if(x >= game.GAME_WIDTH + 50 && this.sprite == SpriteStore.get().getSprite("sprites/FlyMario.png")){
                    if(Math.random() >= 0.5){
                        dx = moveSpeed;
                        setImage("sprites/FlyMario2.png");
                        x = game.GAME_WIDTH + 50;
                        y = game.GAME_HEIGHT / (4 * Math.random());
                    }else{
                        x = game.GAME_WIDTH - game.GAME_WIDTH - 50;
                        y = game.GAME_HEIGHT / (4 * Math.random());
                    }
                }
	}

	/**
	 * Kollisionslogik
	 *
	 * @param other Den andra entiteten,
	 */
	public void collidedWith(Entity other) {
            if (other instanceof Character) {
                if(game.getCharacter().getY() + 25 <= y){
                    alive = false;
                    game.notifyEnemyKilled(this);
                    game.getScore().addScore(100);
                }else{
                    if(alive){
                        ((Character)other).hurt();
                    }
                }
            }
       }
}
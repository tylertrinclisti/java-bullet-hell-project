/**
 * @author Adam Emil Skoog, David Holmquist
 */
package bullethell.character;

import java.awt.Point;
import bullethell.game.Collidable;
import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.SpriteStore;
import bullethell.powerups.BigPowerUp;
import bullethell.powerups.FullPower;
import bullethell.powerups.SmallPowerUp;
import org.duncan.Library2D.Math2D;

public class CharReimu extends Entity
 {
    /**
     * Skapare.
	 * @param position Staden vid att sätta ut spelaren.
     */
    public CharReimu(final Point position)
     {
        // Utnyttja kallet i föräldern.
        super("sprites/reimu_1.png",position.x,position.y,true);
     }

    /**
     * Sätter mängden liv hos spelaren.
     * @param lives Mängden liv att sätta.
     * @return Ändrade spelarenheten.
     */
    public CharReimu setLives(final int lives)
    {
        this.lives = lives;
        if(this.lives > 8){
            this.lives = 8;
        }else if(this.lives < 0){
            this.lives = 0;
        }
        return this;
    }

    /**
     * Sätter mängden bomber hos spelaren
     * @param bombs Mängden bomber att sätta
     * @return Ändrade spelarenheten
     */
    public CharReimu setBombs(final int bombs)
    {
        this.bombs = bombs;
        if(this.bombs > 8){
            this.bombs = 8;
        }else if(this.bombs < 0){
            this.bombs = 0;
        }
        return this;
    }

    /**
     * Sätter mängden power hos spelaren
     * @param power Mängden power att sätta
     * @return Ändrade spelarenheten
     */
    public CharReimu setPower(final int power)
    {
        this.power = power;
        if(this.power > 400){
            this.power = 400;
        }else if(this.power < 0){
            this.power = 0;
        }
        return this;
    }

    /**
     * Ökar nuvarande liv hos spelaren med mängden som givits.
     * @param lives Mängden liv att öka med.
     * @return Ändrade spelarenheten.
     */
    public CharReimu addLives(final int lives)
    {
        if(lives <= 0){
            return this;
        }
        setLives(getLives() + lives);
	return this;
    }

    /**
     * Ökar nuvarande bomber hos spelaren med mängden som givits
     * @param bombs Mängden bomber att öka med
     * @return Ändrade spelarenheten.
     */
    public CharReimu addBombs(final int bombs)
    {
        if(bombs <= 0){
            return this;
        }
        setBombs(getBombs() + bombs);
        return this;
    }

    /**
     * Ökar nuvarande power hos spelaren med mängden som givits
     * @param power Mängden power att öka med
     * @return Ändrade spelarenheten
     */
    public CharReimu addPower(final int power)
    {
        if(power <= 0){
            return this;
        }
        setPower(getPower() + power);
        return this;
    }

    /**
     * Giver mängden liv hos spelaren.
     * @return Mängden liv.
     */
    public final int getLives()
    {
        return lives;
    }

    /**
     * Giver mängden bomber hos spelaren.
     * @return Mängden bomber
     */
    public final int getBombs()
    {
        return bombs;
    }

    /**
     * Giver mängden power hos spelaren.
     * @return Mängden power
     */
    public final int getPower()
    {
        return power;
    }

    /**
     * Utför rätt ändring vid skada.
     */
    public void loseLife()
    {
            if (Game.getInstance().getGameTime() < nextHurt){
		return;
            }
            setLives(getLives() - 1);
            // Can't be hurt right after just being hurt.
            nextHurt = Game.getInstance().getGameTime() + 3000;
            spriteSleep = true;
            setImage("sprites/reimuempty.png");
            if (getLives() <= 0){
                Game.getInstance().addEntity(new FullPower((int) x, (int) y));
            }
            for(int i = 0; i < 10; i++){
                if(power >= 25 && i == 0){
                    Game.getInstance().addEntity(new BigPowerUp((int) x, (int) y));
                    power -= 25;
                    i += 4;
                }else if(power >= 5){
                    Game.getInstance().addEntity(new SmallPowerUp((int) x, (int) y));
                    power -= 5;
                }else{
                    break;
                }
            }
            x = Game.GAME_WIDTH / 2;
            y = Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10);
    }

    /**
     * Gör karaktären odödlig i ett visst antal sekunder
     * @param miliseconds hur många ms som karaktären ska vara odödlig
     */
    public void setInvincibility(int miliseconds)
    {
        nextHurt = Game.getInstance().getGameTime() + miliseconds;
    }

    /** 
     * Skickar tillbaka hur länge till karaktären är odödlig
     * @return Vad klockan var när man blev odödlig + Tiden man är odödlig
     */
    public final long getInvincibility()
    {
        return nextHurt;
    }

    /**
     * Sätter spelarhastigheten.
     * @param speed Hastigheten att sätta.
     * @return Ändrade spelarenheten.
     */
    public CharReimu setSpeed(final float speed)
     {
        this.speed              = speed;
        speedOrDirectionChanged = true;
        return this;
     }

    /**
     * Giver spelarhastigheten.
     * @return Spelarhastigheten.
     */
    public final float getSpeed()
     {
        return speed;
     }

    /**
     * Sätter största tillåtna hastigheten för spelaren, efter full fartökning.
     * @param speed Hastighetsgränsen att sätta.
     * @return Ändrade spelarenheten.
     */
    public CharReimu setMaximalSpeed(final float speed)
     {
        maximalSpeed = speed;
        return this;
     }

    /**
     * Giver största tillåtna hastigheten för spelaren, efter full fartökning.
     * @return Hastighetsgränsen spelarens.
     */
    public final float getMaximalSpeed()
     {
        return maximalSpeed;
     }

    /**
     * Sätter rättningen spelarens. Värdet skall ligga mellan 0 och 360, och är
     * det mera eller mindre, räknas det om i rätt vinkel, så att värdet alltid
     * är rätt.
     * @param direction Rättningen att sätta.
     * @return Ändrade spelarenheten.
     */
    public CharReimu setDirection(final float direction)
     {
        this.direction          = direction;
        speedOrDirectionChanged = true;
        return this;
     }

    /**
     * Giver rättningen spelarens.
     * @return Rättningen spelarens. Värdet ligger alltid mellan 0 och 360.
     */
    public final float getDirection()
     {
        return direction;
     }

    /**
     * Denna händelse kallas var bildruta av spelet.
     */
    @Override
    public void move(long delta)
    {
        super.move(delta);

        // Om hastighet eller rättning är ändrad, skall rätt värden räknas ut
        // åt skiftvärdena ärvda från föräldern.
        if (speedOrDirectionChanged)
        {
            /**
             * @todo Se till att vinkeln alltid är mellan 0 och 360, men ej i
             *       genom att bara kapa av värdet om det är för högt eller
             *       lågt; fortsätt att se det som en tillåten vinkel, och
             *       räkna ut vad värdet skall vara för att motsvara en vinkel
             *       mellan 0 och 360 grader (-20 grader skulle räknas om i 340
             *       grader; 400 grader skulle ändras i 40).
             */
            if (direction>0) {
                direction -= Math.floor(direction/360.f)*360.f;
            }
            else {
                direction = 360.f + direction - (float)Math.floor(direction/360.f)*360.f;
                
            }
            System.out.println(direction);

            // Utnyttja uträkningarna som redan är inkapslade.
            final Point MOVEMENT = Math2D.lengthDir(getSpeed(),getDirection()).toAWT();
            setHorizontalMovement(MOVEMENT.x);
            setVerticalMovement(MOVEMENT.y);
        }

        /**
         * När karaktären har kommit tillbaka till ursprungs startpunkten efter
         * att ha förlorat ett liv, så skall det ta en sekund innan man kan se
         * karaktären igen.
         */
        if(Game.getInstance().getGameTime() > nextHurt - 2000){
            spriteSleep = false;
        }

        /**
         * Karaktären byter hela tiden sprite, om den inte går i sidleds, och
         * om karaktären inte just har förlorat ett liv.
         */
        if(!spriteSleep){
            if(Game.getInstance().getGameTime() > nextSprite && !Game.getInstance().getKeyPressed(1) && !Game.getInstance().getKeyPressed(4)){
                nextSprite = Game.getInstance().getGameTime() + 80;
                if(this.sprite == SpriteStore.get().getSprite("sprites/reimu_1.png")){
                    setImage("sprites/reimu_2.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimu_2.png")){
                    setImage("sprites/reimu_3.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimu_3.png")){
                    setImage("sprites/reimu_4.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuL_2.png")){
                    setImage("sprites/reimuL_1.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuL_3.png")){
                    setImage("sprites/reimuL_2.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuL_4.png")){
                    setImage("sprites/reimuL_3.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuL_5.png") ||
                         this.sprite == SpriteStore.get().getSprite("sprites/reimuL_6.png") ||
                         this.sprite == SpriteStore.get().getSprite("sprites/reimuL_7.png")){
                    setImage("sprites/reimuL_4.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuR_2.png")){
                    setImage("sprites/reimuR_1.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuR_3.png")){
                    setImage("sprites/reimuR_2.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuR_4.png")){
                    setImage("sprites/reimuR_3.png");
                }else if(this.sprite == SpriteStore.get().getSprite("sprites/reimuR_5.png") ||
                         this.sprite == SpriteStore.get().getSprite("sprites/reimuR_6.png") ||
                         this.sprite == SpriteStore.get().getSprite("sprites/reimuR_7.png")){
                    setImage("sprites/reimuR_4.png");
                }else{
                    setImage("sprites/reimu_1.png");
                }

                /**
                * Om karaktären just har förlorat ett liv så skall karaktärens sprite "blinka"
                * mellan en tom sprite och den vanliga
                */
                if(Game.getInstance().getGameTime() < nextHurt && nextHurtSprite < Game.getInstance().getGameTime()){
                    setImage("sprites/reimuempty.png");
                    nextHurtSprite = Game.getInstance().getGameTime() + 160;
                }
            }
        }

        /**
         * Kollar om karaktären är död
         */
        if (getLives() <= 0){
            nextHurt = 0;
            Game.getInstance().notifyDeath();
            return;
        }

        /**
         * Karaktären måste hålla sig inom ramarna på y axeln
         */
        if (y > Game.getInstance().getHeight() - this.getSprite().getHeight()){
                y = Game.getInstance().getHeight() - this.getSprite().getHeight();
        }else if(y < Game.getInstance().getHeight() - Game.getInstance().getHeight()){
                y = Game.getInstance().getHeight() - Game.getInstance().getHeight();
        }

        /**
         * Karaktären måste hålla sig inom ramarna på x axeln
         */
        if (x > Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2)){
                x = Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2);
        }else if(x < Game.getInstance().getWidth() - Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2)){
                x = Game.getInstance().getWidth() - Game.getInstance().getWidth() - (this.getSprite().getWidth() / 2);
        }
    }
    /**
     * Denna händelse orsakas vid möte mellan spelaren och någon annan enhet.
     * @param other Andra enheten i mötet.
     */
    @Override
    public void collidedWith(Entity other){
        
    }

    // Inställningar.
    private int     lives        = 3;
    private int     bombs        = 2;
    private int     power        = 0;
    private float   speed        = .0f;
    private float   maximalSpeed = 10.f;
    private float   direction    = -400.0f;
    // Styrskiftvärden.
    private boolean speedOrDirectionChanged = false;
    // För sprite byte
    private long    nextSprite  = 0L;
    private long    nextHurtSprite = 0L;
    private boolean spriteSleep = false;
    // För odödlighet.
    private long    nextHurt	= 0L;
 }


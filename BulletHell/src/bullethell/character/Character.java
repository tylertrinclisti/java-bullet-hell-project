/**
 * @author Adam Emil Skoog, David Holmquist
 */
package bullethell.character;

import java.awt.Point;
import bullethell.game.Collidable;
import bullethell.game.Entity;
import bullethell.game.Game;
import bullethell.game.PlaceholderPlatform;
import bullethell.platforms.PlatformContainerEntity;
import bullethell.platforms.PlatformNode;
import org.duncan.Library2D.Math2D;

public class Character extends Entity
 {
    /**
     * Skapare.
	 * @param position Staden vid att sätta ut spelaren.
     */
    public Character(final Point position)
     {
        // Utnyttja kallet i föräldern.
        super("sprites/characterSmall.png",position.x,position.y,true);
     }

    public static void main(String[] args)
     {
        Character c = new Character(new Point(8,8));
     }
    
    /**
     * Sätter mängden liv hos spelaren.
     * @param lives Mängden liv att sätta.
     * @return Ändrade spelarenheten.
     */
    public Character setLives(final int lives)
     {
        this.lives = lives;
        return this;
     }

    /**
     * Ökar nuvarande liv hos spelaren med mängden som givits.
     * @param lives Mängden liv att öka med.
     * @return Ändrade spelarenheten.
     */
    public Character addLives(final int lives)
     {
        setLives(getLives() + lives);
      
		if (getLives() <= 0)
			Game.getInstance().notifyDeath();

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
     * Skiftar storleken hos spelaren, som kan vara antingen stor eller liten.
     * Om han är stor, vill ett kall hit vränga detta, och tvärt om.
     * @return Ändrade spelarenheten.
     */
    public Character swapSize()
     {
        big = !big;
        if (isBig()) {
            if (!isRight) {
                setImage("sprites/characterBigLeft.png");

            }
            else{
                setImage("sprites/characterBig.png");
            }
            
            

        }
        else {
            if (!isRight) {
                setImage("sprites/characterSmallLeft.png");
            }
            else {
                setImage("sprites/characterSmall.png");
                 }
            

        }
        return this;
     }

    /**
     * Säger om spelaren för stunden är stor.
     * @return Om stor.
     */
    public final boolean isBig()
     {
        return big;
     }

    /**
     * Utför rätt ändring vid skada.
     */
    public void hurt()
     {
		if (Game.getInstance().getGameTime() < nextHurt)
			return;

        if (isBig())
            swapSize();
        else
            setLives(getLives() - 1);

		if (getLives() <= 0)
			Game.getInstance().notifyDeath();

		// Can't be hurt right after just being hurt.
		nextHurt = Game.getInstance().getGameTime() + 950;
     }

    /**
     * Sätter spelarhastigheten.
     * @param speed Hastigheten att sätta.
     * @return Ändrade spelarenheten.
     */
    public Character setSpeed(final float speed)
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
    public Character setMaximalSpeed(final float speed)
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
    public Character setDirection(final float direction)
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
     * Säger åt spelaren att börja hoppa.
     */
    public void startJump()
     {
        jump = false;
     }

    /**
     * Säger om spelaren kan hoppa eller ej.
	 * @return True om spelaren kan hoppa
	 */
    public final boolean canJump()
     {
        return jump;
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

        if(isRight&&dx<0) {
            if (!isBig()){
                 setImage("sprites/characterSmallLeft.png");

            }
            else {
                 setImage("sprites/characterBigLeft.png");
                 }
            isRight = false;
        }
        else if(!isRight&&dx>0) {
          if (!isBig())  {
                 setImage("sprites/characterSmall.png");
                         }
          else {
              setImage("sprites/characterBig.png");
               }

         isRight = true;
        }

        if (y > Game.getInstance().getHeight())
            Game.getInstance().notifyDeath();

        if (Game.getInstance().getMap().collidesWith(this))
         {
            /*
            Collidable other = Game.getInstance().getMap().getLastCollision();

            if (other != null)
             {
                if (other instanceof PlatformContainerEntity)
                 {
                    PlatformContainerEntity container = (PlatformContainerEntity)other;

                    if (container != null)
                     {
                        if(container.getLastCollision() != null)
                         {
                            PlatformNode platform = container.getLastCollision();

                            if (platform != null)
                             {
                                /*org.duncan.Library2D.Point point = platform.getCollisionPoint(new org.duncan.Library2D.Point((int)dx,(int)dy),
                                                                                              new org.duncan.Library2D.Point((int)dx + sprite.getWidth(),
                                                                                                                             (int)dy + sprite.getHeight()));//
                                org.duncan.Library2D.Point point = platform.getCollisionPoint(new org.duncan.Library2D.Point((int)dx + sprite.getWidth() / 2,(int) dy + sprite.getHeight()),
                                                                                              new org.duncan.Library2D.Point((int)dx + sprite.getWidth() / 2,(int) dy + sprite.getHeight() + 100));

                                if (point != null)
                                 {
									// Denna behöver trimmas
									// Det stog dy = point.getY() förrut, dy är hastigheten, antar att det var fel.
                                    //y = point.getY() - getSprite().getHeight() - 12;
                                    setVerticalMovement(0);
									setSleeping(true);
                                    jump = true;
                                 }
                             }
                         }
                     }
                 }
             }*/
             setSleeping(true);
             setVerticalMovement(0);
             jump = true;
        }
     }

    /**
     * Denna händelse orsakas vid möte mellan spelaren och någon annan enhet.
     * @param other Andra enheten i mötet.
     */
    @Override
    public void collidedWith(Entity other)
     {
        if (other instanceof PlaceholderPlatform)
         {
            if (y > other.getY() - sprite.getHeight() / 2.f)
             {
                if (x > other.getX() - sprite.getWidth() * .8f &&
                    x < other.getX() + other.getSprite().getWidth())
                 {
                    y = other.getY() + other.getSprite().getHeight() + 1.f;
                    setVerticalMovement(0);
                 }
                else
                    setHorizontalMovement(0);
             }
            else
             {
                y = other.getY() - sprite.getHeight();
                setVerticalMovement(0);
                setSleeping(true);
                jump = true;
             }
         }
     }

    // Inställningar.
    private int     lives        = 3;
    private boolean big          = false;
    private float   speed        = .0f,
                    maximalSpeed = 10.f,
                    direction    = -400.0f;
    private boolean jump         = false;
    private boolean isRight    = true;
    // Styrskiftvärden.
    private boolean speedOrDirectionChanged = false;

	// För odödlighet.
	private long	nextHurt	= 0L;
 }


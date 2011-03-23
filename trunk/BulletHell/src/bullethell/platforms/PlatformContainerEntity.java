package bullethell.platforms;

import java.awt.Graphics;
import java.awt.Image;
import bullethell.game.Collidable;
import bullethell.game.Entity;
import bullethell.game.Game;

/**
 * A handle so that PlatformContainers can be used as an Entity.
 * @author Duncan
 */
public class PlatformContainerEntity extends Entity implements Collidable
{
    private PlatformContainer c;
    private Image img;

    /**
     * PlatformContainerEntity constructor.
     */
    public PlatformContainerEntity()
    {
        c = new PlatformContainer();
    }

    /**
     * Wraps a new PlatformContainerEntity around an existing PlatformContainer.
     * @param c The existing PlatformContainer
     * @return The wrapped handle.
     */
    public static PlatformContainerEntity generate(PlatformContainer c)
    {
        PlatformContainerEntity e = new PlatformContainerEntity();
        e.c = c;
        return e;
    }

    /**
     * Adds the given PlatformNode to the PlatformContainer inside this handle.
     * @param node
     */
    public void add(PlatformNode node)
    {
        c.add(node);
    }

    /**
     * Forwards the collision to the given parameter.
     * @param other
     */
    @Override
    public void collidedWith(Entity other)
    {
        other.collidedWith(this);
    }

    /**
     * Returns true if the given collidable is touching this one.
     * @param other
     * @return
     */
    @Override
    public boolean collidesWith(Collidable other)
    {
        return c.collidesWith(other);
    }

    public PlatformNode getLastCollision()
    {
        return c.getLastCollision();
    }

    /**
     * Draws all the platforms in this handle on the given surface.
     * @param g
     */
    @Override
    public void draw(Graphics g)
    {
        if (img == null) img = c.render(Game.getInstance().getView());
        g.drawImage(img, 0, 0, null);
    }
}

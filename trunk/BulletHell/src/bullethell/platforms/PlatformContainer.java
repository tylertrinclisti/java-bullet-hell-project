package bullethell.platforms;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import bullethell.game.Collidable;
import bullethell.game.Entity;

/**
 * A Container for the platform series. There should only exist one container
 * in each scene. To generate a platform sequence, create a number of platform
 * nodes and append them to this contianer using the add(PlaformNode node)-method.
 * To check collision with the platform, use the collidesWith(Colidable col)-method
 * in this class. To view the platform, run the render(Rectangle rect)-method
 * wich will return an Image of the currently visible view. To platform-nodes
 * should never be touched directly.
 * 
 * @author duncan
 */
public class PlatformContainer extends ArrayList<PlatformNode> implements Collidable
{
    private PlatformNode lastCollision;

    /**
     * Renders the given view.
     * @param rect The current viewport.
     * @return A rendered frame.
     */
    public Image render(Rectangle rect)
    {
        BufferedImage imgLayer1 = new BufferedImage(
                (int) rect.getWidth(),
                (int) rect.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics gLayer1 = imgLayer1.getGraphics();

        BufferedImage imgLayer2 = new BufferedImage(
                (int) rect.getWidth(),
                (int) rect.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics gLayer2 = imgLayer2.getGraphics();

        for (int i = 0; i < size(); i++)
        {
            get(i).render(gLayer1, gLayer2, rect);
        }

        gLayer1.drawImage(imgLayer2, 0, 0, null);
        return imgLayer1;
    }

    public PlatformNode getLastCollision()
    {
        return lastCollision;
    }

    /**
     * If the given entity intersects with any platform in this container, then
     * true is returned. Else false.
     * @param entity
     * @return
     */
    @Override
    public boolean collidesWith(Collidable other)
    {
        if (other instanceof Entity)
        {
            Entity e = (Entity) other;
            for (int i = 0; i < size(); i++)
            {
                //if (get(i).getOutsideShape().contains(e.getX(), e.getY()))
                if (e.getSprite() != null && get(i).getOutsideShape().intersects(new Rectangle(e.getX(), e.getY(), e.getSprite().getWidth(), e.getSprite().getHeight())))
                {
                    lastCollision = get(i);
                    if (other instanceof Entity)
                        ((Entity) other).setY(((Entity) other).getY() - 1);
                    return true;
                }
                else if (get(i).getOutsideShape().contains(e.getX(), e.getY()))
                {
                    lastCollision = get(i);
                    return true;
                }
            }
            return false;
        }
        else throw new Error("Det där var ingen Entity!");
    }

    public Entity toEntity()
    {
        return PlatformContainerEntity.generate(this);
    }
}
package bullethell.platforms;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import bullethell.game.Sprite;
import bullethell.game.SpriteStore;
import org.duncan.Library2D.Math2D;
import org.duncan.Library2D.Point;
import org.duncan.Library2D.Ray;

/**
 * A segment in a platform. These should be generated and then appended to a
 * platform container. A platform node has a starting and an ending point. The
 * platform has a thickness of 24 pixels. The only values that is required is
 * the starting and the ending point. 
 * @author duncan
 */
public class PlatformNode
{
    private Sprite textureInside  = SpriteStore.get().getSprite("sprites/dirt.png");
    private Sprite textureOutside = SpriteStore.get().getSprite("sprites/grass.png");

    private Point start;
    private Point end;

    private Polygon pOuter = null;
    private Polygon pInner = null;

    /**
     * Node constructor.
     * @param start The first point in the platform.
     * @param end The end of the platform.
     */
    public PlatformNode(Point start, Point end)
    {
        this.start = start;
        this.end   = end;
    }

    /**
     * Node constructor.
     * @param textureInside The inside texture of the platform.
     * @param textureOutside The outside texture of the platform.
     */
    public PlatformNode(Sprite textureInside, Sprite textureOutside)
    {
        this (new Point(0, 0), new Point(0, 0), textureInside, textureOutside);
    }

    /**
     * Node constructor.
     * @param start The first point in the platform.
     * @param end The end of the platform.
     * @param textureInside The inside texture of the platform.
     * @param textureOutside The outside texture of the platform.
     */
    public PlatformNode(Point start, Point end, Sprite textureInside, Sprite textureOutside)
    {
        if (textureInside == null) System.err.println("Tex is null 2.");
        this.start          = start;
        this.end            = end;
        this.textureInside  = textureInside;
        this.textureOutside = textureOutside;
    }

    /**
     * Sets the starting-position of the platform.
     * @param start The start-position.
     */
    public void setStart(Point start)
    {
        this.start = start;
        pOuter = null;
        pInner = null;
    }

    /**
     * Sets the ending-position of the platform.
     * @param end The end-position.
     */
    public void setEnd(Point end)
    {
        this.end = end;
        pOuter = null;
        pInner = null;
    }

    /**
     * Returns the starting position of the platform.
     * @return The start.
     */
    public Point getStart()
    {
        return start;
    }

    /**
     * Returns the ending position of the platform.
     * @return The end.
     */
    public Point getEnd()
    {
        return end;
    }

    /**
     * Returns the total width of the platform.
     * @return
     */
    public int getWidth()
    {
        return (int) (Math.max(start.getX(), end.getX()) - Math.min(start.getX(), end.getX()) + 24);
    }

    /**
     * Returns the total height of the platform.
     * @return
     */
    public int getHeight()
    {
        return (int) (Math.max(start.getY(), end.getY()) - Math.min(start.getY(), end.getY()) + 24);
    }

    /**
     * Returns the inside texture of the platform.
     * @return
     */
    public Sprite getTextureInside()
    {
        return textureInside;
    }

    /**
     * Returns the outside texture of the platform.
     * @return
     */
    public Sprite getTextureOutside()
    {
        return textureOutside;
    }

    /**
     * Sets the inside texture of the platform.
     * @param textureInside
     */
    public void setTextureInside(Sprite textureInside)
    {
        this.textureInside = textureInside;
    }

    /**
     * Sets the outside texture of the platform.
     * @param textureOutside
     */
    public void setTextureOutside(Sprite textureOutside)
    {
        this.textureOutside = textureOutside;
    }

    /**
     * Returns the shape of the inside of the platform.
     * @return
     */
    public Polygon getInsideShape()
    {
        if (pInner == null)
            return (pInner = PlatformShape.getPlatformShape(getStart(), getEnd(), 8d));
        else return pInner;
    }

    /**
     * Returns the shape of the outside of the platform.
     * @return
     */
    public Polygon getOutsideShape()
    {
        if (pOuter == null)
            return (pOuter = PlatformShape.getPlatformShape(getStart(), getEnd(), 12d));
        else return pOuter;
    }

    /**
     * Returns the first collision point on a line from the player to the given
     * goal.
     * @param player The first point.
     * @param goal The aim.
     * @return The first collision point or null.
     */
    public Point getCollisionPoint(Point player, Point goal)
    {
        return Math2D.getCollisionPoint(getOutsideShape(), player, goal);
    }

    /**
     * Renders the platform.
     * @param layer1 The Outside-layer of the render.
     * @param layer2 The inside-layer of the render.
     * @param view The view to render.
     */
    public void render(Graphics layer1, Graphics layer2, Rectangle view)
    {
        if (textureInside == null) System.err.println("Tex is null 3.");
        BufferedImage i_dirt = new BufferedImage((int) view.getWidth(), (int) view.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g_dirt = i_dirt.getGraphics();
        g_dirt.setClip(getInsideShape());

        BufferedImage i_grass = new BufferedImage((int) view.getWidth(), (int) view.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g_grass = i_grass.getGraphics();
        g_grass.setClip(getOutsideShape());

        for (int x = 0; x < getWidth(); x += 32)
        {
            for (int y = 0; y < getHeight(); y += 32)
            {
                textureInside.draw(g_dirt,
                        (int) (x + Math.min(getStart().getX(), getEnd().getX()) - 8),
                        (int) (y + Math.min(getStart().getY(), getEnd().getY()) - 8));
                textureOutside.draw(g_grass,
                        (int) (x + Math.min(getStart().getX(), getEnd().getX()) - 12),
                        (int) (y + Math.min(getStart().getY(), getEnd().getY()) - 12));
            }
        }
        layer1.drawImage(i_grass, 0, 0, null);
        layer2.drawImage(i_dirt, 0, 0, null);
    }
}
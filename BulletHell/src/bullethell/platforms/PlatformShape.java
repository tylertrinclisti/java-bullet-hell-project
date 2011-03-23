package bullethell.platforms;

import org.duncan.Library2D.Math2D;
import java.awt.Polygon;
import org.duncan.Library2D.Point;

/**
 * Used to generate platform-shapes.
 * @author duncan
 */
public final class PlatformShape
{
    public static Polygon getPlatformShape(Point start, Point end, double radius)
    {
        Polygon p = new Polygon();

        for (int i = 0; i < 16; i++)
        {
            p.addPoint(
                    (int) (start.getX() - Math.cos(Math.PI / 16 * i + Math2D.direction(end, start) + Math.PI * 0.5) * radius),
                    (int) (start.getY() - Math.sin(Math.PI / 16 * i + Math2D.direction(end, start) + Math.PI * 0.5) * radius));
        }

        for (double i = 0; i <= Math.PI; i += Math.PI / 16)
        {
            p.addPoint(
                    (int) (end.getX() + Math.cos(Math2D.direction(start, end) - Math.PI / 2 + i) * radius),
                    (int) (end.getY() + Math.sin(Math2D.direction(start, end) - Math.PI / 2 + i) * radius));
        }

        p.addPoint(p.xpoints[0], p.ypoints[0]);

        return p;
    }
}

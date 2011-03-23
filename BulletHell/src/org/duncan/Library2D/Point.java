package org.duncan.Library2D;

/**
 * A 2D-position.
 * @author Duncan
 */
public class Point
{
    private double x, y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public java.awt.Point toAWT()
    {
        return new java.awt.Point((int) getX(), (int) getY());
    }
}
package game;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Vector2D
{
    double x,y;

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2D()
    {
        this(0,0);
    }

    public void add(Vector2D velocity)
    {
        x+=velocity.x;
        y+=velocity.y;
    }

    @Override
    protected Vector2D clone()
    {
        return  new Vector2D(x,y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

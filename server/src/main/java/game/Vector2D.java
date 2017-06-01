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
    public double cross(Vector2D a)
    {
        return x*a.x + y*a.y;
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

    static public Vector2D sub(Vector2D v1,Vector2D v2) {
        return new Vector2D(v1.x - v2.x ,v1.y - v2.y);
    }
    static public Vector2D add(Vector2D v1,Vector2D v2) {
        return new Vector2D(v1.x + v2.x ,v1.y + v2.y);
    }
}

package game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;

import javax.swing.*;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Obstacle
{
    double x,y;
    double r;

    transient Body circle;

    public Obstacle(double x, double y, double r)
    {
        this.x = x;
        this.y = y;
        this.r = r;

        circle = new Body();
        circle.addFixture(Geometry.createCircle(r));
        circle.setMass(MassType.NORMAL);
        circle.translate(x, y);
    }
    public void sync()
    {
        x=circle.getWorldCenter().x;
        y = circle.getWorldCenter().y;
//        pos = new Vector2D(,);
    }
}

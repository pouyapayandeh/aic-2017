package game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;

import javax.swing.*;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Bullet
{
    Vector2D pos;
    double orientation;

   // transient Body circle;

//    public Body getCircle() {
//        return circle;
//    }

    public Bullet(Vector2D pos, double orientation) {
        this.pos = pos;
        this.orientation = orientation;

//        circle = new Body();
//        circle.addFixture(Geometry.createCircle(ConfigManager.i().getBulletSize()));
////        circle.setMass(MassType.NORMAL);
////        circle.setMass(MassType.FIXED_LINEAR_VELOCITY);
//        circle.translate(pos.getX(), pos.getY());
//        double dx =  (ConfigManager.i().getBulletVelocity()*Math.cos(orientation));
//        double dy = (ConfigManager.i().getBulletVelocity()*Math.sin(orientation));
//        circle.setLinearVelocity(dx,dy);
       // circle.setBullet(true);
    }

    public Vector2D getPos() {
        return pos;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
//    public void sync()
//    {
//        pos = new Vector2D(circle.getWorldCenter().x,circle.getWorldCenter().y);
//    }
}

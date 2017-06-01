package game;

import com.google.gson.annotations.SerializedName;
import com.sun.javafx.geom.Vec2d;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;

import javax.swing.*;
import java.beans.Transient;


/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Agent
{
    static int gid = 0;
    int health;
    int ammo;
    int id;

//    transient Body circle;
    transient Team team;
    Vector2D pos;
    double orientation;

    public void setTeam(Team team)
    {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public Agent(Vector2D pos)
    {
        this.pos = pos.clone();
        id = gid++;
        health = ConfigManager.i().getBaseHealth();
//        circle = new Body();
//        circle.addFixture(Geometry.createCircle(ConfigManager.i().getAgentSize()));
//        circle.setMass(MassType.NORMAL);
//        circle.translate(pos.getX(), pos.getY());
//

    }
    public void move(Vector2D v)
    {
            pos.add(v);
//        circle.setLinearVelocity(v.getX(),v.getY());
    }
    public void rotate(double w)
    {
        orientation+=w;
    }

    public double getOrientation() {
        return orientation;
    }

    public Vector2D getPos() {
        return pos;
    }

    public void setPos(Vector2D pos) {
        this.pos = pos;
    }
//    public void sync()
//    {
//        pos = new Vector2D(circle.getWorldCenter().x,circle.getWorldCenter().y);
//    }
}

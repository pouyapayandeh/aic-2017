package game;

import com.google.gson.annotations.SerializedName;
import com.sun.javafx.geom.Vec2d;

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

    transient Team team;
    Vector2D pos;
    float orientation;

    public void setTeam(Team team)
    {
        this.team = team;
    }


    public Agent(Vector2D pos)
    {
        this.pos = pos.clone();
        id = ++gid;
        health = ConfigManager.i().getBaseHealth();
    }
    public void move(Vector2D v)
    {
        pos.add(v);
    }
    public void rotate(float w)
    {
        orientation+=w;
    }

}

package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya Payandeh on 6/6/2017.
 */
public class GameData
{
    List<Team> teams;
    List<Bullet> bullets;
    List<Obstacle> obstacles;
    int time;
    int width;
    int height;
    int totalTurns;
    public GameData()
    {
        obstacles = new ArrayList<>();
        teams = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getTotalTurns()
    {
        return totalTurns;
    }

    public List<Team> getTeams()
    {
        return teams;
    }

    public int getWidth()
    {
        return width;
    }

    public int getTime()
    {
        return time;
    }



    public int getHeight()
    {
        return height;
    }

    public List<Obstacle> getObstacles()
    {
        return obstacles;
    }
}

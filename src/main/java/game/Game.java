package game;

import game.event.TurnEndEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Game
{

    List<Team> teams;
    List<Bullet> bullets;
    List<Obstacle> obstacles;
    int time;
    public Game()
    {
        teams = new ArrayList<>();
        bullets = new ArrayList<>();
        obstacles = new ArrayList<>();
    }

    public void setupGame()
    {
        Team A = new Team("Team A" );
        Team B = new Team("Team B" );
        A.newAgent(new Vector2D(10,10));
        B.newAgent(new Vector2D(100,100));
        obstacles.add(new Obstacle(50,50,10));
        teams.add(A);
        teams.add(B);
    }
    public void doTurn()
    {
        time +=ConfigManager.i().getTimeStep();
    }

}

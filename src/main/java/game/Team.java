package game;

import java.util.ArrayList;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Team
{
    static int gid = 0;
    int id;
    String name;
    ArrayList<Agent> agents;
    int score;

    public Team(String name )
    {
        this.id = gid++;
        this.name = name;
        score =  0;
        agents = new ArrayList<>();
    }
    public void newAgent(Vector2D pos)
    {
        Agent agent = new Agent(pos);
        agent.setTeam(this);
        agents.add(agent);
    }

}

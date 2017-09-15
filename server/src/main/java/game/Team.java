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

    transient  Game game;


    public Team(String name ,Game game)
    {
        this.id = gid++;
        this.name = name;
        score =  0;
        this.game =game;
        agents = new ArrayList<>();
    }
    public void newAgent(Vector2D pos , double w)
    {
        Agent agent = new Agent(pos);
        agent.setTeam(this);
        agent.orientation= w;
        agents.add(agent);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

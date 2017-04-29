package game.command;

import game.Agent;
import game.Game;
import game.Vector2D;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class RotateCommand implements Command
{
    int team;
    int agent;

    public RotateCommand(int team, int agent, double w) {
        this.team = team;
        this.agent = agent;
        this.w = w;
    }

    double w;

    @Override
    public void apply(Game game)
    {
        Agent ag = game.getAgent(agent);
        if(ag.getTeam().getId() == team)
            ag.rotate(w);

    }
}

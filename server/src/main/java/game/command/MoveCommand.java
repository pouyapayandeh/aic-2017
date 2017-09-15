package game.command;

import game.Agent;
import game.Game;
import game.Vector2D;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class MoveCommand implements  Command
{



    int team;
    int agent;
    Vector2D v;

    public MoveCommand(int team, int agent, Vector2D v) {
        this.team = team;
        this.agent = agent;
        this.v = v;
    }

    @Override
    public void apply(Game game)
    {
         Agent ag = game.getAgent(agent);
        if(ag.getTeam().getId() == team) {
            Vector2D loc = game.collisionLocation(ag, v);
            if(loc != null)
                ag.setPos(loc);
        }
    }
}

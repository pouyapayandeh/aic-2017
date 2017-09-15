package game.command;

import game.*;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class ShootCommand implements  Command
{
    int agent;
    int team;

    public ShootCommand(int team,int agent) {
        this.agent = agent;
        this.team = team;
    }

    @Override
    public void apply(Game game) {
        Agent ag = game.getAgent(agent);
        if (ag.getTeam().getId() == team) {
            int aw = ConfigManager.i().getAgentSize();
            int bw = ConfigManager.i().getBulletSize();
            double x =  (Math.cos( ag.getOrientation())*(aw+bw+3));
            double y = (Math.sin( ag.getOrientation())*(aw+bw+3));
            Bullet b =new Bullet(new Vector2D(ag.getPos().getX()+x,
                    ag.getPos().getY()+y), ag.getOrientation());
            game.getBullets().add(b);
        }
    }
}

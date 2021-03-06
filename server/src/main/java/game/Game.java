package game;

import game.command.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Game extends GameData {


    public Game() {
        super();
    }

    public void setupGame() {
        width = ConfigManager.i().getMapWidth();
        height = ConfigManager.i().getMapHeight();
        Team A = new Team("TeamA", this);
        Team B = new Team("TeamB", this);
        A.newAgent(new Vector2D(10, 10),0);
        B.newAgent(new Vector2D(100, 100),0);
        Obstacle obstacle = new Obstacle(300, 300, 50);
        obstacles.add(obstacle);
        teams.add(A);
        teams.add(B);
    }

    public void setupGame(URI map)
    {
        try
        {
            Scanner scanner = new Scanner(new File(map));
            totalTurns = scanner.nextInt();
            int teamNum = scanner.nextInt();
            width = scanner.nextInt();
            height = scanner.nextInt();
            int obstacleNum = scanner.nextInt();
            for (int i = 0; i < obstacleNum; i++)
            {
                int x,y,r;
                x = scanner.nextInt();
                y = scanner.nextInt();
                r = scanner.nextInt();
                obstacles.add(new Obstacle(x,y,r));
            }
            for(int i = 0 ; i < teamNum ; i++)
            {
                scanner.nextInt();
                String teamName = scanner.next();
                Team t = new Team(teamName,this);
                int agentNum;
                agentNum = scanner.nextInt();
                for (int j = 0; j < agentNum; j++)
                {
                    scanner.nextInt();
                    int x,y;
                    double w;
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    w = scanner.nextDouble();
                    t.newAgent(new Vector2D(x,y),w);
                }
                teams.add(t);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void doTurn(List<Command> commands) {
        moveBullets();
        removeBullets();
        commands.forEach(command -> command.apply(this));
        time += ConfigManager.i().getTimeStep();
    }

    private void removeBullets() {
        bullets.removeIf(bullet -> isNotInMap(bullet.pos));
    }

    private void moveBullets() {

//
        for (Bullet bullet : bullets) {
            double dx = (ConfigManager.i().getBulletVelocity() * Math.cos(bullet.getOrientation()));
            double dy = (ConfigManager.i().getBulletVelocity() * Math.sin(bullet.getOrientation()));
            bullet.getPos().add(new Vector2D(dx, dy));
        }
    }

    public Vector2D MotionEquation(Vector2D u, Vector2D v, double t) {
        if (t >= 0 && t <= 1) {
            return new Vector2D((1-t)*u.getX()+(t)*v.getX(),(1-t)*u.getY()+(t)*v.getY());

        }
        return null;
    }

    public double collisionDetect(Vector2D u1, Vector2D u2, Vector2D v1, Vector2D v2, double r1, double r2) {
        double R = r1 + r2;
        double R2 = R * R;
        Vector2D v = Vector2D.sub(v2, v1);
        Vector2D u = Vector2D.sub(u2, u1);
        double uu = u.cross(u);
        double vv = v.cross(v);
        double uv = v.cross(u);

        double a = uu - 2 * uv + vv;
        double b = -2 * uu + 2 * uv;
        double c = uu - R2;

        double delta = b * b - 4 * a * c;

        if (delta >= 0) {
            double t1 = (-b + Math.sqrt(delta)) / (2 * a);
            double t2 = (-b - Math.sqrt(delta)) / (2 * a);
            if (t1 >= 0 && t1 <= 1 && t2 >= 0 && t2 <= 1 && t1 < t2) {
                return t1;//MotionEquation(u1,v1,t1);
            }
            if (t1 >= 0 && t1 <= 1 && t2 >= 0 && t2 <= 1 && t1 > t2) {
                return t2;//MotionEquation(u1,v1,t2);
            }
            if (t1 >= 0 && t1 <= 1 && !(t2 >= 0 && t2 <= 1)) {
                return t1;//MotionEquation(u1,v1,t1);
            }
            if (!(t1 >= 0 && t1 <= 1) && (t2 >= 0 && t2 <= 1)) {
                return t2;//MotionEquation(u1,v1,t2);
            }

        }
        return -1;
    }
    public double collisionDetect(Agent agent, Obstacle o , Vector2D v)
    {
        return collisionDetect(agent.getPos(),o.getPos(),Vector2D.add(agent.getPos(),v),
                o.getPos(),ConfigManager.i().getAgentSize(),o.r);
    }
    public Vector2D collisionLocation(Agent agent, Vector2D v)
    {
        Optional<Double> p = obstacles.stream().
                map(obstacle -> collisionDetect(agent, obstacle, v)).
                filter(aDouble -> aDouble >= 0 && aDouble <= 1)
                .min(Double::compareTo);
        return p.map(aDouble -> MotionEquation(agent.getPos(), Vector2D.add(agent.getPos(), v), aDouble)).
                orElseGet(() -> Vector2D.add(agent.getPos(), v));

    }
    public Agent getAgent(int agent) {

        return teams.stream().flatMap(team -> team.agents.stream()).filter(agent1 -> agent1.id == agent).
                findFirst().get();
    }

    private boolean isNotInMap(Vector2D pos) {
        return pos.getX() < 0 || pos.getY() < 0 || pos.getX() > ConfigManager.i().getMapWidth() ||
                pos.getY() > ConfigManager.i().getMapHeight();
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

}

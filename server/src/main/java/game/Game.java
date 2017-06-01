package game;

import game.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Game {

    List<Team> teams;
    List<Bullet> bullets;
    List<Obstacle> obstacles;
    int time;
    int width, height;


    public Game() {
        teams = new ArrayList<>();
        bullets = new ArrayList<>();
        obstacles = new ArrayList<>();
    }

    public void setupGame() {
        width = ConfigManager.i().getMapWidth();
        height = ConfigManager.i().getMapHeight();
        Team A = new Team("Team A", this);
        Team B = new Team("Team B", this);
        A.newAgent(new Vector2D(10, 10),0);
        B.newAgent(new Vector2D(100, 100),0);
        Obstacle obstacle = new Obstacle(300, 300, 50);
        obstacles.add(obstacle);
        teams.add(A);
        teams.add(B);
    }

    public void doTurn(List<Command> commands) {
        moveBullets();

//        sync();
        removeBullets();
        commands.forEach(command -> command.apply(this));
//        world.updatev(1);
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

    public double CollisionDetect(Vector2D u1, Vector2D u2, Vector2D v1, Vector2D v2, double r1, double r2) {
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
            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
            double x2 = (-b - Math.sqrt(delta)) / (2 * a);
            if (x1 >= 0 && x1 <= 1 && x2 >= 0 && x2 <= 1 && x1 < x2) {
                return x1;//MotionEquation(u1,v1,x1);
            }
            if (x1 >= 0 && x1 <= 1 && x2 >= 0 && x2 <= 1 && x1 > x2) {
                return x2;//MotionEquation(u1,v1,x2);
            }
            if (x1 >= 0 && x1 <= 1 && !(x2 >= 0 && x2 <= 1)) {
                return x1;//MotionEquation(u1,v1,x1);
            }
            if (!(x1 >= 0 && x1 <= 1) && (x2 >= 0 && x2 <= 1)) {
                return x2;//MotionEquation(u1,v1,x2);
            }

        }
        return -1;
    }
    public double CollisionDetect(Agent agent,Obstacle o , Vector2D v)
    {
        return CollisionDetect(agent.getPos(),o.getPos(),Vector2D.add(agent.getPos(),v),
                o.getPos(),ConfigManager.i().getAgentSize(),o.r);
    }
    public Vector2D CollisionLocation(Agent agent,Vector2D v)
    {
        Optional<Double> p = obstacles.stream().
                map(obstacle -> CollisionDetect(agent, obstacle, v)).
                filter(aDouble -> aDouble >= 0 && aDouble <= 1)
                .min(Double::compareTo);
        return p.map(aDouble -> MotionEquation(agent.getPos(), Vector2D.add(agent.getPos(), v), aDouble)).
                orElseGet(() -> Vector2D.add(agent.getPos(), v));

    }
    public Agent getAgent(int agent) {

        return teams.stream().flatMap(team -> team.agents.stream()).filter(agent1 -> agent1.id == agent).findFirst().get();
    }

    private boolean isNotInMap(Vector2D pos) {
        return pos.getX() < 0 || pos.getY() < 0 || pos.getX() > ConfigManager.i().getMapWidth() || pos.getY() > ConfigManager.i().getMapHeight();
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

}

package game;

import game.command.Command;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionAdapter;
import org.dyn4j.dynamics.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Game extends CollisionAdapter
{

    List<Team> teams;
    List<Bullet> bullets;
    List<Obstacle> obstacles;
    int time;
    int width,height;

    @Override
    public boolean collision(Body body1, BodyFixture fixture1, Body body2, BodyFixture fixture2, Penetration penetration) {



        Object o1  = getObject(body1);
        Object o2  = getObject(body2);


        if(o1 instanceof Bullet) {
            body1.getLinearVelocity().zero();
            body1.setAngularVelocity(0.0);
            world.removeBody(body1);
            bullets.remove(o1);

        }

        if(o2 instanceof Bullet) {
            body2.getLinearVelocity().zero();
            body2.setAngularVelocity(0.0);
            world.removeBody(body2);
            bullets.remove(o2);
        }

        if(o1 instanceof Obstacle && o2 instanceof Agent)
        {

            body2.getLinearVelocity().zero();
            body2.setAngularVelocity(0.0);
            body2.setActive(false);
        }
        if(o2 instanceof Obstacle && o1 instanceof Agent)
        {

            body1.getLinearVelocity().zero();
            body1.setAngularVelocity(0.0);
            body1.setActive(false);
        }
        return false;
    }

    private Object getObject(Body body1) {

        Optional<Agent> opt = teams.stream().flatMap(team -> team.agents.stream()).
                filter(agent -> agent.circle == body1).findFirst();
        if(opt.isPresent())
            return opt.get();

        Optional<Bullet> opt2 = bullets.stream().filter(bullet -> bullet.circle == body1).findFirst();
            if(opt2.isPresent())
                return opt2.get();
        Optional<Obstacle> opt3 = obstacles.stream().filter(obstacle -> obstacle.circle == body1).findFirst();
        return opt3.orElse(null);
    }

    transient World world = new World();

    public Game()
    {
        teams = new ArrayList<>();
        bullets = new ArrayList<>();
        obstacles = new ArrayList<>();
        this.world.setGravity(World.ZERO_GRAVITY);
        this.world.addListener(this);
    }

    public void setupGame()
    {
        width =ConfigManager.i().getMapWidth();
        height = ConfigManager.i().getMapHeight();
        Team A = new Team("Team A" ,this);
        Team B = new Team("Team B" ,this);
        A.newAgent(new Vector2D(10,10));
        B.newAgent(new Vector2D(100,100));
        Obstacle obstacle = new Obstacle(300, 300, 50);
        this.world.addBody(obstacle.circle);
        obstacles.add(obstacle);
        teams.add(A);
        teams.add(B);
    }

    public void doTurn(List<Command> commands)
    {
        //moveBullets();

        sync();
        removeBullets();
        commands.forEach(command -> command.apply(this));
        world.updatev(1);
        time +=ConfigManager.i().getTimeStep();
    }

    private void removeBullets() {
        bullets.stream().filter(bullet -> isNotInMap(bullet.pos)).forEach(bullet -> world.removeBody(bullet.circle));
        bullets.removeIf(bullet -> isNotInMap(bullet.pos));
    }

    private void sync() {
        teams.stream().flatMap(team -> team.agents.stream()).forEach(agent -> agent.sync());
        bullets.forEach(bullet -> bullet.sync());
        obstacles.forEach(obstacle -> obstacle.sync());
    }

    private void moveBullets() {

//
        for (Bullet bullet:bullets) {
            double dx =  (ConfigManager.i().getBulletVelocity()*Math.cos(bullet.getOrientation()));
            double dy = (ConfigManager.i().getBulletVelocity()*Math.sin(bullet.getOrientation()));
            bullet.getPos().add(new Vector2D(dx,dy));
        }
    }

    public Agent getAgent(int agent) {

        return teams.stream().flatMap(team -> team.agents.stream()).filter(agent1 -> agent1.id == agent).findFirst().get();
    }

    private boolean isNotInMap(Vector2D pos)
    {
        return pos.getX()< 0 || pos.getY()< 0 || pos.getX()> ConfigManager.i().getMapWidth() || pos.getY() > ConfigManager.i().getMapHeight() ;
    }
    public List<Bullet> getBullets() {
        return bullets;
    }

    public World getWorld() {
        return world;
    }
}

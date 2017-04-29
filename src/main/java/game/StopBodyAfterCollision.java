package game;

import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionAdapter;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

/**
 * Created by pouya on 4/17/17.
 */
public class StopBodyAfterCollision {
    /** The serial version id */
    private static final long serialVersionUID = 7229897723400288930L;
    final World world = new World();
    /**
     * Custom contact listener to stop the bodies.
     * @author William Bittle
     * @version 3.2.0
     * @since 3.2.0
     */

    Body circle;
    protected void initializeWorld() {
        // create the world


        // create all your bodies/joints

        // create a circle
        // test adding some force
        circle.setLinearVelocity(new Vector2(-1.0, 0.0));
        // set some linear damping to simulate rolling friction
        this.world.addBody(circle);

        // try a rectangle
        Body rectangle = new Body();
        rectangle.addFixture(Geometry.createRectangle(1, 1));
        rectangle.setMass(MassType.NORMAL);
        rectangle.translate(0.0, 2.0);
        this.world.addBody(rectangle);

    }

    /**
     * Entry point for the example application.
     * @param args command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        StopBodyAfterCollision simulation = new StopBodyAfterCollision();
        simulation.initializeWorld();
        while (true) {

            System.out.println(simulation.circle.getWorldCenter().toString());
            simulation.world.updatev(1);
            Thread.sleep(100);

        }
    }
}
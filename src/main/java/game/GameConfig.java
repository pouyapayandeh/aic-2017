package game;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public interface GameConfig
{
    int getMaxVelocity();
    float getMaxW();
    int getBaseHealth();

    int getTimeStep();
}

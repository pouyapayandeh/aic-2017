package game;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class MockConfig implements GameConfig
{
    @Override
    public int getMaxVelocity()
    {
        return 10;
    }

    @Override
    public float getMaxW()
    {
        return 5;
    }

    @Override
    public int getBaseHealth()
    {
        return 1;
    }

    @Override
    public int getTimeStep()
    {
        return 1;
    }

    @Override
    public double getBulletVelocity() {
        return 100;
    }

    @Override
    public int getAgentSize() {
        return 10;
    }

    @Override
    public int getBulletSize() {
        return 1;
    }

    @Override
    public int getMapWidth() {
        return 500;
    }

    @Override
    public int getMapHeight() {
        return 500;
    }
}

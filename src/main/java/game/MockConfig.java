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
}

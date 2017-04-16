package game;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class ConfigManager
{
    private static GameConfig instance;

    public static GameConfig i()
    {
        if(instance == null)
            instance = new MockConfig();
        return instance;
    }
}

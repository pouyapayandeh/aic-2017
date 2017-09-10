package game;

import com.beust.jcommander.JCommander;
import network.GameManager;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class Main
{
    public static void main(String[] args)
    {
        ServerArgs serverArgs = new ServerArgs();
        new JCommander(serverArgs,args);
        GameManager manager = new GameManager(serverArgs.port,serverArgs.map,serverArgs.logDir);
        manager.startGame();
    }
}

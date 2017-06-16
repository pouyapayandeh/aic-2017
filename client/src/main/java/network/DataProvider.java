package network;

import game.*;
import game.command.Command;

import java.util.List;

/**
 * Created by Pouya Payandeh on 6/6/2017.
 */
public class DataProvider
{
    public Game readGame()
    {
        Game game = new Game();
        game.setupGame();
        return game;
    }
    public int  getTeamId()
    {
        return 0;
    }

    public void sendCommands(List<Command> commands)
    {

    }
}

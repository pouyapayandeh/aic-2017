package network;

import game.*;

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
}

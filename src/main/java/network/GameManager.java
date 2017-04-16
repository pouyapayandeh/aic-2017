package network;

import com.google.gson.Gson;
import game.Game;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class GameManager
{
    Game game = new Game();
    GameServer gameServer;
    Thread thread ;
    public GameManager()
    {
        gameServer = new GameServer(8888);
    }
    public void startGame()
    {
        thread = new Thread(this::loop);
        game.setupGame();
        thread.start();
    }
    public void loop()
    {
        Gson gson = new Gson();
        while(true)
        {
            game.doTurn();
            String msg = gson.toJson(game);
            System.out.println(msg);
            gameServer.broadcast(msg);
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


}

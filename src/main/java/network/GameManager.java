package network;

import com.google.gson.Gson;
import game.Game;
import game.Vector2D;
import game.command.Command;
import game.command.MoveCommand;
import game.command.RotateCommand;
import game.command.ShootCommand;

import java.util.ArrayList;

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
            ArrayList<Command> commands = new ArrayList<>();
            MoveCommand cmd = new MoveCommand(0, 0, new Vector2D(15, 15));


            commands.add(cmd);
            commands.add(new RotateCommand(1,1,Math.PI/16));
            commands.add(new ShootCommand(1,1));
            game.doTurn(commands);
            String msg = gson.toJson(game);
           // System.out.println(msg);
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

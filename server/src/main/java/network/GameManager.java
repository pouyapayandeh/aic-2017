package network;

import com.google.gson.Gson;
import game.Game;
import game.Vector2D;
import game.command.Command;
import game.command.MoveCommand;
import game.command.RotateCommand;
import game.command.ShootCommand;
import org.java_websocket.WebSocket;

import java.net.URISyntaxException;
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
        try
        {
            game.setupGame(this.getClass().getResource("../map1.txt").toURI());
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        thread.start();
    }
    public void loop()
    {
        Gson gson = new Gson();
        while(true)
        {
            ArrayList<Command> commands = new ArrayList<>();
//            MoveCommand cmd = new MoveCommand(0, 0, new Vector2D(3, 3));
//
//
//            commands.add(cmd);
//            commands.add(new RotateCommand(0,1,Math.PI/16));
////            commands.add(new ShootCommand(1,0));

            game.doTurn(commands);



           // System.out.println(msg);
            String msg = gson.toJson(game);
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
    public void onMessage(WebSocket socket,String s)
    {

    }

}

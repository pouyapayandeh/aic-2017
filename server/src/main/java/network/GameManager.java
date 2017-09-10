package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.Game;
import game.Vector2D;
import game.command.Command;
import game.command.MoveCommand;
import game.command.RotateCommand;
import game.command.ShootCommand;
import org.java_websocket.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class GameManager
{
    Game game = new Game();
    GameServer gameServer;
    Thread thread ;
    Gson gson;
    URI mapfile;
    List<Command> commands = Collections.synchronizedList(new ArrayList<Command>());
    public GameManager(int port, String map, String logDir)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Command.class, new CommandAdapter());
        gson = builder.create();
        gameServer = new GameServer(port);
        gameServer.setOnMessage(this::onMessage);
        mapfile = Paths.get(map).toUri();

    }

    public void startGame()
    {
        thread = new Thread(this::loop);
        game.setupGame(mapfile);
        thread.start();
    }
    private void loop()
    {
        while(true)
        {
            System.out.println(commands);
            game.doTurn(commands);
            commands.clear();
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
    private void onMessage(WebSocket socket, String s)
    {
        commands.add(gson.fromJson(s,Command.class));
    }

}

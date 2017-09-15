package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.Game;
import game.Team;
import game.Vector2D;
import game.command.Command;
import game.command.MoveCommand;
import game.command.RotateCommand;
import game.command.ShootCommand;
import game.logger.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.Socket;
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
    List<WebSocket> sockets = new ArrayList<>();
    List<Boolean> headerFlags = new ArrayList<>();
    List<Command> commands = Collections.synchronizedList(new ArrayList<Command>());
    Logger mainLogger ;
    int players = 0;
    public GameManager(int port, String map, String logDir)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Command.class, new CommandAdapter());
        gson = builder.create();
        gameServer = new GameServer(port);
        gameServer.setOnMessage(this::onMessage);
        gameServer.setOnConnect(this::onConnect);
        mapfile = Paths.get(map).toUri();
        mainLogger = new Logger(logDir+"/game.json");

    }

    private synchronized void onConnect(WebSocket socket, ClientHandshake clientHandshake)
    {
        socket.send(gson.toJson(new Header(sockets.size())));
        sockets.add(socket);
        headerFlags.add(true);
    }

    public void startGame()
    {
        thread = new Thread(this::loop);
        game.setupGame(mapfile);
        thread.start();
    }
    private void loop()
    {
        while(!isFinished())
        {
            if(players > 1)
            {
                System.out.println(commands);
                game.doTurn(commands);
                commands.clear();
                String msg = gson.toJson(game);
                gameServer.broadcast(msg);
                mainLogger.addStringLog(msg);
            }
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        mainLogger.finished();
    }

    private boolean isFinished()
    {
        return game.getTotalTurns() < game.getTime();
    }

    private void onMessage(WebSocket socket, String s)
    {
        if(isHeaderMsg(socket))
        {
            Header header = gson.fromJson(s,Header.class);
            setTeamName(header);
            players ++ ;
        }else
        {
            commands.add(gson.fromJson(s,Command.class));
        }

    }

    private void setTeamName(Header header)
    {
        Team team = game.getTeams().get(header.teamId);
        team.setName(header.teamName);
        System.out.printf("Team Name %d Set To %s \n",header.teamId,header.teamName);
    }

    private boolean isHeaderMsg(WebSocket socket)
    {
        int index = sockets.indexOf(socket);
        if(headerFlags.get(index))
        {
            headerFlags.set(index,false);
            return true;
        }
        return false;

    }

}

package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.Game;
import game.command.Command;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya Payandeh on 6/6/2017.
 */
public class NetworkDataProvider extends DataProvider
{
    Client client;
    Gson gson;
    int teamId =0 ;
    String teamName;
    public NetworkDataProvider()
    {
        this("localhost",8888,"No Name");
    }
    public NetworkDataProvider(String host,int port , String name)
    {
        super();
        teamName = name;
        System.out.println("Running");
        try
        {
            client = new Client(new URI("ws://"+host+":"+port));
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Command.class,new  CommandAdapter());
        gson = builder.create();

        client.setOnHeader(this::onHeader);
        client.connect();

    }

    private void onHeader(String s)
    {
        teamId = gson.fromJson(s,Header.class).teamId;
        System.out.println("Team Id : " + teamId);
        Header header = new Header(teamId,teamName);
        String msg = gson.toJson(header, Header.class);
        client.send(msg+"\n");
    }

    @Override
    public Game readGame()
    {
        String msg = client.getData();
        return gson.fromJson(msg,Game.class);
    }

    @Override
    public int getTeamId()
    {
        return teamId;
    }

    @Override
    public void sendCommands(List<Command> commands)
    {
        for (Command command:commands
             )
        {
            String msg = gson.toJson(command, Command.class);
            client.send(msg+"\n");
            System.out.println(msg);
        }

    }
}

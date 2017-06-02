import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import game.command.Command;
import game.command.ShootCommand;
import network.CommandAdapter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pouya Payandeh on 6/2/2017.
 */
public class jsonTestUnit
{
    @Test
    public void testSerialize()
    {
        Command c = new ShootCommand(10, 20);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Command.class, new CommandAdapter());
        Gson gson = builder.create();
        JsonElement elem = gson.toJsonTree(c, Command.class);
        assertEquals(elem.getAsJsonObject().has("type"), true);

    }
    @Test
    public void testDeserialize()
    {
        Command c = new ShootCommand(10,20);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Command.class,new  CommandAdapter());
        Gson gson = builder.create();
        String elem = gson.toJson(c,Command.class);
        Command cmd = gson.fromJson(elem,Command.class);
        assertEquals(cmd instanceof ShootCommand,true);

    }

}

package network;

import com.google.gson.*;
import game.command.Command;

import java.lang.reflect.Type;

/**
 * Created by Pouya Payandeh on 6/2/2017.
 */
public class CommandAdapter implements JsonDeserializer<Command> , JsonSerializer<Command>
{
    @Override
    public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        String type = json.getAsJsonObject().get("type").getAsString();
        try
        {
            Command cmd = context.deserialize(json, Class.forName(type));
            return cmd;
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonElement serialize(Command src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject e = (JsonObject) context.serialize(src);
        e.addProperty("type",src.getClass().getName());
        return e;
    }
}

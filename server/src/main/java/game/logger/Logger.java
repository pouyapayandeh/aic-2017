package game.logger;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya Payandeh on 9/15/2017.
 */
public class Logger
{
    String path;
    List<String> logs = new ArrayList<>();
    public Logger(String path)
    {
        this.path = path;
    }
    public void addStringLog(String entry)
    {
        logs.add(entry);
    }
    public void finished()
    {
        try(PrintWriter writer = new PrintWriter(path))
        {
            writer.println("{");
            writer.println("\"data\":[");
            for (int i = 0; i < logs.size(); i++)
            {
                writer.print(logs.get(i));
                if(i < logs.size() -1)
                    writer.println(",");
            }
            writer.println("\n]}");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}

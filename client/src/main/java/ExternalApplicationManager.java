import java.io.*;
import java.util.Scanner;

/**
 * Created by Pouya Payandeh on 6/6/2017.
 */
public class ExternalApplicationManager
{
    String path;
    Process process;
    private Scanner scanner;
    private PrintWriter writer;

    public ExternalApplicationManager(String path)
    {
        this.path = path;
    }
    public ExternalApplicationManager()
    {

    }
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
    //todo make start return boolean
    public void start()
    {
        try
        {
            process = Runtime.getRuntime().exec(path);
            Runtime.getRuntime().addShutdownHook(new Thread(() ->
            {
                process.destroy();
            }));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        scanner = new Scanner(new BufferedInputStream(process.getInputStream()));
        writer = new PrintWriter(process.getOutputStream());

    }

    public Scanner getScanner()
    {
        return scanner;
    }

    public PrintWriter getWriter()
    {
        return writer;
    }
}

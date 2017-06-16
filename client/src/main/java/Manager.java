import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import network.Client;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pouya on 5/30/17.
 */
public class Manager {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        ClientArgs clientArgs = new ClientArgs();
        try
        {
            JCommander jCommander = new JCommander(clientArgs,args);
            System.out.println(clientArgs.cmd);
            Interpreter i = new Interpreter(clientArgs.cmd,clientArgs.host,clientArgs.port);
            i.cycle();
        }catch (ParameterException e)
        {
            System.err.println(e.getMessage());
        }
    }
}

import com.beust.jcommander.Parameter;

/**
 * Created by Pouya Payandeh on 6/16/2017.
 */
public class ClientArgs
{
    @Parameter(names = "--help", help = true)
    public boolean help;
    @Parameter(names = {"-c","--command"} ,description = "Path of the program to interact with",required = true)
    public String cmd;
    @Parameter(names = {"-h","--host"} ,description = "remote host to connect",arity = 1)
    public String host = "localhost";
    @Parameter(names = {"-p" , "--port"},description = "remote port to connect")
    public int port = 8888;

}

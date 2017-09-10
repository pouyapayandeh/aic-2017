package game;

import com.beust.jcommander.Parameter;

/**
 * Created by Pouya Payandeh on 6/16/2017.
 */
public class ServerArgs
{
    @Parameter(names = "--help", help = true)
    public boolean help;
    @Parameter(names = {"-m","--map"} ,description = "Path of the map",required = true)
    public String map;
    @Parameter(names = {"-l","--log"} ,description = "Path of the log dir")
    public String logDir = "./";
    @Parameter(names = {"-p" , "--port"},description = "port to listen")
    public int port = 8888;

}

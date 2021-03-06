import game.Game;
import game.Vector2D;
import game.command.Command;
import game.command.MoveCommand;
import game.command.RotateCommand;
import game.command.ShootCommand;
import network.DataProvider;
import network.NetworkDataProvider;
import sun.swing.BakedArrayList;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pouya Payandeh on 6/6/2017.
 */
public class Interpreter
{
    DataProvider dataProvider;
    ExternalApplicationManager applicationManager;
    public Interpreter()
    {
        dataProvider = new NetworkDataProvider();
        applicationManager = new ExternalApplicationManager();
    }
    public Interpreter(String cmd,String name ,String host,int port)
    {
        dataProvider = new NetworkDataProvider(host,port , name);
        applicationManager = new ExternalApplicationManager(cmd);
    }
    public void cycle()
    {
        applicationManager.start();
        PrintWriter p = applicationManager.getWriter();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //        while (true)
        {
            Game g = dataProvider.readGame();
            Scanner scanner = applicationManager.getScanner();
            writeMap(g);
            writeTeams(g);
            p.println(dataProvider.getTeamId());

            p.flush();

            while (true)
            {
//              waitForData()
                g = dataProvider.readGame();
                writeTeams(g);
                p.flush();

                // waiting for output
                List<Command> commands = new ArrayList<>();

                Thread thread = new Thread(() ->
                {
                    while (true)
                    {
                        String command = scanner.next();
                        if(command.equals("fin"))
                            break;
                        else
                        {
                            int agentId = scanner.nextInt();

                            if(command.equals("move"))
                            {

                                double dX = scanner.nextDouble();
                                double dY = scanner.nextDouble();
                                commands.add(new MoveCommand(dataProvider.getTeamId() ,agentId,new Vector2D(dX,dY)));
                            }

                            if(command.equals("rotate"))
                            {

                                double dW = scanner.nextDouble();
                                commands.add(new RotateCommand(dataProvider.getTeamId(),agentId,dW));
                            }
                            if(command.equals("shoot"))
                            {
                                commands.add(new ShootCommand(dataProvider.getTeamId() ,agentId));
                            }

                        }
                    }
                });
                thread.start();
                try
                {
                    thread.join(120000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.out.println(commands);
//                Write Data Back
                dataProvider.sendCommands(commands);
            }
        }
    }

    private void writeTeams(Game g)
    {
        PrintWriter p = applicationManager.getWriter();
        g.getTeams().forEach(team ->
        {
            p.println(team.getId());
            p.println(team.getName());
            p.println(team.getAgents().size());

            team.getAgents().forEach(agent ->
            {
                p.println(agent.getId());
                p.println(agent.getPos().getX());
                p.println(agent.getPos().getY());
                p.println(agent.getOrientation());
            });
        })
        ;
    }

    private void writeMap(Game g)
    {
        PrintWriter p = applicationManager.getWriter();
        p.println(g.getTotalTurns());
        p.println(g.getTeams().size());
        p.println(g.getWidth());
        p.println(g.getHeight());
        p.println(g.getObstacles().size());

        g.getObstacles().forEach(obstacle ->
        {
            p.printf("%f %f %f\n", obstacle.getPos().getX(), obstacle.getPos().getY(), obstacle.getR());
        });
    }
}

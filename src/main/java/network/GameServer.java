package network;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by Pouya Payandeh on 4/11/2017.
 */
public class GameServer extends WebSocketServer
{
    Logger logger = LoggerFactory.getLogger(GameServer.class);
    GameServer(int port)
    {
        super(new InetSocketAddress(port));
        start();
        logger.info("Game Server Started On Port:"+port);

    }
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake)
    {
        logger.info("A Client Connected");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b)
    {
        logger.info("A Client Disconnected");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s)
    {

    }

    @Override
    public void onError(WebSocket webSocket, Exception e)
    {

    }

    public void broadcast(String msg)
    {
        for (WebSocket conn: connections())
        {
            conn.send(msg);
        }
    }
}

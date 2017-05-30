package network;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Pouya Payandeh on 4/11/2017.
 */
public class GameClient extends WebSocketClient
{
    public GameClient(URI uri)
    {
        super(uri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake)
    {

    }

    @Override
    public void onMessage(String s)
    {

    }

    @Override
    public void onClose(int i, String s, boolean b)
    {

    }

    @Override
    public void onError(Exception e)
    {

    }

//    public static void main(String[] args) throws URISyntaxException, InterruptedException
//    {
//        GameClient gc = new GameClient(new URI("ws://localhost:8887"));
//        gc.connect();
//        Thread.sleep(1000);
//        gc.close();
//
//    }
}

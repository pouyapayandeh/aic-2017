package network;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by pouya on 5/30/17.
 */
public class Client   extends GameClient{
    public Client(URI uri) {
        super(uri);
    }
    ArrayBlockingQueue<String> msgs = new ArrayBlockingQueue<String>(10);
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        super.onOpen(serverHandshake);
        System.out.println("Client Connected");
    }

    @Override
    public void onMessage(String s)
    {
        msgs.add(s);
    }

    public String getData()
    {
        try
        {
            return msgs.take();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

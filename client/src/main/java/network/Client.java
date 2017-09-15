package network;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

/**
 * Created by pouya on 5/30/17.
 */
public class Client   extends GameClient{
    private boolean firstMsg=true;

    public Client(URI uri) {
        super(uri);
    }
    ArrayBlockingQueue<String> msgs = new ArrayBlockingQueue<String>(10);

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        super.onOpen(serverHandshake);
        System.out.println("Client Connected");
    }
    Consumer<String> onHeader;

    public void setOnHeader(Consumer<String> onHeader)
    {
        this.onHeader = onHeader;
    }

    @Override
    public  void onMessage(String s)
    {

        if(firstMsg)
        {
            firstMsg = false;
            onHeader.accept(s);
        }
        else
        {
            msgs.add(s);
        }
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

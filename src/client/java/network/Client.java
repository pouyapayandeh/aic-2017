package network;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by pouya on 5/30/17.
 */
public class Client   extends GameClient{
    public Client(URI uri) {
        super(uri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        super.onOpen(serverHandshake);
        System.out.println("Client Connected");
    }
}

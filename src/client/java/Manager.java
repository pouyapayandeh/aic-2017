import network.Client;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pouya on 5/30/17.
 */
public class Manager {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        Client client = new Client(new URI("ws://localhost:8888"));
        client.connect();
        Thread.sleep(100000);
    }
}

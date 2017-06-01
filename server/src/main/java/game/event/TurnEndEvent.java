package game.event;

import java.util.Observable;

/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
public class TurnEndEvent extends Observable
{
    public void doEvent(Object obj)
    {
        setChanged();
        notifyObservers(obj);
    }
}

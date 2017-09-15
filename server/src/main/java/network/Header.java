package network;

/**
 * Created by Pouya Payandeh on 9/10/2017.
 */
public class Header
{
    public int teamId;
    public String teamName;
    public Header(int teamId)
    {
        this.teamId = teamId;
    }

    public Header(int teamId, String teamName)
    {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}

package sk.spedry.weebcollector.app.controllers.util.lists;

import sk.spedry.weebcollector.app.controllers.util.WCMServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerList implements Serializable {
    private final List<WCMServer> list;

    public ServerList() {
        this.list = new ArrayList<WCMServer>();
    }

    public List<WCMServer> getServerList() {
        return list;
    }

    public void addServer(WCMServer server) {
        this.list.add(server);
    }

    public int getSize() {
        return this.list.size();
    }
}

package sk.spedry.weebcollector.app.controllers.util;

import lombok.Getter;

public class WCMServer {
    @Getter
    private int id;
    @Getter
    private final String serverName;
    @Getter
    private final String serveChannelName;

    public WCMServer(String serverName, String serveChannelName) {
        this.serverName = serverName;
        this.serveChannelName = serveChannelName;
    }
}

package sk.spedry.weebcollector.app.controllers.util;

import lombok.Getter;

public class WCMSetup {
    @Getter
    private final String userName;
    @Getter
    private final String downloadFolder;
    @Getter
    private final String serverName;
    @Getter
    private final String channelName;

    public WCMSetup(String userName, String downloadFolder, String serverName, String channelName) {
        this.userName = userName;
        this.downloadFolder = downloadFolder;
        this.serverName = serverName;
        this.channelName = channelName;
    }
}

package sk.spedry.weebcollector.app.controllers.util;

import lombok.Getter;

public class WCMSetup {
    @Getter
    private final String userName;
    @Getter
    private final String downloadFolder;

    public WCMSetup(String userName, String downloadFolder) {
        this.userName = userName;
        this.downloadFolder = downloadFolder;
    }
}

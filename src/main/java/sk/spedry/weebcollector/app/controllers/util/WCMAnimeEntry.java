package sk.spedry.weebcollector.app.controllers.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class WCMAnimeEntry {
    @Getter
    private int id;
    @Getter
    private final String animeName;
    @Getter
    private final String typeOfQuality;
    @Getter
    private final String serverName;
    @Getter
    @Setter
    private String numberOfEpisode;
    // TODO FOR FUTURE private String status;

    public WCMAnimeEntry(@NonNull String anime, @NonNull String typeOfQuality, @NonNull String serverName, String numberOfEpisode) {
        this.animeName = anime;
        this.typeOfQuality = typeOfQuality;
        this.serverName = serverName;
        this.numberOfEpisode = numberOfEpisode;
    }

    public WCMAnimeEntry(@NonNull String anime, @NonNull String typeOfQuality, @NonNull String serverName) {
        this.animeName = anime;
        this.typeOfQuality = typeOfQuality;
        this.serverName = serverName;
    }
}

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
    private final String botName;
    @Getter
    @Setter
    private String numberOfEpisodes;
    // TODO FOR FUTURE private String status;

    public WCMAnimeEntry(@NonNull String anime, @NonNull String typeOfQuality, String botName, String numberOfEpisodes) {
        this.animeName = anime;
        this.typeOfQuality = typeOfQuality;
        this.botName = botName;
        this.numberOfEpisodes = numberOfEpisodes;
    }
}

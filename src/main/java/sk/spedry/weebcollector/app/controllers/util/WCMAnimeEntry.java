package sk.spedry.weebcollector.app.controllers.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;

import java.time.LocalDate;

public class WCMAnimeEntry {
    @Getter
    @Setter
    private int id;
    @Getter
    private final String animeName;
    @Getter
    private final CodeTable typeOfQuality;
    @Getter
    private final String botName;
    @Getter
    @Setter
    private int numberOfEpisodes;
    @Getter
    @Setter
    private int numberOfDownloadedEpisodes;
    @Getter
    @Setter
    private boolean wasDownloaded;
    @Getter
    @Setter
    private LocalDate releaseDate;
    // TODO FOR FUTURE private String status;

    public WCMAnimeEntry(@NonNull String anime, @NonNull CodeTable typeOfQuality, String botName, int numberOfEpisodes) {
        this.animeName = anime;
        this.typeOfQuality = typeOfQuality;
        this.botName = botName;
        this.numberOfEpisodes = numberOfEpisodes;
    }
    // TODO DELETE LATER
    public WCMAnimeEntry(@NonNull String anime, @NonNull CodeTable typeOfQuality, String botName, int numberOfEpisodes, int numberOfDownloadedEpisodes) {
        this.animeName = anime;
        this.typeOfQuality = typeOfQuality;
        this.botName = botName;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfDownloadedEpisodes = numberOfDownloadedEpisodes;
    }
}

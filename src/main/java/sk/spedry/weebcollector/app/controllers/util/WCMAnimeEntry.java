package sk.spedry.weebcollector.app.controllers.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;

import java.time.DayOfWeek;

public class WCMAnimeEntry {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String animeName;
    @Getter
    @Setter
    private CodeTable typeOfQuality;
    @Getter
    private String botName;
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
    private boolean missedEpisode;
    @Getter
    @Setter
    private DayOfWeek releaseDay;
    // TODO FOR FUTURE private String status;

    public WCMAnimeEntry(@NonNull String anime, @NonNull CodeTable typeOfQuality, String botName, int numberOfEpisodes) {
        this.animeName = anime;
        this.typeOfQuality = typeOfQuality;
        this.botName = botName;
        this.numberOfEpisodes = numberOfEpisodes;
    }
}

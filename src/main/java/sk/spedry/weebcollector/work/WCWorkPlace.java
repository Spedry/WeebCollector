package sk.spedry.weebcollector.work;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sk.spedry.weebcollector.app.controllers.PreferencesPopupController;
import sk.spedry.weebcollector.app.controllers.WeebCollectorController;
import sk.spedry.weebcollector.app.controllers.cell.AnimeCell;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMSetup;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.app.controllers.util.lists.AnimeList;

public class WCWorkPlace {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final WCUtilManager manager = new WCUtilManager();

    public void addNewAnimeEntry(WCMessage wcMessage) {
        final ObservableList<WCMAnimeEntry> wcmAnimeEntryObservableList = WeebCollectorController.wcmAnimeEntryObservableList;
        Platform.runLater(() -> {
            wcmAnimeEntryObservableList.clear();
            AnimeList animeList = new Gson().fromJson(wcMessage.getMessageBody(), AnimeList.class);
            wcmAnimeEntryObservableList.addAll(animeList.getAnimeList());
            for (WCMAnimeEntry animeEntry : wcmAnimeEntryObservableList) {
                logger.info(animeEntry.getAnimeName());
            }
        });
    }

    public void setSetup(WCMessage wcMessage) {
        PreferencesPopupController.setup = new Gson().fromJson(wcMessage.getMessageBody(), WCMSetup.class);
    }

    public void setDownloadingAnimeProgressBar(WCMessage wcMessage) {
        double progress = new Gson().fromJson(wcMessage.getMessageBody(), double.class);
        WeebCollectorController.barUpdater.set(progress);
    }

    public void setDownloadingAnimeName(WCMessage wcMessage) {
        Platform.runLater(() -> {
            String animeName = new Gson().fromJson(wcMessage.getMessageBody(), String.class);
            WeebCollectorController.labelUpdate.set("Downloading: " + animeName);
        });
    }

    public void setAnimeToOpen(WCMessage wcMessage) {
        String animeToOpen = new Gson().fromJson(wcMessage.getMessageBody(), String.class);
        AnimeCell.setAnimeToOpen(animeToOpen);
    }
}

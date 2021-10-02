package sk.spedry.weebcollector.work;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.AddNewAnimeController;
import sk.spedry.weebcollector.app.controllers.PreferencesPopupController;
import sk.spedry.weebcollector.app.controllers.WeebCollectorController;
import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMServer;
import sk.spedry.weebcollector.app.controllers.util.WCMSetup;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.app.controllers.util.lists.AnimeList;
import sk.spedry.weebcollector.app.controllers.util.lists.ServerList;

public class WCWorkPlace {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final WCUtilManager manager = new WCUtilManager();

    public void serverList(WCMessage wcMessage) {
        final ObservableList<CodeTable> ircServerObservableList = AddNewAnimeController.ircServerObservableList;
        ircServerObservableList.clear();
        ServerList serverList = new Gson().fromJson(wcMessage.getMessageBody(), ServerList.class);
        for (WCMServer server : serverList.getServerList()) {
            ircServerObservableList.add(manager.wcmServerToCodeTable(server));
        }
    }

    public void addNewAnimeEntry(WCMessage wcMessage) {
        final ObservableList<WCMAnimeEntry> wcmAnimeEntryObservableList = WeebCollectorController.wcmAnimeEntryObservableList;
        wcmAnimeEntryObservableList.clear();
        AnimeList animeList = new Gson().fromJson(wcMessage.getMessageBody(), AnimeList.class);
        wcmAnimeEntryObservableList.addAll(animeList.getAnimeList());
        for (WCMAnimeEntry animeEntry : animeList.getAnimeList()) {
            logger.info(animeEntry.getAnimeName());
        }
    }

    public void setSetup(WCMessage wcMessage) {
        WCMSetup setup = new Gson().fromJson(wcMessage.getMessageBody(), WCMSetup.class);
        PreferencesPopupController.userName = setup.getUserName();
        logger.info(setup.getDownloadFolder());
        PreferencesPopupController.downloadFolder = setup.getDownloadFolder();
    }
}

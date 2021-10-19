package sk.spedry.weebcollector.app.controllers;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.app.controllers.util.exteders.AnimeController;

import java.io.PrintWriter;

public class EditAnimeController extends AnimeController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final int id;

    public EditAnimeController(PrintWriter out, WCMAnimeEntry animeEntry) {
        super(out);
        this.id = animeEntry.getId();
        animeNameTextField.setText(animeEntry.getAnimeName());
        qualityChoiceBox.setValue(animeEntry.getTypeOfQuality());
        botTextField.setText(animeEntry.getBotName());
        numberOfEpisodesTextField.setText(animeEntry.getNumberOfEpisodes());
        logger.trace("Creating AddNewAnimeController");
    }

    @FXML
    @Override
    public void onActionSave() {
        String botName = null, numberOfEp = null;
        if (!numberOfEpisodesTextField.getText().isEmpty())
            numberOfEp = numberOfEpisodesTextField.getText();
        if (!botTextField.getText().isEmpty())
            botName = botTextField.getText();
        WCMAnimeEntry animeEntry = new WCMAnimeEntry(animeNameTextField.getText(), qualityChoiceBox.getValue(), botName, numberOfEp);
        animeEntry.setId(animeEntry.getId());
        WeebCollectorController.wcmAnimeEntryObservableList.set(id, animeEntry);
        sendMessage(new WCMessage("updateAnime", animeEntry));
        onActionCancel();
    }
}

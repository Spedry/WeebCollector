package sk.spedry.weebcollector.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.app.controllers.util.exteders.AnimeController;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAnimeController extends AnimeController implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final WCMAnimeEntry animeEntry;

    public EditAnimeController(PrintWriter out, WCMAnimeEntry animeEntry) {
        super(out);
        this.animeEntry = animeEntry;
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
        WeebCollectorController.wcmAnimeEntryObservableList.set(animeEntry.getId(), animeEntry);
        sendMessage(new WCMessage("updateAnime", animeEntry));
        onActionCancel();
    }

    private void fillFields() {
        animeNameTextField.setText(animeEntry.getAnimeName());
        qualityChoiceBox.getSelectionModel().select(animeEntry.getTypeOfQuality().getId());
        botTextField.setText(animeEntry.getBotName());
        numberOfEpisodesTextField.setText(animeEntry.getNumberOfEpisodes());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.trace("Initializing");
        initChoiceBox();
        setHandler();
        fillFields();
    }
}

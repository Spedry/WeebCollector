package sk.spedry.weebcollector.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMDownAlrRel;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.app.controllers.util.exteders.AnimeController;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewAnimeController extends AnimeController implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public AddNewAnimeController(PrintWriter out) {
        super(out);
        logger.trace("Creating AddNewAnimeController");
    }

    @FXML
    @Override
    public void onActionSave() {
        String botName = null;
        int numberOfEp = 0, numberOfEpToDownload = 0;
        if (!numberOfEpisodesTextField.getText().isEmpty())
            numberOfEp = Integer.parseInt(numberOfEpisodesTextField.getText());
        if (!botTextField.getText().isEmpty())
            botName = botTextField.getText();

        if (check) {
            if (!numberOfEpisodesTextField2.getText().isEmpty())
                numberOfEp = Integer.parseInt(numberOfEpisodesTextField2.getText());
            if (!numberOfEpisodesToDownloadTextField.getText().isEmpty())
                numberOfEpToDownload = Integer.parseInt(numberOfEpisodesToDownloadTextField.getText());
            sendMessage(new WCMessage("addNewAnimeEntry", new WCMAnimeEntry(
                    animeNameTextField.getText(),
                    qualityChoiceBox.getValue(),
                    botName,
                    numberOfEp),
                    new WCMDownAlrRel(check, numberOfEpToDownload)));
        }
        else {
            sendMessage(new WCMessage("addNewAnimeEntry", new WCMAnimeEntry(
                    animeNameTextField.getText(),
                    qualityChoiceBox.getValue(),
                    botName,
                    numberOfEp)));
        }
        //TODO
        // ADD OPTIONS TO CHOOSE IF USER WANT TO CLOSE STAGE
        // AFTER ADDING NEW ANIME ENTRY
        onActionCancel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.trace("Initializing");
        initChoiceBox();
        setHandler();
        hBox.setVisible(false);
    }
}

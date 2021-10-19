package sk.spedry.weebcollector.app.controllers;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.app.controllers.util.exteders.AnimeController;

import java.io.PrintWriter;

public class AddNewAnimeController extends AnimeController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public AddNewAnimeController(PrintWriter out) {
        super(out);

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

        sendMessage(new WCMessage("addNewAnimeEntry", new WCMAnimeEntry(
                animeNameTextField.getText(),
                qualityChoiceBox.getValue(),
                botName,
                numberOfEp)));
        //sendMessage(new WCMessage("getAnimeList"));
        //TODO
        // ADD OPTIONS TO CHOOSE IF USER WANT TO CLOSE STAGE
        // AFTER ADDING NEW ANIME ENTRY
        onActionCancel();
    }
}

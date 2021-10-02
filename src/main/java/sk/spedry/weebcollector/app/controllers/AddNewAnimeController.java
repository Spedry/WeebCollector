package sk.spedry.weebcollector.app.controllers;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMServer;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewAnimeController extends ClientMessageSender implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @FXML
    private TextField animeNameTextField;
    @FXML
    private ChoiceBox<CodeTable> serverChoiceBox;
    @FXML
    private ChoiceBox<CodeTable> qualityChoiceBox;
    @FXML
    private TextField numberOfEpisodesTextField;

    private final ObservableList<CodeTable> qualityTypeObservableList = FXCollections
            .observableArrayList(
                    new CodeTable(1, "480p"),
                    new CodeTable(2, "540p"),
                    new CodeTable(3, "720p"),
                    new CodeTable(4, "1080p"));

    public static ObservableList<CodeTable> ircServerObservableList = FXCollections.observableArrayList();

    public AddNewAnimeController(PrintWriter out) {
        super(out);

        logger.trace("Creating AddNewAnimeController");
    }

    @FXML
    public void onActionSave() {
        logger.info("TODO save button --> sending new anime to bot");
        WCMAnimeEntry wcmAnimeEntry;
        if (numberOfEpisodesTextField.getText().isEmpty()) {
            wcmAnimeEntry = new WCMAnimeEntry(animeNameTextField.getText(),
                    qualityChoiceBox.getValue().getName(),
                    serverChoiceBox.getValue().getName());
        } else {
            wcmAnimeEntry = new WCMAnimeEntry(animeNameTextField.getText(),
                    qualityChoiceBox.getValue().getName(),
                    serverChoiceBox.getValue().getName(),
                    numberOfEpisodesTextField.getText());
        }
        sendMessage(new WCMessage("addNewAnimeEntry", wcmAnimeEntry));
        //TODO
        // ADD OPTIONS TO CHOOSE IF USER WANT TO CLOSE STAGE
        // AFTER ADDING NEW ANIME ENTRY
        onActionCancel();
    }

    @FXML
    public void onActionCancel() {
        logger.debug("Closing scene");
        Stage stage = (Stage) animeNameTextField.getScene().getWindow();
        stage.close();
    }

    private void initChoiceBox() {
        logger.debug("initializing Choice Box");
        qualityChoiceBox.getItems().setAll(qualityTypeObservableList);
        serverChoiceBox.getItems().setAll(ircServerObservableList);
        for (CodeTable codeTable : ircServerObservableList) {
            logger.info(codeTable.getName());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.trace("Initializing");
        initChoiceBox();
    }
}

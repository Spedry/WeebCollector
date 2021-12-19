package sk.spedry.weebcollector.app.controllers.util.exteders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.PrintWriter;

public class AnimeController extends ClientMessageSender {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @FXML
    public TextField animeNameTextField;
    @FXML
    public TextField botTextField;
    @FXML
    public ChoiceBox<CodeTable> qualityChoiceBox;
    @FXML
    public TextField numberOfEpisodesTextField;
    @FXML
    public CheckBox checkBox;
    @FXML
    public HBox hBox;
    @FXML
    public TextField numberOfEpisodesToDownloadTextField;
    public boolean check = false;
    @FXML
    public TextField numberOfEpisodesTextField2;

    //TODO COMBINE 480P/540P
    public final ObservableList<CodeTable> qualityTypeObservableList = FXCollections
            .observableArrayList(
                    new CodeTable(0, "480p"),
                    new CodeTable(1, "540p"),
                    new CodeTable(2, "720p"),
                    new CodeTable(3, "1080p"));

    public AnimeController(PrintWriter out) {
        super(out);
    }

    @FXML
    public void onActionSave() {
        logger.warn("Empty onActionSave method");
    }

    @FXML
    public void onActionCancel() {
        logger.debug("Closing scene");
        Stage stage = (Stage) animeNameTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onActionDownAlrReleasedCheck() {
        if(check) {
            numberOfEpisodesTextField.setText(numberOfEpisodesTextField2.getText());
            numberOfEpisodesTextField2.clear();
            check = false;
        }
        else {
            numberOfEpisodesTextField2.setText(numberOfEpisodesTextField.getText());
            numberOfEpisodesTextField.clear();
            check = true;
        }
        numberOfEpisodesTextField.setVisible(!check);
        hBox.setVisible(check);
    }

    public void setHandler() {
        numberOfEpisodesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOfEpisodesTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void initChoiceBox() {
        logger.debug("initializing Choice Box");
        qualityChoiceBox.getItems().setAll(qualityTypeObservableList);
    }
}

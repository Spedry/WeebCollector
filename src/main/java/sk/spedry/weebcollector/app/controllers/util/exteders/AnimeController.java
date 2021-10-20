package sk.spedry.weebcollector.app.controllers.util.exteders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class AnimeController extends ClientMessageSender implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @FXML
    public TextField animeNameTextField;
    @FXML
    public TextField botTextField;
    @FXML
    public ChoiceBox<CodeTable> qualityChoiceBox;
    @FXML
    public TextField numberOfEpisodesTextField;

    public final ObservableList<CodeTable> qualityTypeObservableList = FXCollections
            .observableArrayList(
                    new CodeTable(1, "480p"),
                    new CodeTable(2, "540p"),
                    new CodeTable(3, "720p"),
                    new CodeTable(4, "1080p"));

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
    public void setHandler() {
        numberOfEpisodesTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberOfEpisodesTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void initChoiceBox() {
        logger.debug("initializing Choice Box");
        qualityChoiceBox.getItems().setAll(qualityTypeObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.trace("Initializing");
        initChoiceBox();
        setHandler();
    }
}

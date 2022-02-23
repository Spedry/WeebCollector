package sk.spedry.weebcollector.app.controllers.preferencescontrollers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.PreferencesPopupController;
import sk.spedry.weebcollector.app.controllers.util.WCMServer;
import sk.spedry.weebcollector.app.controllers.util.WCMSetup;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.time.chrono.AbstractChronology;
import java.util.Objects;
import java.util.ResourceBundle;

public class SetupSettings extends ClientMessageSender implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final Window window;

    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField downloadFolderTextField;
    @FXML
    private TextField channelNameTextField;
    @FXML
    private TextField serverNameTextField;
    @FXML
    private Button startBotButton;

    public SetupSettings(PrintWriter out, Window window) {
        super(out);
        this.window = window;
    }

    @FXML
    public void onActionSaveSetup() {
        if (!Objects.equals(userNameTextField.getText(), "") &&
            !Objects.equals(downloadFolderTextField.getText(), "") &&
            !Objects.equals(serverNameTextField.getText(), "") &&
            !Objects.equals(channelNameTextField.getText(), ""))
                sendMessage(new WCMessage("setSetup", new WCMSetup(
                    userNameTextField.getText(),
                    downloadFolderTextField.getText(),
                    serverNameTextField.getText(),
                    channelNameTextField.getText())));
        else
            logger.warn("Any field can't be empty");
    }

    @FXML
    public void onActionBrowse() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        try {
            if (!Objects.equals(downloadFolderTextField.getText(), "")) {
                directoryChooser.setInitialDirectory(new File(downloadFolderTextField.getText()));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        File selectedDirectory = directoryChooser.showDialog(window);
        downloadFolderTextField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    public void onActionStartBot() {
        onActionSaveSetup();
        sendMessage(new WCMessage("startIRCBot"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameTextField.setText(PreferencesPopupController.setup.getUserName());
        downloadFolderTextField.setText(PreferencesPopupController.setup.getDownloadFolder());
        serverNameTextField.setText(PreferencesPopupController.setup.getServerName());
        channelNameTextField.setText(PreferencesPopupController.setup.getChannelName());
        EventHandler<KeyEvent> handler = event -> {
            handle();
        };
        userNameTextField.setOnKeyReleased(handler);
        downloadFolderTextField.setOnKeyReleased(handler);
        serverNameTextField.setOnKeyReleased(handler);
        channelNameTextField.setOnKeyReleased(handler);
        handle();
    }

    private void handle() {
        startBotButton.setDisable(true);
        try {
            if (userNameTextField.getText() != null && !Objects.equals(userNameTextField.getText(), ""))
                if (downloadFolderTextField.getText() != null && !Objects.equals(downloadFolderTextField.getText(), ""))
                    if (serverNameTextField.getText() != null && !Objects.equals(serverNameTextField.getText(), ""))
                        if (channelNameTextField.getText() != null && !Objects.equals(channelNameTextField.getText(), ""))
                            startBotButton.setDisable(false);
        }
        catch (Exception e) {
            logger.error("Error occurred while enabling button");
        }
    }
}

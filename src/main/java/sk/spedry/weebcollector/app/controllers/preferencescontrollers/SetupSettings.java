package sk.spedry.weebcollector.app.controllers.preferencescontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class SetupSettings extends ClientMessageSender implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final Window window;

    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField downloadFolderTextField;

    public SetupSettings(PrintWriter out, Window window, String userName, String downloadFolder) {
        super(out);
        this.window = window;
    }

    @FXML
    public void onActionSaveSetup() {
        if (!Objects.equals(userNameTextField.getText(), "") && !Objects.equals(downloadFolderTextField.getText(), ""))
            sendMessage(new WCMessage("setSetup", new WCMSetup(userNameTextField.getText(), downloadFolderTextField.getText())));
        else
            logger.warn("Username and download folder field can't be empty");
    }

    @FXML
    public void onMouseClickBrowse() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameTextField.setText(PreferencesPopupController.userName);
        downloadFolderTextField.setText(PreferencesPopupController.downloadFolder);
    }
}

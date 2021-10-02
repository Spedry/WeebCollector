package sk.spedry.weebcollector.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.WCApplication;
import sk.spedry.weebcollector.app.controllers.preferencescontrollers.ServerSettings;
import sk.spedry.weebcollector.app.controllers.preferencescontrollers.SetupSettings;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.IOException;
import java.io.PrintWriter;

public class PreferencesPopupController extends ClientMessageSender {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @FXML
    private AnchorPane rightSideOfSplitPane = new AnchorPane();
    private String rightPaneText;

    public static String userName = "";
    public static String downloadFolder = "";

    public PreferencesPopupController(PrintWriter out) {
        super(out);
        sendMessage(new WCMessage("getSetup"));
    }

    @FXML
    public void onActionShowServer() {
        try {
            FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/right-pane-server.fxml"));
            if (!"serverSettings".equals(rightPaneText)) {
                rightPaneText = "serverSettings";
                logger.debug("Showing server settings on right side");
                loader.setController(new ServerSettings(getOut()));
                rightSideOfSplitPane.getChildren().setAll((Node) loader.load());
            }
        } catch (IOException e) {
            logger.error("onActionShowPreferences - loader load", e);
        }
    }

    @FXML
    public void onActionShowSetup() {
        try {
            FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/right-pane-setup.fxml"));
            if (!"setupSettings".equals(rightPaneText)) {
                rightPaneText = "setupSettings";
                logger.debug("Showing setup settings on right side");
                loader.setController(new SetupSettings(getOut(),
                        rightSideOfSplitPane.getScene().getWindow(),
                        userName,
                        downloadFolder));
                rightSideOfSplitPane.getChildren().setAll((Node) loader.load());
            }
        } catch (IOException e) {
            logger.error("onActionShowPreferences - loader load", e);
        }
    }
}

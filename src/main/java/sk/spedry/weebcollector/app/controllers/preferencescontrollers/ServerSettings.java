package sk.spedry.weebcollector.app.controllers.preferencescontrollers;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.util.WCMServer;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.PrintWriter;

// MAYBE RENAME
public class ServerSettings extends ClientMessageSender {

    private final Logger logger = LogManager.getLogger(this.getClass());

    //TODO
    // ADD TEXTFIELD FOR USER SPECIFIED NAME OR CREATE A COMBINATION OF SERVER AND CHANNEL NAME
    // PROBLEM WITH SERVERS WITH MULTIPLE CHANNELS
    @FXML
    private TextField channelNameTextField;
    @FXML
    private TextField serverNameTextField;

    public ServerSettings(PrintWriter out) {
        super(out);
    }

    @FXML
    public void onActionAddServerNameAndChanel() {
        sendMessage(new WCMessage("addServer", new WCMServer(serverNameTextField.getText(), channelNameTextField.getText())));
    }

    @FXML
    public void onActionClearServerAndChanelTextField() {
        channelNameTextField.clear();
        serverNameTextField.clear();
    }
}

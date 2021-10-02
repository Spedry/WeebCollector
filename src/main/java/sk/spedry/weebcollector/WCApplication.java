package sk.spedry.weebcollector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.WeebCollectorController;
import sk.spedry.weebcollector.clienthandler.ClientMessageHandler;
import sk.spedry.weebcollector.clienthandler.ClientSocketCloser;

import java.io.IOException;

public class WCApplication extends javafx.application.Application {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private ClientSocketCloser socketCloser;

    @Override
    public void start(Stage primaryStage) {
        try {
            ClientMessageHandler messageHandler = new ClientMessageHandler();
            logger.trace("App is starting...");
            WeebCollectorController weebCollectorController = new WeebCollectorController(primaryStage, messageHandler);
            FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/weeb-collector.fxml"));
            loader.setController(weebCollectorController);
            Parent root = loader.load();
            Scene login = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(login);
            primaryStage.show();
            socketCloser = new ClientSocketCloser(messageHandler.getSocket());
        } catch (IOException e) {
            logger.error("Loading parent root went wrong", e);
        }
    }

    @Override
    public void stop() {
        try {
            socketCloser.shutDownSocket();
            logger.debug("Sending shutDownSocket message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
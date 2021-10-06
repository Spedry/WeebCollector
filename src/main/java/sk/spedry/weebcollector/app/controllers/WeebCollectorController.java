package sk.spedry.weebcollector.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.WCApplication;
import sk.spedry.weebcollector.app.controllers.cell.AnimeCell;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageHandler;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WeebCollectorController extends ClientMessageSender implements Initializable {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final ClientMessageHandler messageHandler;

    private final Stage window;
    @FXML
    private VBox mainPanel;
    @FXML
    public ListView<WCMAnimeEntry> animeListView;
    public static ObservableList<WCMAnimeEntry> wcmAnimeEntryObservableList = FXCollections.observableArrayList();
    @FXML
    private Button addButton;
    @FXML
    private Button swapButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Label downloadingAnimeLabel;
    @FXML
    private ProgressBar  downloadingAnimeProgressBar;
    @FXML
    private MenuBar drag;
    private double xOffset = 0;
    private double yOffset = 0;

    public WeebCollectorController(Stage window, ClientMessageHandler messageHandler) {
        super(messageHandler.getOut());
        logger.trace("Creating weeb collector controller" + Thread.currentThread().getName());
        this.window = window;
        this.messageHandler = messageHandler;
    }

    private void animeListView() {
        logger.debug("Creating animeListView");
        animeListView.setItems(wcmAnimeEntryObservableList);
        animeListView.setCellFactory(param -> new AnimeCell());
    }

    @FXML
    public void onActionOpenPreferences() {
        try {
            logger.debug("Opening preferences");
            Stage popup = new Stage();
            popup.initOwner(window);
            FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/preferences-popup.fxml"));
            PreferencesPopupController preferences = new PreferencesPopupController(messageHandler.getOut());
            loader.setController(preferences);
            Scene newScene = new Scene(loader.load());
            popup.setScene(newScene);
            popup.show();
            preferences.onActionShowSetup();
        } catch (IOException e) {
            logger.error("FXML loader", e);
        }
    }

    @FXML
    public void onActionAdd(ActionEvent actionEvent) {
        try {
            logger.debug("Showing add scene");
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initOwner(window);
            FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/add-new-anime.fxml"));
            loader.setController(new AddNewAnimeController(getOut()));
            Scene newScene = new Scene(loader.load());
            popup.setX(window.getX() + 5);
            popup.setY(window.getY() + 35);
            popup.setScene(newScene);
            popup.show();
        } catch (IOException e) {
            logger.error("FXML loader", e);
        }
    }

    @FXML
    public void onActionSwap(ActionEvent actionEvent) {
        logger.info("TODO swap button");
    }

    @FXML
    public void onActionRefresh(ActionEvent actionEvent) {
        logger.info("TODO refresh button");
    }

    @FXML
    public void onActionClose() {
        Stage stage = (Stage) window.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onActionMinimize() {
        Stage stage = (Stage) window.getScene().getWindow();
        stage.setIconified(true);
    }

    private void initClientMessageHandler() {

    }

    private void initDragWindow() {
        logger.debug("Initializing drag window");
        drag.setOnMousePressed(event -> {
            xOffset = window.getX() - event.getScreenX();
            yOffset = window.getY() - event.getScreenY();
        });

        drag.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() + xOffset);
            window.setY(event.getScreenY() + yOffset);
        });
    }

    private void initClientSide() {
        logger.debug("Initializing client side");

        Thread clientsSideHandler = new Thread(messageHandler);
        clientsSideHandler.setDaemon(true);
        clientsSideHandler.start();
    }

    private void initObservableList() {
        logger.debug("Initializing server observable list");
        sendMessage(new WCMessage("getServerList"));
        sendMessage(new WCMessage("getAnimeList"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.trace("Initializing");
        animeListView();

        initDragWindow();

        initClientSide();

        initObservableList();
    }
}

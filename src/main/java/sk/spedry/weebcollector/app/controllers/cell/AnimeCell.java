package sk.spedry.weebcollector.app.controllers.cell;

import java.awt.Desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sk.spedry.weebcollector.WCApplication;
import sk.spedry.weebcollector.app.controllers.EditAnimeController;
import sk.spedry.weebcollector.app.controllers.WeebCollectorController;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;
import sk.spedry.weebcollector.properties.Configuration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class AnimeCell extends ListCell<WCMAnimeEntry> {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private WCMAnimeEntry animeEntry;

    private final Stage window ;
    private final ClientMessageSender sender;
    @FXML
    public HBox animeEntryHBox;
    @FXML
    public Label animeName;
    @FXML
    public Label numberOfEpisodes;
    @FXML
    public AnchorPane indicator;


    static String animeToOpen = null;

    public AnimeCell(Stage window, ClientMessageSender sender) {
        super();
        this.window = window;
        this.sender = sender;
        FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/anime-cell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            logger.error("Error while loading anime-cell.fxml", e);
        }
    }

    @Override
    protected void updateItem(WCMAnimeEntry animeEntry, boolean empty) {
        super.updateItem(animeEntry, empty);
        setGraphic(null);

        if (animeEntry != null && !empty) {
            this.animeEntry = animeEntry;
            animeName.setText(animeEntry.getAnimeName());
            Tooltip tooltip = new Tooltip(animeEntry.getAnimeName());
            tooltip.setShowDelay(Duration.millis(200));
            animeName.setTooltip(tooltip);
            if (animeEntry.getNumberOfEpisodes() == 0)
                numberOfEpisodes.setText("/?");
            else
                numberOfEpisodes.setText(animeEntry.getNumberOfDownloadedEpisodes() + "/" + animeEntry.getNumberOfEpisodes());

            boolean doISetMargin = false;

            if (animeEntry.isWasDownloaded()) {
                logger.debug(animeEntry.getAnimeName() + " was " + animeEntry.isWasDownloaded());
                indicator.setStyle("-fx-background-color: #37BD54");
                doISetMargin = true;
            }
            else if (animeEntry.getReleaseDate() != null && animeEntry.getReleaseDate().equals(LocalDate.now())) {
                logger.debug("Today day matches with releaseDay");
                indicator.setStyle("-fx-background-color: #7289DA");
                doISetMargin = true;
            }

            indicator.setVisible(doISetMargin);
            indicator.setDisable(doISetMargin);

            if (doISetMargin) {
                HBox.setMargin(animeName, new Insets(0, 0, 0, 0));
            } else {
                HBox.setMargin(animeName, new Insets(0, 0, 0, -7));
            }

            setGraphic(animeEntryHBox);
        }
    }

    @FXML
    public void onActionDeleteEntry(ActionEvent actionEvent) {
        logger.trace("Delete entry button was pressed");

        sender.sendMessage(new WCMessage("removeAnimeFromList", animeEntry));
        WeebCollectorController.wcmAnimeEntryObservableList.remove(animeEntry);
    }

    @FXML
    public void onActionEditEntry(ActionEvent actionEvent) {
        logger.trace("Edit entry button was pressed");
        try {
            logger.debug("Showing add scene");
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initOwner(window);
            FXMLLoader loader = new FXMLLoader(WCApplication.class.getResource("fxml/add-new-anime.fxml"));
            loader.setController(new EditAnimeController(sender.getOut(), animeEntry));
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
    public void onMouseClickedOpenLastEpisode() {
        logger.debug("Opening last downloaded anime");
        sender.sendMessage(new WCMessage("getAnimeToOpen", animeName.getText()));

        String pathToAnime = new Configuration().getProperty("pathToAnime");

        /*if (pathToAnime.equals("")) {
            logger.error("TODO get anime download folder from server");
            return;
        }*/

        Thread openAnime = new Thread(() -> {
            logger.debug("Waiting");
            synchronized (AnimeCell.class) {
                try {
                    AnimeCell.class.wait();
                } catch (InterruptedException e) {
                    logger.error("Couldn't wait");
                }
            }
            logger.debug("Not waiting");

            logger.debug("pathToAnime: {}", pathToAnime);
            logger.debug("animeToOpen: {}", animeToOpen);
            logger.debug(pathToAnime + "\\" + animeToOpen);

            File file = new File(pathToAnime + "\\" + animeToOpen);

            if(!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) {
                try {
                    desktop.open(file);
                    sender.sendMessage(new WCMessage("openLastEpisode", animeName.getText()));
                } catch (IOException e) {
                    logger.error("Couldn't open last downloaded episode");
                }
            }
            animeToOpen = null;
        });
        openAnime.setDaemon(true);
        openAnime.start();
    }

    public static void setAnimeToOpen(String a) {
        animeToOpen = a;
        synchronized (AnimeCell.class) {
            AnimeCell.class.notify();
        }
    }
}

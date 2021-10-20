package sk.spedry.weebcollector.app.controllers.cell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.WCApplication;
import sk.spedry.weebcollector.app.controllers.EditAnimeController;
import sk.spedry.weebcollector.app.controllers.WeebCollectorController;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMessage;
import sk.spedry.weebcollector.clienthandler.ClientMessageSender;

import java.io.IOException;
import java.io.PrintWriter;

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
            if (animeEntry.getNumberOfEpisodes() == null)
                numberOfEpisodes.setText("/...");
            else
                numberOfEpisodes.setText(animeEntry.getNumberOfDownloadedEpisodes() + "/" + animeEntry.getNumberOfEpisodes());
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
}

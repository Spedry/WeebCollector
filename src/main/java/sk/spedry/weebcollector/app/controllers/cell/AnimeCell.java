package sk.spedry.weebcollector.app.controllers.cell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.WCApplication;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;

import java.io.IOException;

public class AnimeCell extends ListCell<WCMAnimeEntry> {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @FXML
    public HBox animeEntry;
    @FXML
    public Label animeName;
    @FXML
    public Label numberOfEpisodes;
    @FXML
    public Button deleteEntryButton;
    @FXML
    public Button editEntryButton;
    private FXMLLoader loader;

    public AnimeCell() {
        super();
        loader = new FXMLLoader(WCApplication.class.getResource("fxml/anime-cell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            logger.error("Error while loading anime-cell.fxml", e);
        }
    }

    @Override
    protected void updateItem(WCMAnimeEntry wcmAnimeEntry, boolean empty) {
        super.updateItem(wcmAnimeEntry, empty);
        setGraphic(null);

        if (wcmAnimeEntry != null && !empty) {
            animeName.setText(wcmAnimeEntry.getAnimeName());
            if (wcmAnimeEntry.getNumberOfEpisodes() == null)
                numberOfEpisodes.setText("/...");
            else
                numberOfEpisodes.setText(wcmAnimeEntry.getNumberOfDownloadedEpisodes() + "/" + wcmAnimeEntry.getNumberOfEpisodes());
            setGraphic(animeEntry);
        }
    }

    @FXML
    public void onActionDeleteEntry(ActionEvent actionEvent) {
        logger.trace("Delete entry button was pressed");
    }

    @FXML
    public void onActionEditEntry(ActionEvent actionEvent) {
        logger.trace("Edit entry button was pressed");
    }
}

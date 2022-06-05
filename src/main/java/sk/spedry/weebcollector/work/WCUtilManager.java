package sk.spedry.weebcollector.work;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.spedry.weebcollector.app.controllers.choiceboxitems.CodeTable;
import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;
import sk.spedry.weebcollector.app.controllers.util.WCMServer;
import sk.spedry.weebcollector.app.controllers.util.lists.AnimeList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class WCUtilManager {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public CodeTable wcmServerToCodeTable(WCMServer wcmServer) {
        return new CodeTable(
                wcmServer.getId(),
                wcmServer.getServerName(),
                wcmServer.getServeChannelName()
        );
    }

    public AnimeList sortAnimeListByReleaseDay(AnimeList animeList) {
        logger.traceEntry();
        AnimeList sortedAnimeList = new AnimeList();
        ArrayList<AnimeList> tempList = new ArrayList<>(9);
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        for (int i = 0; i < 9; i++) {
            tempList.add(new AnimeList());
        }

        for (WCMAnimeEntry anime : animeList.getAnimeList()) {
            if (anime.getNumberOfDownloadedEpisodes() >= anime.getNumberOfEpisodes()) {
                if (anime.getNumberOfEpisodes() == 0)
                    tempList.get(anime.getReleaseDay().getValue() - 1).getAnimeList().add(anime);
                else
                    tempList.get(7).getAnimeList().add(anime);
            } else if (anime.getReleaseDay() == null) {
                tempList.get(8).getAnimeList().add(anime);
            }
            else {
                tempList.get(anime.getReleaseDay().getValue() - 1).getAnimeList().add(anime);
            }
        }
        System.out.println("\n");

        /*for (AnimeList list : tempList) {
            for (WCMAnimeEntry anime : list.getAnimeList()) {
                System.out.println(anime.getAnimeNa
                §me() + " " + anime.getReleaseDay());

            }
        }*/
        // add from current day
        for (int i = today.getValue()-1; i < 7; i++) {
            sortedAnimeList.getAnimeList().addAll(tempList.get(i).getAnimeList());
        }
        // add rest but in order, monday, tuesday...
        for (int i = 0; i < today.getValue()-1; i++) {
            sortedAnimeList.getAnimeList().addAll(tempList.get(i).getAnimeList());
        }
        // add all anime where episodes == downloadedEpisodes
        sortedAnimeList.getAnimeList().addAll(tempList.get(7).getAnimeList());
        // add all anime where releaseDay isn't set yet
        sortedAnimeList.getAnimeList().addAll(tempList.get(8).getAnimeList());

        for (WCMAnimeEntry anime : sortedAnimeList.getAnimeList()) {
            System.out.println(anime.getAnimeName() + " " + anime.getReleaseDay());
        }

        // TODO ADD SECONDARY SORTING

        return logger.traceExit(sortedAnimeList);
    }
}

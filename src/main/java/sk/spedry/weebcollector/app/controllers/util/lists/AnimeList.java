package sk.spedry.weebcollector.app.controllers.util.lists;

import sk.spedry.weebcollector.app.controllers.util.WCMAnimeEntry;

import java.util.ArrayList;
import java.util.List;

public class AnimeList {
    private final List<WCMAnimeEntry> list;

    public AnimeList() {
        this.list = new ArrayList<WCMAnimeEntry>();
    }

    public List<WCMAnimeEntry> getAnimeList() {
        return list;
    }

    public void addAnime(WCMAnimeEntry anime) {
        this.list.add(anime);
    }

    public int getSize() {
        return this.list.size();
    }
}

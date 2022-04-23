package service;

import DAO.MangaDAO;
import model.Manga;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MangaService {

    //tim tim truyen theo id
    public Manga getMangaByID(String id) {
        increaseMangaVisits(id);
        return new MangaDAO().getMangaByID(id);
    }

    //moi trang mac dinh co 12 truyen
    final static int NUM_OF_MANGA_ON_PAGE = 4;

    //ham tim kiem truyen
    public List<Manga> searchManga(String by, String value, int page, String text) {
        Document filter = new Document();
        Document sort = new Document();

        if (by != null && value != null)
            filter.append(by, value);
        if (text != null)
            filter.append("$text", new Document("$search", text));
        else
            sort.append("mangaName", 1);

        List<Manga> list = new MangaDAO().searchManga(filter, sort, NUM_OF_MANGA_ON_PAGE, (page - 1) * NUM_OF_MANGA_ON_PAGE);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    //tinh so trang
    public long getTotalPages(String by, String value, String text) {
        Document filter = new Document();
        if (by != null && value != null)
            filter.append(by, value);
        if (text != null)
            filter.append("$text", new Document("$search", text));

        long totalManga = new MangaDAO().getMangaNumber(filter);
        return (long) Math.ceil((float) totalManga / NUM_OF_MANGA_ON_PAGE);
    }

    public void increaseMangaVisits(String id) {
        new MangaDAO().increaseMangaVisits(id);
    }

    public List<Manga> getTopTrending() {
        return new MangaDAO().getTopTrending().subList(0,3);
    }
}
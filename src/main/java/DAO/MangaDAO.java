package service;

import DAO.MangaDAO;
import org.bson.Document;

public class MangaService {

    //moi trang mac dinh co 4 truyen
    final static int NUM_OF_MANGA_ON_PAGE = 4;

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
}

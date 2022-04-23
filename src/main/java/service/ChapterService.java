package service;

import DAO.MangaDAO;
import model.Chap;
import model.Manga;

public class ChapterService {

    public Chap getChapByName(String idManga, String chapName) {
        Manga manga = new MangaDAO().getMangaByID(idManga);
        Chap chap = new Chap();
        for (int i = 0; i < manga.getChap().size(); i++) {
            if(manga.getChap().get(i).getChapName().equals(chapName))
                chap = manga.getChap().get(i);
        }
        return chap;
    }
}
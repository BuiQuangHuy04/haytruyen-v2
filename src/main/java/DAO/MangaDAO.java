package DAO;

import com.mongodb.client.MongoCollection;
import model.Chap;
import model.ListImg;
import model.Manga;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MangaDAO extends AbsDAO {

    public List<Manga> getManga() {
        //tim collection "manga"
        MongoCollection<Document> listManga = getDB().getCollection("manga");
        List<Manga> list = new ArrayList<>();
        listManga.find().limit(4).forEach(d -> list.add(doctoManga(d)));
        return list;
    }

    //chuyen tu Document -> Manga
    public Manga doctoManga(Bson bson) {
        //tao 1 Object Manga
        Manga manga = new Manga();
        //tao Object document de doc
        Document document = (Document) bson;
        //lay du lieu tu tung truong cua document roi luu vao manga
        manga.setId(document.getObjectId("_id").toHexString());
        manga.setMangaName(MessageFormat.format("{0}", document.get("mangaName")));
        manga.setMangaPoster(document.getString("mangaPoster"));
        manga.setMangaWriter(document.getString("mangaWriter"));
        manga.setVisits(document.getInteger("visits"));
        manga.setMangaGenre((List<String>) document.get("mangaGenre"));
        manga.setChap(docstoChapList(document));
        return manga;
    }

   public List<Chap> docstoChapList(Document document) {
        ArrayList<Document> chapDocs = new ArrayList<>();
        for (int i = 0; i < ((List<?>) document.get("listChap")).size(); i++) {
            chapDocs.add((Document) ((List<?>) document.get("listChap")).get(i));
        }
        List<Chap> chapArrayList = new ArrayList<>();
        for (int i = 0; i < chapDocs.size(); i++) {
            Chap chap = new Chap();
            chap.setChapName((String) chapDocs.get(i).get("chapName"));
            chap.setListImg(docstoImgList((List<Document>) chapDocs.get(i).get("listImg")));
            chapArrayList.add(chap);
        }
        return chapArrayList;
    }

    public ListImg docstoImgList(List<Document> documents) {
        ListImg ImageList = new ListImg();
        List<String> imgUrl = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            imgUrl.add((String) documents.get(i).get("urlImg"));
        }
        ImageList.setUrlImg(imgUrl);
        return ImageList;
    }

    public long getMangaNumber(Document filter) {
        MongoCollection<Document> manga = getDB().getCollection("manga");
        return manga.countDocuments(filter);
    }
}

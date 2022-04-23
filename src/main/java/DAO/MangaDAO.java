package DAO;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import model.Chap;
import model.ListImg;
import model.Manga;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class MangaDAO extends AbsDAO {

    public Manga getMangaByID(String id) {
        //tim collection "manga"
        MongoCollection<Document> listManga = getDB().getCollection("manga");
        //lay ra cac ban ghi theo id, tra ve Object dang Document
        Document manga = listManga.find(eq("_id", new ObjectId(id))).first();
        //goi ham de truyen tu Object document thanh Object manga
        return doctoManga(manga);
    }

    public List<Manga> getTopTrending() {
        //tim collection "manga"
        MongoCollection<Document> mangaCollection = getDB().getCollection("manga");
        FindIterable<Document> listDoc = mangaCollection.find().sort(descending("visits"));

        List<Manga> mangaList = new ArrayList<>();

        listDoc.forEach(d-> {
            mangaList.add(doctoManga(d));
        });

        return mangaList;
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

    public List<Manga> searchManga(Document filter, Document sort, int limit, int skip) {
        MongoCollection<Document> manga = getDB().getCollection("manga");
        List<Manga> list = new ArrayList<>();
        manga.find(filter).sort(sort).limit(limit).skip(skip).forEach(d -> list.add(doctoManga(d)));
        return list;
    }

    public long getMangaNumber(Document filter) {
        MongoCollection<Document> manga = getDB().getCollection("manga");
        return manga.countDocuments(filter);
    }

    public void increaseMangaVisits(String id) {
        MongoCollection collection = getDB().getCollection("manga");

        Document doc = (Document) collection.find(eq("_id", new ObjectId(id)))
                .projection(
                        Projections.fields(
                                Projections.include("_id", "visits"),
                                Projections.excludeId()))
                .first();

        Document query = new Document().append("_id",new ObjectId(id));
        Bson updates = Updates.combine(
                Updates.set("visits",Integer.parseInt(String.valueOf(doc.get("visits"))) + 1));
        UpdateOptions options = new UpdateOptions().upsert(true);
        try {
            collection.updateOne(query, updates, options);
        } catch (MongoException me) {
        }
    }
}
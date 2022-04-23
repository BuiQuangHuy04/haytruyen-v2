package model;

import lombok.Data;

import java.util.List;

@Data
public class Manga {
    protected String id;

    protected String mangaPoster;

    protected String mangaWriter;

    protected List<String> mangaGenre;

    protected int visits;

    protected String mangaName;

    protected List<Chap> chap;

    private int total_comments;

    public String getMangaPoster ()
    {
        return mangaPoster;
    }

    public void setMangaPoster (String mangaPoster)
    {
        this.mangaPoster = mangaPoster;
    }

    public String getMangaWriter ()
    {
        return mangaWriter;
    }

    public void setMangaWriter (String mangaWriter)
    {
        this.mangaWriter = mangaWriter;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<String> getMangaGenre ()
    {
        return mangaGenre;
    }

    public void setMangaGenre (List<String> mangaGenre)
    {
        this.mangaGenre = mangaGenre;
    }

    public String getMangaName ()
    {
        return mangaName;
    }

    public void setMangaName (String mangaName)
    {
        this.mangaName = mangaName;
    }

    public List<Chap> getChap()
    {
        return chap;
    }

    public void setChap(List<Chap> chap)
    {
        this.chap = chap;
    }

    @Override
    public String toString()
    {
        return "[id = "+ id +"" +
                "\nmangaName = "+mangaName+"" +
                "\nmangaPoster = "+mangaPoster+"" +
                "\nmangaVisits = "+visits+"" +
                "\nmangaWriter = "+mangaWriter+"" +
                "\nmangaGenre = "+mangaGenre+"" +
                "\nlistChap = "+ chap +"]\n";
    }
}

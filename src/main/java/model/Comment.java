package model;

import lombok.Data;
import org.bson.Document;
import service.TimeService;

import java.util.Date;

@Data
public class Comment extends Document {
    protected String id;
    protected String name;
    protected String email;
    protected String text;
    protected String manga_id;
    protected Date date;

    public String getTimeAgo() {
        return TimeService.timeAgo(new Date(), date);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", text='" + text + '\'' +
                ", manga_id='" + manga_id + '\'' +
                ", date=" + date +
                '}';
    }
}

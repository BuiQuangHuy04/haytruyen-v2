package DAO;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.Comment;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CommentDAO extends AbsDAO {

    //ham lay cmt tu db
    public List<Comment> getComments(String manga_id) {
        //ket noi collection "comments"
        MongoCollection commentCollection = db.getCollection("comments");
        //tim cac cmt theo manga_id
        FindIterable<Document> commentDoc = commentCollection.find(eq("manga_id",new ObjectId(manga_id)));
        List<Comment> listComment = new ArrayList<>();
        commentDoc.forEach(d-> {
            Comment comment = new Comment();
            comment.setId(String.valueOf(d.get("_id")));
            comment.setName(String.valueOf(d.get("name")));
            comment.setEmail(String.valueOf(d.get("email")));
            comment.setText(String.valueOf(d.get("text")));
            comment.setManga_id(String.valueOf(d.get("manga_id")));
            comment.setDate(d.getDate("date"));
            listComment.add(comment);
        });
        return listComment;
    }

    //ham them cmt
    public void addComment(Comment comment) {
        //ket noi vao collection "comments"
        MongoCollection<Document> commentCollection = getDB().getCollection("comments");
        //tao 1 ban ghi moi
        comment.setDate(new Date());
        Document document = new Document();
        document.append("name", comment.getName());
        document.append("email", comment.getEmail());
        document.append("text", comment.getText());
        document.append("manga_id", new ObjectId(comment.getManga_id()));
        document.append("date", comment.getDate());
        //them ban ghi vao collection
        commentCollection.insertOne(document);
    }

}
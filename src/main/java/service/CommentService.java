package service;

import DAO.CommentDAO;
import model.Comment;

import java.util.List;

public class CommentService {

    //lay comment
    public List<Comment>  getComments(String manga_id) {
        //goi ham getComments cua CommentDAO de hien thi ra cmt theo manga_id
        List<Comment> list = new CommentDAO().getComments(manga_id);
        return list;
    }
    //them comment
    public void addComment(Comment comment) {
        //goi ham addComment cua CommentDAO de them comment
        new CommentDAO().addComment(comment);
    }
}
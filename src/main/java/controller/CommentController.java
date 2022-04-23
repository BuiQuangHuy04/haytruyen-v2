package controller;

import model.Comment;
import org.thymeleaf.ITemplateEngine;
import service.CommentService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommentController implements IController {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {
        //day comment
        if (request.getMethod().equalsIgnoreCase("post") && request.getParameter("action") != null && request.getParameter("action").equals("addComment")) {
            Comment comment = new Comment();
            comment.setName(request.getParameter("name"));
            comment.setEmail(request.getParameter("email"));
            comment.setText(request.getParameter("comment"));
            comment.setManga_id(request.getParameter("manga_id"));
            //goi ham addComment cua CommentService de day comment
            new CommentService().addComment(comment);
            response.sendRedirect("/manga?id=" + request.getParameter("manga_id"));
        }
    }
}
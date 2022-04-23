package controller;

import model.Manga;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.MangaService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MangaController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        //bat id truyen
        String id = request.getParameter("id");
        //goi ham getMangaByID cua MangaService de tim truyen
        Manga manga = new MangaService().getMangaByID(id);

        //tra ve cac bien de dung ben front end
        ctx.setVariable("id", id);
        ctx.setVariable("manga", manga);

        //goi template index.html
        templateEngine.process("manga", ctx, response.getWriter());
    }
}
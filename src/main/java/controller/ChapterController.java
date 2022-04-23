package controller;

import model.Chap;
import model.Manga;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.ChapterService;
import service.MangaService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChapterController implements IController {

    @Override
    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        //bat id truyen
        String id = request.getParameter("id");
        //goi ham getMangaByID cua MangaService de tim truyen
        Manga manga = new MangaService().getMangaByID(id);
        //bat chapter truyen
        String chapName = request.getParameter("chapter");
        //goi ham getChapByName cua ChapService de tim chuong truyen
        Chap chap = new ChapterService().getChapByName(id, chapName);

        //tra ve cac bien dung ben front end
        ctx.setVariable("chapter", chap);
        ctx.setVariable("manga", manga);
        //goi template chap.html
        templateEngine.process("chap", ctx, response.getWriter());
    }

}
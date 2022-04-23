package controller;

import DAO.MangaDAO;
import model.Manga;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.MangaService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, @org.jetbrains.annotations.NotNull final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        //tao bien de tim kiem
        String url = "";
        String by = null;
        String value = null;
        String text = null;

        //tao link truyen
        if (request.getParameter("text") != null) {
            text = request.getParameter("text").trim();
            url = url + "&text=" + text;
        }
        //phan loai
        if (request.getParameter("by") != null) {
            by = request.getParameter("by").trim();
            url = url + "&by=" + by;
        }
        if (request.getParameter("value") != null) {
            value = request.getParameter("value").trim();
            url = url + "&value=" + value;
        }
        //tra ve link truyen, breadcrumb de dung o ben front end
        ctx.setVariable("url", url);
        long totalPages = new MangaService().getTotalPages(by, value, text);
        ctx.setVariable("totalPages", totalPages);

        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page").trim());
        ctx.setVariable("page", page);

        //loc truyen theo cac tham so truyen vao
        List<Manga> list = new MangaDAO().getManga();

        //tra ve list truyen de dung o ben front end
        ctx.setVariable("list", list);

        //goi template index.html
        templateEngine.process("index", ctx, response.getWriter());
    }
}

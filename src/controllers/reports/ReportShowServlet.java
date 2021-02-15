package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportShowServlet
 */
@WebServlet("/reports/show")
public class ReportShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //該当のIDの日報１件のみをデータベースから取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //コメントを取得
        int page = 1;
        try{
        page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}
        List<Comment> comments = em.createNamedQuery("getMyAllComments", Comment.class)
                .setParameter("report", r)
                .setFirstResult(5 * (page - 1))
                .setMaxResults(5)
                .getResultList();
        long comments_count = (long)em.createNamedQuery("getCommentsCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        //いいね数を取得
        long likes_count = (long)em.createNamedQuery("getLikesCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();


        em.close();

        //日報データをリクエストスコープにセットしてJSPに送る
        request.setAttribute("page", page);
        request.setAttribute("comments_count", comments_count);
        request.setAttribute("report", r);
        request.setAttribute("comments", comments);
        request.setAttribute("likes_count", likes_count);
        request.setAttribute("_token", request.getSession().getId());
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}

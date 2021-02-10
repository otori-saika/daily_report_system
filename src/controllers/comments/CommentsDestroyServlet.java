package controllers.comments;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsDestroyServlet
 */
@WebServlet("/comments/destroy")
public class CommentsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));
            request.setAttribute("report", r);

            //該当のIDのコメント1件のみをデータベースから取得
            Comment c = em.find(Comment.class, Integer.parseInt(request.getParameter("comment_id")));

            em.getTransaction().begin();
            //削除
            em.remove(c);
            em.getTransaction().commit();
            em.close();

            //showページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
        }
    }

}

package controllers.comments;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsCreateServlet
 */
@WebServlet("/comments/create")
public class CommentsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セキュリティ対策↓
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            //データベースに接続↓
            EntityManager em = DBUtil.createEntityManager();
            em.getTransaction().begin();


            Comment c = new Comment();

            String comment = request.getParameter("comment");
            c.setComment(comment);
            Employee e = (Employee)request.getSession().getAttribute("login_employee");
            c.setEmployee(e);

            //フォームから受け取ったreport_idと同じidをデータベースから取得
            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));
            c.setReport(r);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            c.setCreated_at(currentTime);


            //データベースに保存
            em.persist(c);
            em.getTransaction().commit();
            em.close();


            //show.jspにデータを渡して呼び出す

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
            }
        }

    }



package controllers.like;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class LikesCreateServlet
 */
@WebServlet("/likes/create")
public class LikesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
          //データベースに接続↓
            EntityManager em = DBUtil.createEntityManager();


            Like l = new Like();

            Employee e = (Employee)request.getSession().getAttribute("login_employee");
            l.setEmployee(e);
            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));
            l.setReport(r);
            try{
                Like c_l = em.createNamedQuery("checkLike", Like.class)
                        .setParameter("e", e)
                        .setParameter("r", r)
                        .getSingleResult();
                if(c_l != null) {
                    em.getTransaction().begin();
                    em.remove(c_l);
                    em.getTransaction().commit();
                    em.close();
                    request.getSession().setAttribute("flush", "いいねを取り消しました。");
                    response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
                }
                } catch(NoResultException ex) {
                em.getTransaction().begin();
                em.persist(l);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "いいねしました。");


                response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());

            }

        }



            }

}










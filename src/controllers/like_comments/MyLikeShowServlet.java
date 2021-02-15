package controllers.like_comments;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import utils.DBUtil;

/**
 * Servlet implementation class MyLikeShowServlet
 */
@WebServlet("/myLike")
public class MyLikeShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyLikeShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();


        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");



        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        List<Like> myLikes = em.createNamedQuery("getMyLikes", Like.class)
                .setParameter("employee", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();
        long myLikesCount = (long)em.createNamedQuery("getMyLikesCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("employee", login_employee);
        request.setAttribute("page", page);
        request.setAttribute("myLikes", myLikes);
        request.setAttribute("myLikesCount", myLikesCount);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/like_comments/myLike.jsp");
        rd.forward(request, response);
    }

}

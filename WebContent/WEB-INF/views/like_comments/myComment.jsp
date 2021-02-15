<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <ul>
            <li style="display: inline;"><a href="<c:url value='/likeComments/myComment?id=${employee.id}' />">自分のコメント</a>&nbsp;&nbsp;</li>
            <li style="display: inline;"><a href="<c:url value='/otherComment?id=${employee.id}' />">社員のコメント</a>&nbsp;&nbsp;</li>
            <li style="display: inline;"><a href="<c:url value='/myLike?id=${employee.id}' />">いいねした日報</a>&nbsp;&nbsp;</li>
            <li style="display: inline;"><a href="<c:url value='/otherLike?id=${employee.id}' />">いいねされた日報</a>&nbsp;&nbsp;</li>
        </ul><br />

        <table id="comment">
               <c:forEach var="comments" items="${myComments}">
               <tbody style="border: 1px solid #cccccc;">
               <tr style="border:none;">
                   <td style="border:none;padding: 5px 2%;"><c:out value="${comments.report.employee.name}" />&nbsp;さんにコメントしました。&nbsp;&nbsp;
                   ｜&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${comments.created_at}" pattern="yyyy-MM-dd HH:mm" />&nbsp;&nbsp;&nbsp;
                   </td>
               </tr>
               <tr style="border:none;">
                    <td style="border:none;padding: 5px 2%;">
                    日報タイトル：<a href="<c:url value='/reports/show?id=${comments.report.id}' />">【${comments.report.title}】</a>
                    </td>
               </tr>
               <tr style="border:none;">
                   <td style="border:none;padding: 5px 2%;"><pre style="width: 300px;white-space: nowrap;text-overflow:ellipsis;overflow: hidden;">コメント：<c:out value="${comments.comment}" /></pre></td>
               </tr>


               </c:forEach>
               <tbody/></table>
               <div id="pagination">
                (全 ${myComments_count} 件) <br />
                <c:forEach var="i" begin="1" end="${((myComments_count - 1) / 15) + 1}" step="1">
                    <c:choose>
                        <c:when test="${i == page}">
                            <c:out value="${i}" />&nbsp;
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/likeComments/myComment?id=${employee.id}&page=${i}" />"><c:out value="${i}" /></a>&nbsp;
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>


    </c:param>
</c:import>

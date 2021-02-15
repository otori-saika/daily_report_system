<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
        <div id="flush_success">
            <c:out value="${flush}"></c:out>
        </div>
        </c:if>

        <c:choose>
            <c:when test="${report != null}">
                <h2>日報　詳細ページ</h2>

                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a></p>
                </c:if>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                            <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                            <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                            <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>



                <%-- コメント入力フォーム --%>
                <br />

                <form style="display:inline-block" method="POST" action="<c:url value='/comments/create' />">
                    <textarea name="comment" cols="45" placeholder="コメントを入力してください"></textarea>&nbsp;&nbsp;
                    <input type="hidden" name="report_id" value="${report.id}" />
                    <input type="hidden" name="_token" value="${_token}" />
                    <button type="submit">投稿</button>
                </form>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <%-- いいねボタン --%>
                <form method="POST" style="display:inline-block" action="<c:url value='/likes/create' />">
                    <input type="hidden" name="report_id" value="${report.id}" />
                    <input type="hidden" name="_token" value="${_token}" />
                    <button id="btn1" style="display:inline-flex" type="submit" >いいね</button>
                </form>&nbsp;<c:out value="${likes_count}" /><br />


                <%-- コメント一覧表示 --%>
               <br />
               <table id="comment">
               <c:forEach var="comment" items="${comments}">
               <tbody style="border: 1px solid #cccccc;">
               <tr style="border:none;">
                   <td style="border:none;"><c:out value="${comment.employee.name}" />&nbsp;さん&nbsp;&nbsp;
                   ｜&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${comment.created_at}" pattern="yyyy-MM-dd HH:mm" />&nbsp;&nbsp;&nbsp;

                <%-- コメント削除 --%>
               <c:if test="${sessionScope.login_employee.id == comment.employee.id}">
                   <a href="#" onclick="confirmDestroy();">削除</a>
                   <form method="POST" action="<c:url value='/comments/destroy' />">
                       <input type="hidden" name="report_id" value="${report.id}" />
                       <input type="hidden" name="comment_id" value="${comment.id}" />
                       <input type="hidden" name="_token" value="${_token}" />
                   </form>
                   <script>
                   function confirmDestroy() {
                       if(confirm("本当に削除してよろしいですか？")) {
                           document.forms[2].submit();
                       }
                   }
                   </script>
               </c:if></td>
               </tr>


               <tr style="border:none;">
                   <c:set var="a" value="\r\n" />
                   <c:set var="b" value="<br />" />
                   <c:set var="comments" value="${comment.comment}" />
                   <td style="border:none;"><pre><c:out value="${comments}" /></pre></td>
               </tr>

               </c:forEach>
               <tbody/>
               </table>
            <div id="pagination">
                (全 ${comments_count} 件) <br />
                <c:forEach var="i" begin="1" end="${((comments_count - 1) / 5) + 1}" step="1">
                    <c:choose>
                        <c:when test="${i == page}">
                            <c:out value="${i}" />&nbsp;
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/reports/show?id=${report.id}&page=${i}" />"><c:out value="${i}" /></a>&nbsp;
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>



            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>
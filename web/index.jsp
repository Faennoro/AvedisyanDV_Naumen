<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Сервис заметок</title>
  </head>
  <body>
  <h3>Сервис заметок</h3>
  <h5>Добавить заметку</h5>
  <form action="create" method="post">
    <table>
      <tr><td>Заголовок</td><td><input type="text" name="caption" /></td></tr>
      <tr><td>Текст заметки</td><td><input type="text" name="text" /></td></tr>
      <tr><td><input type="Submit" value="Добавить"/></td></tr>
    </table>
  </form>
<c:out value='${createResult}'/>

  <h5>Фильтр</h5>
  <form action="getList">
    <table>
      <tr>
        <td><input type="text" name="filter" /></td>
        <td><input type="Submit" value="Список"/></td>
      </tr>
    </table>
  </form>
  <h5>Список заметок</h5>
  <table border="1px solid black">
    <c:forEach items="${list}" var="note">
      <tr>
        <td><c:out value="${note.caption}" /></td>
        <td><c:out value="${note.noteText}" /></td>
        <td><a href="delete?id=<c:out value="${note.id}"/>">Удалить</a></td>
      </tr>
    </c:forEach>
  </table>
  </body>
</html>

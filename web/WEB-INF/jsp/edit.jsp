<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <table>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${type.equals('OBJECTIVE') || type.equals('PERSONAL')}">
                        <input type='text' name='${type}' size=30 value='<%=section%>'>
                    </c:when>
                    <c:when test="${type.equals('QUALIFICATIONS') || type.equals('ACHIEVEMENT')}">
                        <input type='text' name='${type}' cols=30 value='<%=String.join("/n", ((ListSection) section).getItems())%>'>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        </table>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
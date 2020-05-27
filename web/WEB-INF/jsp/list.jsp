<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Резюме</title>
</head>
<body>
<section>
    <table border=\"1\" cellpading=\"8\" cellspacing=\"0\">
        <tr>
            <th>ФИО</th>
        </tr>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a></td>
        </tr>
        <%
            }
        %>
    </table>
</section>
</body>
</html>

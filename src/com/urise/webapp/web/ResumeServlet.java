package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        sqlStorage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        PrintWriter printWriter = response.getWriter();
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume resume = sqlStorage.get(uuid);
            printWriter.write(resume.getUuid() + resume.getFullName());
        } else {
            printWriter.write(" <html>");
            printWriter.write("<head>");
            printWriter.write("<title>Резюме</title>");
            printWriter.write("</head>");
            printWriter.write("<body>");
            printWriter.write("<table>");
            printWriter.write("<tr>");
            printWriter.write(" <th>uuid</th>");
            printWriter.write(" <th>ФИО</th>");
            printWriter.write(" </tr>");
            List<Resume> list = sqlStorage.getAllSorted();
            for (Resume resume : list) {
                printWriter.write("<tr>");
                printWriter.write("<td>" + resume.getUuid() + "</td>");
                printWriter.write("<td>" + resume.getFullName() + "</td>");
                printWriter.write("</tr>");
            }
            printWriter.write("</table>");
            printWriter.write("</body>");
            printWriter.write("</html>");
        }
    }
}

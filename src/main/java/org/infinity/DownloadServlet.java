package org.infinity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

public class DownloadServlet extends HttpServlet{

    private String pathOfServer;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext conf = config.getServletContext();
        pathOfServer = conf.getRealPath(conf.getInitParameter("directory"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OutputStream out = resp.getOutputStream();
        String param = req.getParameter("id");
        Document document = null;
        try {
            document = DataManagement.getInstance().getInfoById(Integer.parseInt(param));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        File doc = new File(pathOfServer + document.getSha1());
        //doc.renameTo(new File(pathOfServer + document.getName()));
        resp.addHeader("Content-Disposition", "attachment; filename=" + document.getName());
        //resp.setContentLength((int) doc.length());
        FileInputStream input = new FileInputStream(doc);
        BufferedInputStream buf = new BufferedInputStream(input);
        int readBytes = 0;
        while ((readBytes = buf.read()) != -1)
            out.write(readBytes);
        out.close();
        buf.close();
        resp.sendRedirect("/");
    }
}

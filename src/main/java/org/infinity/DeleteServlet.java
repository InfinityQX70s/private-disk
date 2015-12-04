package org.infinity;

import javafx.scene.chart.PieChart;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by infinity on 01.12.15.
 */
public class DeleteServlet extends HttpServlet{

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
        String param = req.getParameter("sha");
        File file = new File(pathOfServer + param);
        if (file.exists()) {
            file.delete();
            try {
                DataManagement.getInstance().deleteFileBySha1(param);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("/");
    }

}

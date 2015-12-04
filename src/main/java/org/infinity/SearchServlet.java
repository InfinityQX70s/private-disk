package org.infinity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by infinity on 04.12.15.
 */
public class SearchServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Document> list = null;
        try {
            list = DataManagement.getInstance().searchFilesByName(req.getParameter("file").replace('?','_').replace('*','%'));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html");
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }
}

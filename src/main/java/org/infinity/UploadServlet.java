package org.infinity;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by infinity on 30.11.15.
 */
@MultipartConfig
public class UploadServlet extends HttpServlet {

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
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Part filePart = req.getPart("description");
        InputStream in = filePart.getInputStream();
        String fileName = filePart.getSubmittedFileName();
        File f = new File(pathOfServer + fileName);
        FileOutputStream fos = new FileOutputStream(f);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
            sha1.update(bytes, 0, read);
        }
        fos.close();
        in.close();
        byte[] hashBytes = sha1.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
            sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        try {
            if (!DataManagement.getInstance().findFileBySha1(sb.toString())){
                f.renameTo(new File(pathOfServer + sb.toString()));
                Document document = new Document();
                document.setName(fileName);
                document.setSize(filePart.getSize());
                document.setSha1(sb.toString());
                DataManagement.getInstance().insertFile(document);
            } else {
                f.delete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/");
    }

}

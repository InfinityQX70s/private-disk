package org.infinity;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 04.12.15.
 */
public class DataManagement {
    private static Connection con;
    private static DataManagement instance;
    private static DataSource dataSource;

    private DataManagement() {
    }

    public static synchronized DataManagement getInstance() {
        if (instance == null) {
            try {
                instance = new DataManagement();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/file");
                con = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public List<Document> searchFilesByName(String wildcards) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM file WHERE name LIKE ? ORDER BY date DESC LIMIT 25");
        stmt.setString(1, wildcards);
        List<Document> list = getListOfElements(stmt.executeQuery());
        stmt.close();
        return list;

    }
    public Document getInfoById(int id) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM file WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet resultSet= stmt.executeQuery();
        Document document = new Document();
        while (resultSet.next()){
            document.setId(resultSet.getInt(1));
            document.setName(resultSet.getString(2));
            document.setDate(resultSet.getTimestamp(3).getTime());
            document.setSize(resultSet.getInt(4));
            document.setSha1(resultSet.getString(5));
        }
        stmt.close();
        return document;

    }

    public boolean findFileBySha1(String sha1) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM file WHERE sha1 = ?");
        stmt.setString(1, sha1);
        ResultSet resultSet = stmt.executeQuery();
        return resultSet.next();
    }

    public void deleteFileById(int id) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("DELETE FROM file WHERE id =  ?");
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }

    public void deleteFileBySha1(String sha1) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("DELETE FROM file WHERE sha1 =  ?");
        stmt.setString(1, sha1);
        stmt.execute();
        stmt.close();
    }

    public void insertFile(Document document) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("INSERT INTO file (name, date, size, sha1) VALUES(?, NOW(), ?, ?)");
        stmt.setString(1, document.getName());
        stmt.setLong(2, document.getSize());
        stmt.setString(3, document.getSha1());
        stmt.execute();
        stmt.close();
    }

    public List<Document> getAllFiles() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM file ORDER BY date DESC");
        List<Document> list = getListOfElements(stmt.executeQuery());
        stmt.close();
        return list;
    }

    private List<Document> getListOfElements(ResultSet resultSet) throws SQLException{
        List<Document> list = new ArrayList<Document>();
        while (resultSet.next()) {
            Document doc = new Document();
            doc.setId(resultSet.getInt(1));
            doc.setName(resultSet.getString(2));
            doc.setDate(resultSet.getTimestamp(3).getTime());
            doc.setSize(resultSet.getInt(4));
            doc.setSha1(resultSet.getString(5));
            list.add(doc);
        }
        resultSet.close();
        return list;
    }

}

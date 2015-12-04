<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ page import="org.infinity.Document" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
<%--    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">--%>
    <title>Private Disk</title>
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Private Disk</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <form class="navbar-form navbar-right" method="post" action="/search">
                <div class="form-group">
                    <input type="text" name="file" placeholder="Search in dictionary" class="form-control">
                </div>
                <button type="submit" class="btn btn-success">Search</button>
            </form>
        </div><!--/.navbar-collapse -->
    </div>
</nav>
<div class="container">
        <form action="/upload" method="post" enctype="multipart/form-data" name="upload">
            <div class="form-group">
                Select file to upload:
                <input type="file" name="description">
                <p class="help-block"></p>
                <input type="submit" class="btn btn-default" name="file">
            </div>
        </form>
    <table class="table table-hover">
        <tr>
            <th>Name</th>
            <th>Date</th>
            <th>Size</th>
            <th></th>
            <th></th>
        </tr>
        <%List<Document> list = (List<Document>) request.getAttribute("list");%>
        <% for (Document element : list) { %>
        <tr>
            <th><%=element.getName()%>
            </th>
            <th><%=new SimpleDateFormat("dd/MM/yyyy").format(element.getDate())%>
            </th>
            <th><%=FileUtils.byteCountToDisplaySize(element.getSize())%>
            </th>
            <th>
                <form action="/delete" method="post">
                    <input type="hidden" name="id" value="<%=element.getId()%>">
                    <input type="hidden" name="sha" value="<%=element.getSha1()%>">
                    <input type="submit" name="submit" value=Delete>
                </form>
            </th>
            <th>
                <form action="/download" method="post">
                    <input type="hidden" name="id" value="<%=element.getId()%>">
                    <input type="hidden" name="sha" value="<%=element.getSha1()%>">
                    <input type="submit" name="submit" value=Download>
                </form>
            </th>
        </tr>
        <% } %>
    </table>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
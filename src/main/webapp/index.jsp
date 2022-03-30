<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    request.getRequestDispatcher("/jsp/index.jsp").forward(request,response);
%>
</body>
</html>

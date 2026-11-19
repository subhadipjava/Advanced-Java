<%@ page import="java.net.InetAddress" %>

<%
    // Client IP Address
    String clientIP = request.getRemoteAddr();

    // URL
    String url = request.getRequestURL().toString();

    // Context Information
    String contextPath = request.getContextPath();
    String serverInfo = application.getServerInfo();

    // Hit Count
    Integer hitCount = (Integer) application.getAttribute("hitCount");

    if (hitCount == null) {
        hitCount = 1;
    } else {
        hitCount++;
    }

    application.setAttribute("hitCount", hitCount);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Server-Side Information</title>

    <style>
        body {
            font-family: Arial;
            margin: 50px;
        }

        h1 {
            color: #333;
        }

        p {
            font-size: 18px;
        }
    </style>
</head>

<body>

    <h1>Server-Side Information</h1>

    <p>
        <b>Client IP Address:</b>
        <%= clientIP %>
    </p>

    <p>
        <b>URL:</b>
        <%= url %>
    </p>

    <p>
        <b>Context Path:</b>
        <%= contextPath %>
    </p>

    <p>
        <b>Server Information:</b>
        <%= serverInfo %>
    </p>

    <p>
        <b>Hit Count:</b>
        <%= hitCount %>
    </p>

</body>
</html>
<%@ page import="java.sql.*" %>

<html>
<head>
    <title>Login Result</title>
</head>

<body>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/jsp_login",
            "root",
            "SUsql@300405"
        );

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM users WHERE username=? AND password=?"
        );

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            out.println("<h2>Login Successful!</h2>");
            out.println("<p>Welcome, " + rs.getString("name") + "!</p>");
            out.println("<p>Batch: " + rs.getString("batch") + "</p>");
            out.println("<p>Email: " + rs.getString("email") + "</p>");
            out.println("<p>Department: " + rs.getString("department") + "</p>");
            out.println("<p>Mobile: " + rs.getString("mobile") + "</p>");
        }
        else
        {
            out.println("<h2>Login Failed!</h2>");
            out.println("<p>Invalid username or password.</p>");
            out.println("<a href='login.html'>Try Again</a>");
        }

        rs.close();
        ps.close();
        con.close();
    }
    catch(Exception e)
    {
        out.println("<h2>Login Failed</h2>");
        out.println("<p>Error: " + e.getMessage() + "</p>");
    }
%>

</body>
</html>
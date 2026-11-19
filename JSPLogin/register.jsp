<%@ page import="java.sql.*" %>

<html>
<head>
    <title>Registration Result</title>
</head>
<body>

<%
    String name = request.getParameter("name");
    String batch = request.getParameter("batch");
    String email = request.getParameter("email");
    String department = request.getParameter("department");
    String mobile = request.getParameter("mobile");
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
            "INSERT INTO users " +
            "(name, batch, email, department, mobile, username, password) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)"
        );

        ps.setString(1, name);
        ps.setString(2, batch);
        ps.setString(3, email);
        ps.setString(4, department);
        ps.setString(5, mobile);
        ps.setString(6, username);
        ps.setString(7, password);

        ps.executeUpdate();

        out.println("<h2>Registration Successful!</h2>");
        out.println("<p>Your registration has been completed successfully.</p>");
        out.println("<a href='login.html'>Go to Login Page</a>");

        ps.close();
        con.close();
    }
    catch(Exception e)
    {
        out.println("<h2>Registration Failed</h2>");
        out.println("<p>Error: " + e.getMessage() + "</p>");
    }
%>

</body>
</html>
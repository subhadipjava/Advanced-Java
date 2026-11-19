
package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/studentdb";

    private static final String DB_USER = "root";

    // Replace with your actual MySQL password.
    // If your MySQL root account has no password, use "".
    private static final String DB_PASSWORD = "SUsql@300405";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String rollNo = request.getParameter("roll_no");
        String department = request.getParameter("department");
        String email = request.getParameter("email");
        String ageText = request.getParameter("age");

        try (PrintWriter out = response.getWriter()) {

            if (name == null || name.trim().isEmpty()
                    || rollNo == null || rollNo.trim().isEmpty()) {
                out.println("<h3>Name and Roll Number are required.</h3>");
                return;
            }

            int age;

            try {
                age = Integer.parseInt(ageText);

                if (age < 1 || age > 150) {
                    throw new NumberFormatException();
                }

            } catch (Exception e) {
                out.println("<h3>Please enter a valid age.</h3>");
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String sql = "INSERT INTO students "
                        + "(name, roll_no, department, email, age) "
                        + "VALUES (?, ?, ?, ?, ?)";

                try (Connection con = DriverManager.getConnection(
                            URL, DB_USER, DB_PASSWORD);
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, name.trim());
                    ps.setString(2, rollNo.trim());
                    ps.setString(3, department);
                    ps.setString(4, email);
                    ps.setInt(5, age);

                    ps.executeUpdate();

                    out.println("<!DOCTYPE html>");
                    out.println("<html><head><title>Registration Result</title>");
                    out.println("<meta charset='UTF-8'></head><body>");
                    out.println("<h2>Student registered successfully!</h2>");
                    out.println("<p>Roll Number: "
                            + escapeHtml(rollNo.trim()) + "</p>");
                    out.println("<p><a href='register.html'>Register another student</a></p>");
                    out.println("<p><a href='search.html'>Search student</a></p>");
                    out.println("</body></html>");

                }

            } catch (SQLException e) {

                if ("23000".equals(e.getSQLState())) {
                    out.println("<h3>This Roll Number is already registered.</h3>");
                } else {
                    getServletContext().log("Student registration failed.", e);
                    out.println("<h3>Registration failed. Check the Tomcat logs.</h3>");
                }

            } catch (ClassNotFoundException e) {
                getServletContext().log("MySQL JDBC driver not found.", e);
                throw new ServletException("MySQL Connector JAR not found.", e);
            }
        }
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}

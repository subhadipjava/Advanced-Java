
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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/studentdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "SUsql@300405";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String rollNo = request.getParameter("roll_no");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html><html><head><title>Student Search</title>");
            out.println("<style>body{font-family:Arial;margin:40px;background:#f4f6f9}"
                    + ".card{background:white;padding:24px;border-radius:12px;"
                    + "max-width:600px;margin:auto}td,th{padding:10px;"
                    + "border:1px solid #ddd;text-align:left}table{border-collapse:"
                    + "collapse;width:100%}a{color:#1769aa}</style></head><body>");
            out.println("<div class='card'><h2>Student Search Result</h2>");

            if (rollNo == null || rollNo.isBlank()) {
                out.println("<p>Please enter a roll number.</p>");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String sql = "SELECT name, roll_no, department, email, age "
                        + "FROM students WHERE roll_no = ?";

                try (Connection con = DriverManager.getConnection(
                             URL, DB_USER, DB_PASSWORD);
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, rollNo.trim());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            out.println("<table>");
                            showRow(out, "Name", rs.getString("name"));
                            showRow(out, "Roll Number", rs.getString("roll_no"));
                            showRow(out, "Department", rs.getString("department"));
                            showRow(out, "Email", rs.getString("email"));
                            showRow(out, "Age", String.valueOf(rs.getInt("age")));
                            out.println("</table>");
                        } else {
                            out.println("<p>No student found with roll number "
                                    + escapeHtml(rollNo.trim()) + ".</p>");
                        }
                    }

                } catch (SQLException e) {
                    getServletContext().log("Student search failed.", e);
                    out.println("<p>Search failed. Check the server logs.</p>");
                }
            }

            out.println("<p><a href='search.html'>Search again</a></p>");
            out.println("<p><a href='register.html'>Register a student</a></p>");
            out.println("</div></body></html>");

        } catch (ClassNotFoundException e) {
            getServletContext().log("MySQL JDBC driver not found.", e);
            throw new ServletException("MySQL Connector JAR not found.", e);
        }
    }

    private void showRow(PrintWriter out, String label, String value) {
        out.println("<tr><th>" + escapeHtml(label) + "</th><td>"
                + escapeHtml(value == null ? "" : value) + "</td></tr>");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}

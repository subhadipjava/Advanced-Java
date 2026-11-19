import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
                           throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db",
                "root",
                "SUsql@300405"
            );

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM employee WHERE id=?"
            );

            ps.setInt(1, Integer.parseInt(id));

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h2>Employee Deleted Successfully!</h2>");
            } else {
                out.println("<h2>Employee ID Not Found!</h2>");
            }

            out.println("<a href='index.html'>Back to Home</a>");

            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UpdateEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
                           throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String salary = request.getParameter("salary");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db",
                "root",
                "SUsql@300405"
            );

            PreparedStatement ps = con.prepareStatement(
                "UPDATE employee SET name=?, department=?, salary=? WHERE id=?"
            );

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, Double.parseDouble(salary));
            ps.setInt(4, Integer.parseInt(id));

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h2>Employee Updated Successfully!</h2>");
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
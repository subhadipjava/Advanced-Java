import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AddEmployee extends HttpServlet {

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
                "INSERT INTO employee (id, name, department, salary) VALUES (?, ?, ?, ?)"
            );

            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setDouble(4, Double.parseDouble(salary));

            ps.executeUpdate();

            out.println("<h2>Employee Added Successfully!</h2>");
            out.println("<a href='index.html'>Back to Home</a>");

            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
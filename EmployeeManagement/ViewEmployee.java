import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ViewEmployee extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_db",
                "root",
                "SUsql@300405"
            );

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT * FROM employee"
            );

            out.println("<html>");
            out.println("<head><title>Employee List</title></head>");
            out.println("<body>");

            out.println("<h2>Employee Details</h2>");

            out.println("<table border='1' cellpadding='10'>");

            out.println("<tr>");
            out.println("<th>Employee ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Department</th>");
            out.println("<th>Salary</th>");
            out.println("</tr>");

            while (rs.next()) {

                out.println("<tr>");

                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("<td>" + rs.getDouble("salary") + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br>");
            out.println("<a href='index.html'>Back to Home</a>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
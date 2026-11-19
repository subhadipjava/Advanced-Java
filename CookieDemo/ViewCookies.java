import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ViewCookies extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>View Cookies</title>");

        out.println("<style>");

        out.println("body {");
        out.println("font-family: Arial;");
        out.println("margin: 60px;");
        out.println("}");

        out.println("h1 {");
        out.println("font-size: 36px;");
        out.println("}");

        out.println("p {");
        out.println("font-size: 20px;");
        out.println("}");

        out.println("a {");
        out.println("font-size: 20px;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<h1>Cookies Currently Present</h1>");

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("College")) {

                    out.println("<p>Persistent Cookie: "
                            + cookie.getName()
                            + " = "
                            + cookie.getValue()
                            + "</p>");
                }

                if (cookie.getName().equals("User")) {

                    out.println("<p>Non-Persistent Cookie: "
                            + cookie.getName()
                            + " = "
                            + cookie.getValue()
                            + "</p>");
                }
            }

        } else {

            out.println("<p>No cookies found.</p>");
        }

        out.println("<br>");

        out.println("<a href='CookieServlet'>Create Cookies</a>");

        out.println("</body>");

        out.println("</html>");
    }
}
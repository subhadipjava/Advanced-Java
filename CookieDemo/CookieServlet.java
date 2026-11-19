import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CookieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // Create Non-Persistent Cookie
        Cookie userCookie = new Cookie("User", "Subhadip");

        // No setMaxAge() means non-persistent/session cookie
        response.addCookie(userCookie);


        // Create Persistent Cookie
        Cookie collegeCookie = new Cookie("College", "Amity");

        // Persistent for 1 day
        collegeCookie.setMaxAge(24 * 60 * 60);

        response.addCookie(collegeCookie);


        // Display webpage
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Cookies Demo</title>");

        out.println("<style>");

        out.println("body {");
        out.println("font-family: Arial;");
        out.println("margin: 60px;");
        out.println("}");

        out.println("h1 {");
        out.println("font-size: 36px;");
        out.println("}");

        out.println("h2 {");
        out.println("margin-top: 30px;");
        out.println("}");

        out.println("a {");
        out.println("font-size: 20px;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<h1>Cookies Created Successfully!</h1>");

        out.println("<h2>Non-Persistent: User = Subhadip</h2>");

        out.println("<h2>Persistent: College = Amity (1 day)</h2>");

        out.println("<br>");

        out.println("<a href='ViewCookies'>View Cookies</a>");

        out.println("<br><br>");

        out.println("<h2>Cookies Currently Present</h2>");

        // Read cookies
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
        }

        out.println("<br>");

        out.println("<a href='CookieServlet'>Create Cookies</a>");

        out.println("</body>");

        out.println("</html>");
    }
}
package pada.da.servlets.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Zaehler")
public class CounterServlet extends HttpServlet {
    private Counter counter;

    public void init() {
        counter = new Counter();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Z&auml;hler</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Z&auml;hler</h1>");
        String message = "";
        String operation1 = request.getParameter("increment");
        if (operation1 != null) {
            int value = counter.increment();
            message = "Der Z&auml;hler wurde auf " + value + " erh&ouml;ht.<p>";
        }
        String operation2 = request.getParameter("reset");
        if (operation2 != null) {
            int value = counter.reset();
            message = "Der Z&auml;hler wurde auf " + value
                    + " zur&uuml;ckgesetzt.<p>";
        }
        out.println(message);
        out.println("<form method=\"post\">");
        out.println("<input type=\"submit\" name=\"increment\" "
                + "value=\"Erh&ouml;hen\">");
        out.println("<input type=\"submit\" name=\"reset\" "
                + "value=\"Zur&uuml;cksetzen\">");
        out.println("</form");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
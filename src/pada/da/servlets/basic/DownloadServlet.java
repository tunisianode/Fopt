package pada.da.servlets.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Herunterladen")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        String linesString = request.getParameter("lines");
        int numberOfLines = 0;
        try {
            numberOfLines = Integer.parseInt(linesString);
            if (numberOfLines <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Fehler</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Der eingegebene Text '" + linesString +
                    "' ist keine positive ganze Zahl.</h3>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"lines.txt\"");
        PrintWriter out = response.getWriter();
        for (int i = 1; i <= numberOfLines; i++) {
            out.println("Zeile " + i);
        }
    }
}

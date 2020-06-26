package pada.da.servlets.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(value = "/Hochladen")
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Antwort auf Hochladen" + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Antwort auf Hochladen</h1>");
        out.println("<h3>Folgende Kopffelder sind " +
                "angekommen:</h3>");
        out.println("<pre>");

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            String hvalue = request.getHeader(header);
            out.println(header + ": " + hvalue);
        }
        out.println("</pre>");

        out.println("<h3>Folgende Daten sind "
                + "angekommen:</h3>");
        out.println("<pre>");
        BufferedReader br = request.getReader();
        String line;
        while ((line = br.readLine()) != null) {
            out.println(line); // --> Ausgabe der Datei
        }
        out.println("</pre>");
        out.println("</body></html>");
    }
}

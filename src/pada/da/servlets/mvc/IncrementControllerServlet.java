package pada.da.servlets.mvc;

import pada.da.servlets.basic.Counter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/mvc_erhoehen")
public class IncrementControllerServlet extends HttpServlet
{
    private static final long serialVersionUID = 7968546825609574805L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        Counter requestCounter = (Counter)request.getAttribute("requestCounter");
        if(requestCounter == null)
        {
            requestCounter = new Counter();
            request.setAttribute("requestCounter", requestCounter);
        }
        requestCounter.increment();
        
        HttpSession session = request.getSession(true);
        Counter sessionCounter = (Counter)session.getAttribute("sessionCounter");
        if(sessionCounter == null)
        {
            sessionCounter = new Counter();
            session.setAttribute("sessionCounter", sessionCounter);
        }
        sessionCounter.increment();
        
        ServletContext application = getServletContext();
        Counter applicationCounter = (Counter)application.getAttribute("applicationCounter");
        if(applicationCounter == null)
        {
            applicationCounter = new Counter();
            application.setAttribute("applicationCounter", applicationCounter);
        }
        applicationCounter.increment();
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/modelOkay.jsp");
        dispatcher.forward(request, response);
    }
}

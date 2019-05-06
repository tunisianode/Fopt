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

@WebServlet("/mvc_setzen")
public class SetControllerServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
              throws IOException, ServletException
    {
        boolean inputOkay = false;
        int newValue = 0;
        String newValueString = request.getParameter("newValue");
        if(newValueString != null)
        {
            try
            {
                newValue = Integer.parseInt(newValueString);
                inputOkay = true;
            }
            catch(NumberFormatException e)
            {
            }
        }
        if(!inputOkay)
        {
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/modelError.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        Counter requestCounter =
                    (Counter)request.getAttribute("requestCounter");
        if(requestCounter == null)
        {
            requestCounter = new Counter();
            request.setAttribute("requestCounter", requestCounter);
        }
        requestCounter.set(newValue);
        
        HttpSession session = request.getSession(true);
        Counter sessionCounter;
        synchronized(session)
        {
            sessionCounter =
                    (Counter)session.getAttribute("sessionCounter");
            if(sessionCounter == null)
            {
                sessionCounter = new Counter();
                session.setAttribute("sessionCounter",
                                     sessionCounter);
            }
        }
        sessionCounter.set(newValue);
        
        ServletContext application = getServletContext();
        Counter applicationCounter;
        synchronized(application)
        {
            applicationCounter =
                         (Counter)application.getAttribute(
                                              "applicationCounter");
            if(applicationCounter == null)
            {
                applicationCounter = new Counter();
                application.setAttribute("applicationCounter",
                                         applicationCounter);
            }
        }
        applicationCounter.set(newValue);
        
        RequestDispatcher dispatcher =
                     request.getRequestDispatcher("/modelOkay.jsp");
        dispatcher.forward(request, response);
    }
}

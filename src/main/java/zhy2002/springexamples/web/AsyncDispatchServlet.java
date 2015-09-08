package zhy2002.springexamples.web;


import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(value = "/dispatch", asyncSupported = true)
public class AsyncDispatchServlet extends TimeServlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        AsyncContext asyncContext = servletRequest.startAsync();
        servletResponse.getWriter().write("dispatching.\r\n");
        asyncContext.dispatch("/target");
        servletResponse.getWriter().write("dispatched.\r\n");

    }
}


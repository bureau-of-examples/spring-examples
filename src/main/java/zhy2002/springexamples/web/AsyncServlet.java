package zhy2002.springexamples.web;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends TimeServlet{

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setContentType("text/html");
        servletResponse.getWriter().write("Creating async context @Thread" + Thread.currentThread().getId() + "<br>");
        final AsyncContext asyncContext = servletRequest.startAsync();
        servletResponse.getWriter().write("Creating worker thread @Thread" + Thread.currentThread().getId() +  "<br>");
        new Thread(()->{
            try {
                servletResponse.getWriter().write("Beginning long task @Thread" + Thread.currentThread().getId() +  "<br>");
                Thread.sleep(1000);
                servletResponse.getWriter().write("Long task completed @Thread" + Thread.currentThread().getId() +  "<br>");
                asyncContext.complete();
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }

        }).start();
        servletResponse.getWriter().write("Exiting service method @Thread" + Thread.currentThread().getId() +  "<br>");
    }
}

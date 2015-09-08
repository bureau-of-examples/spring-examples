package zhy2002.springexamples.web;


import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Scanner;

@WebServlet("/test")
public class ForwardingServlet extends TimeServlet {



    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        //get class path resource
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletRequest.getServletContext());
        Scanner scanner = new Scanner( context.getResource("classpath:logback.xml").getInputStream());
        while (scanner.hasNextLine())
            servletResponse.getWriter().write(scanner.nextLine());

        getServletConfig().getServletContext().getRequestDispatcher("/test1.html").include(servletRequest, servletResponse);

        //get servlet context resource
        scanner = new Scanner( context.getResource("views/test1view.jsp").getInputStream());
        while (scanner.hasNextLine())
            servletResponse.getWriter().write(scanner.nextLine());

    }
}

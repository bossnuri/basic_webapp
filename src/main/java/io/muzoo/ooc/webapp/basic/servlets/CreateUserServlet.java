package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.security.SecurityService;
import io.muzoo.ooc.webapp.basic.security.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends AbstractRoutableHttpServlet {

    public void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);

        if (authorized) {
            String username = (String) request.getSession().getAttribute("username");
            UserService userService = UserService.getInstance();

            request.setAttribute("user", userService.findByUsername(username));

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/create.jsp");
            requestDispatcher.include(request, response);

            request.getSession().removeAttribute("hasError");
            request.getSession().removeAttribute("message");
        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }
    }
    @Override
    public String getPattern() {
        return "/user/create";
    }
}

package edu.sru.cpsc.webshopping.util.advice;

import java.io.IOException;
import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import edu.sru.cpsc.webshopping.secure.UnauthenticatedUserException;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class PrincipalControllerAdvice {
    
    @ExceptionHandler(UnauthenticatedUserException.class)
    public void handleUnauthenticated(Principal principal, HttpServletResponse response) throws IOException {
        if (principal == null) {
            response.sendRedirect("/index");
        }
    }
    
}

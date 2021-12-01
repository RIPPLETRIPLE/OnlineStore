package testCommands.testGuestCommands;

import com.training.InternetStore.controller.command.guestCommand.Login;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class testLogin {
    private final Login login = new Login();
    private final HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    private final HttpSession session = mock(HttpSession.class);

    @AfterEach
    public void resetMocks() {
        reset(session, httpRequest);
    }

    @Test
    void testLoginVoidLogin() {
        when(httpRequest.getParameter("login")).thenReturn(null);

        try {
            assertEquals(JSPPageConstants.LOGIN_PAGE, login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void testPasswordVoidLogin() {
        when(httpRequest.getParameter("login")).thenReturn("Roman");
        when(httpRequest.getParameter("password")).thenReturn(null);

        try {
            assertEquals(JSPPageConstants.LOGIN_PAGE, login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void testValidLogin() {
        ServletContext context = mock(ServletContext.class);
        when(httpRequest.getParameter("login")).thenReturn("Roman");
        when(httpRequest.getParameter("password")).thenReturn("Roman123");
        when(httpRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(new HashSet<>());
        when(session.getAttribute("cart")).thenReturn(null);

        try {
            assertEquals("redirect:/app/user/mainPage", login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }
    @Test
    void testInvalidLoginOrPassword() {
        when(httpRequest.getParameter("login")).thenReturn("smth");
        when(httpRequest.getParameter("password")).thenReturn("smth");
        when(httpRequest.getSession()).thenReturn(session);
        try {
            assertEquals("redirect:/app/guest/login", login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }
    @Test
    void testUserAlreadyLogged() {
        ServletContext context = mock(ServletContext.class);
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Roman");

        when(httpRequest.getParameter("login")).thenReturn("Roman");
        when(httpRequest.getParameter("password")).thenReturn("Roman123");
        when(httpRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(hashSet);

        try {
            assertEquals("redirect:/app/guest/login", login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }
}

package testCommands.testGuestCommands;

import com.training.InternetStore.controller.command.guestCommand.Registration;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class testRegistration {
    private final Registration registration = new Registration();
    private final HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    private final HttpSession session = mock(HttpSession.class);

    @AfterEach
    public void resetMocks() {
        reset(session, httpRequest);
    }

    @Test
    void testVoidLogin() {
        when(httpRequest.getParameter("login")).thenReturn(null);
        try {
            assertEquals(JSPPageConstants.REGISTRATION_PAGE, registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void testVoidPassword() {
        when(httpRequest.getParameter("password")).thenReturn(null);
        try {
            assertEquals(JSPPageConstants.REGISTRATION_PAGE, registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }

    @Test
    void testInvalidLoginOrPassword() {
        when(httpRequest.getParameter("login")).thenReturn("123");
        when(httpRequest.getParameter("password")).thenReturn("123");
        when(httpRequest.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("1");
        try {
            assertEquals("redirect:/app/guest/registration", registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }
    @Test
    void testLoginAlreadyExist() {
        when(httpRequest.getParameter("login")).thenReturn("Roman");
        when(httpRequest.getParameter("password")).thenReturn("Roman123");
        when(httpRequest.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("1");
        try {
            assertEquals("redirect:/app/guest/registration", registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }
}

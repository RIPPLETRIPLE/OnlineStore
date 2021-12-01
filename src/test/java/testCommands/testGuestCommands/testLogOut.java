package testCommands.testGuestCommands;

import com.training.InternetStore.controller.command.guestCommand.LogOut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class testLogOut {
    private final LogOut logOut = new LogOut();
    private final HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    private final HttpSession session = mock(HttpSession.class);

    @AfterEach
    public void resetMocks() {
        reset(session, httpRequest);
    }

    @Test
    void testLogOut() {
        when(httpRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        assertEquals("redirect:/app/guest/mainPage", logOut.execute(httpRequest));
    }
}

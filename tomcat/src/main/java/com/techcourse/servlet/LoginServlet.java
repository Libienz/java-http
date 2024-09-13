package com.techcourse.servlet;

import com.techcourse.SessionManager;
import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.apache.coyote.http11.Servlet;
import org.apache.coyote.http11.request.HttpServletRequest;
import org.apache.coyote.http11.response.HttpServletResponse;
import org.apache.util.FileUtils;

public class LoginServlet implements Servlet {

    private static final String LOGIN_FAIL_PAGE = "/401.html";
    private static final String LOGIN_SUCCESS_REDIRECT_URI = "http://localhost:8080/index.html";
    private static final SessionManager sessionManager = SessionManager.getInstance();
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";

    @Override
    public void doService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = request.getFormData(ACCOUNT);
        String password = request.getFormData(PASSWORD);

        Optional<User> found = InMemoryUserRepository.findByAccount(account);
        if (found.isEmpty() || !found.get().checkPassword(password)) {
            response.unauthorized(FileUtils.readFile(LOGIN_FAIL_PAGE), "html");
            return;
        }

        UUID sessionId = sessionManager.putUserSession(found.get());

        response.redirect(LOGIN_SUCCESS_REDIRECT_URI);
        response.setJsessionCookie(sessionId);
    }
}

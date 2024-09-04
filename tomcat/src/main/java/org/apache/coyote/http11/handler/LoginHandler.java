package org.apache.coyote.http11.handler;

import java.io.IOException;
import org.apache.coyote.http11.Http11Processor;
import org.apache.coyote.http11.request.HttpProtocol;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.Method;
import org.apache.coyote.http11.request.Uri;
import org.apache.coyote.http11.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements HttpRequestHandler {
    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";
    private static final Method SUPPORTING_METHOD = Method.GET;
    private static final Uri SUPPORTING_URI = new Uri("/login");
    private static final HttpProtocol SUPPORTING_PROTOCOL = HttpProtocol.HTTP_11;

    @Override
    public boolean supports(final HttpRequest request) {
        if (request.isMethodNotEqualWith(SUPPORTING_METHOD)) {
            return false;
        }
        if (request.isHttpProtocolNotEqualWith(SUPPORTING_PROTOCOL)) {
            return false;
        }
        if (request.isUriNotStartsWith(SUPPORTING_URI)) {
            return false;
        }
        if (request.getQueryParameter(ACCOUNT_PARAMETER) == null) {
            return false;
        }
        if (request.getQueryParameter(PASSWORD_PARAMETER) == null) {
            return false;
        }
        return true;
    }

    @Override
    public HttpResponse handle(final HttpRequest request) throws IOException {
        String account = request.getQueryParameter(ACCOUNT_PARAMETER);
        String password = request.getQueryParameter(PASSWORD_PARAMETER);

        log.info("login request id: {}, password: {}", account, password);
        return HttpResponse.ok("ok", "html");
    }
}

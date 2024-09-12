package org.apache.coyote.http11.request;

import org.apache.coyote.http11.common.HttpCookies;
import org.apache.coyote.http11.common.HttpHeaders;
import org.apache.coyote.http11.common.HttpMessageBody;
import org.apache.coyote.http11.common.HttpProtocol;
import org.apache.coyote.http11.request.line.Method;
import org.apache.coyote.http11.request.line.RequestLine;
import org.apache.coyote.http11.request.line.Uri;

public class HttpServletRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final HttpMessageBody httpMessageBody;

    public HttpServletRequest(RequestLine requestLine, HttpHeaders httpHeaders, HttpMessageBody httpMessageBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.httpMessageBody = httpMessageBody;
    }

    public boolean methodEquals(Method method) {
        return requestLine.isMethod(method);
    }

    public boolean protocolEquals(HttpProtocol httpProtocol) {
        return requestLine.isHttpProtocol(httpProtocol);
    }

    public boolean uriEquals(Uri uri) {
        return requestLine.isUri(uri);
    }

    public boolean isUriHome() {
        return requestLine.isUriHome();
    }

    public String getUriPath() {
        return requestLine.getUriPath();
    }

    public String getFormData(String name) {
        return httpMessageBody.getFormData(name);
    }

    public String getSessionId() {
        HttpCookies cookies = HttpCookies.from(httpHeaders);
        return cookies.getCookieValue("JSESSIONID");
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public HttpProtocol getProtocol() {
        return requestLine.getProtocol();
    }
}

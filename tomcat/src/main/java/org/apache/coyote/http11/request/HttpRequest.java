package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequest(final RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public static HttpRequest from(String requestLine) {
        String[] requestElements = requestLine.split(" ");

        Method method = Method.from(requestElements[0]);
        Uri uri = new Uri(requestElements[1]);
        HttpProtocol protocol = HttpProtocol.from(requestElements[2]);

        return new HttpRequest(new RequestLine(method, uri, protocol));
    }

    public void addHeader(String headerLine) {
        String[] headerParts = headerLine.split(": ");
        String key = headerParts[0];
        String value = headerParts[1];
        headers.put(key, value);
    }

    public boolean isMethodNotEqualWith(Method method) {
        return !method.equals(getMethod());
    }

    public boolean isHttpProtocolNotEqualWith(HttpProtocol httpProtocol) {
        return !httpProtocol.equals(getHttpProtocol());
    }

    public boolean isUriNotEqualWith(Uri uri) {
        return !uri.equals(getUri());
    }

    public boolean isUriHome() {
        return getUri().equals(new Uri("/"));
    }

    public boolean isUriNotStartsWith(Uri uri) {
        return !getUri().isStartsWith(uri);
    }

    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public Uri getUri() {
        return requestLine.getUri();
    }

    public HttpProtocol getHttpProtocol() {
        return requestLine.getProtocol();
    }

    public String getUriPath() {
        return requestLine.getUriPath();
    }

    public String getQueryParameter(final String paramterName) {
        String queryString = getUri().getQueryString();
        Map<String, String> queryParams = new HashMap<>();

        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            queryParams.put(keyValue[0], keyValue[1]);
        }
        return queryParams.get(paramterName);
    }
}

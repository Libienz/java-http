package com.techcourse.controller;

import java.io.IOException;
import org.apache.coyote.http11.HttpProtocol;
import org.apache.coyote.http11.HttpRequestHandler;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.line.Method;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.util.FileUtils;

public class StaticResourceController implements HttpRequestHandler {

    private static final String STATIC_RESOURCE_PATH = "static";
    private static final Method SUPPORTING_METHOD = Method.GET;
    private static final HttpProtocol SUPPORTING_PROTOCOL = HttpProtocol.HTTP_11;

    @Override
    public boolean supports(HttpRequest request) {
        return request.methodEquals(SUPPORTING_METHOD) &&
                request.protocolEquals(SUPPORTING_PROTOCOL) &&
                !request.isUriHome() &&
                resourceAvailable(request.getUriPath());
    }

    @Override
    public HttpResponse handle(HttpRequest request) throws IOException {
        final String fileName = request.getUriPath();
        String fileContent = FileUtils.readFile(fileName);
        return HttpResponse.ok(fileContent, FileUtils.getFileExtension(fileName));
    }

    private boolean resourceAvailable(String fileName) {
        try {
            final String filePath = getClass().getClassLoader().getResource(STATIC_RESOURCE_PATH + fileName).getFile();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}

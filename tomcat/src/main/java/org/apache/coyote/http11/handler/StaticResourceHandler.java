package org.apache.coyote.http11.handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import org.apache.coyote.http11.request.HttpProtocol;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.Method;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.util.FileUtils;

public class StaticResourceHandler implements HttpRequestHandler {

    private static final Method SUPPORTING_METHOD = Method.GET;
    private static final HttpProtocol SUPPORTING_PROTOCOL = HttpProtocol.HTTP_11;
    private static final String STATIC_RESOURCE_PATH = "static";

    @Override
    public boolean supports(HttpRequest request) {
        if (request.isMethodNotEqualWith(SUPPORTING_METHOD)) {
            return false;
        }
        if (request.isHttpProtocolNotEqualWith(SUPPORTING_PROTOCOL)) {
            return false;
        }
        if (request.isUriHome()) {
            return false;
        }
        try {
            final String fileName = request.getUriPath();
            final String filePath = getClass().getClassLoader().getResource(STATIC_RESOURCE_PATH + fileName).getFile();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public HttpResponse handle(HttpRequest request) throws IOException {
        final String fileName = request.getUriPath();
        String fileContent = readFile(fileName);
        return HttpResponse.ok(fileContent, FileUtils.getFileExtension(fileName));
    }

    private String readFile(final String fileName) throws IOException {
        URL resource = getClass().getClassLoader().getResource(STATIC_RESOURCE_PATH + fileName);
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}

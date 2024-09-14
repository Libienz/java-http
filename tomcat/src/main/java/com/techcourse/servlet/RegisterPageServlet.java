package com.techcourse.servlet;

import java.io.IOException;
import org.apache.catalina.servlet.Servlet;
import org.apache.coyote.http11.request.HttpServletRequest;
import org.apache.coyote.http11.response.HttpServletResponse;
import org.apache.util.FileUtils;

public class RegisterPageServlet implements Servlet {

    private static final String PAGE_RESOURCE_PATH = "/register.html";

    @Override
    public void doService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileContent = FileUtils.readFile(PAGE_RESOURCE_PATH);
        response.ok(fileContent, "html");
    }
}

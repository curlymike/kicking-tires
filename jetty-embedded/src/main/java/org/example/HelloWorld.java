package org.example;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorld extends AbstractHandler {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new HelloWorld());
        server.start();
        server.join();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Declare response encoding and types
        response.setContentType("text/html; charset=utf-8");

        // Declare response status code
        response.setStatus(HttpServletResponse.SC_OK);

        // Write back response
        response.getWriter().println("<h1>Hello, World!</h1><h2>It's Jetty!</h2>");

        // Inform jetty that this request has now been handled
        baseRequest.setHandled(true);
    }

}
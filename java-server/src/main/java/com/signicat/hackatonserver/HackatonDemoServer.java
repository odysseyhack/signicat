package com.signicat.hackatonserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class HackatonDemoServer {

    private HackatonDemoServer() {
    }

    private static final Logger LOG = LogManager.getLogger(HackatonDemoServer.class.getName());

    public static void main(final String[] args) {
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        final Server server = new Server(8080);
        server.setHandler(context);
        final ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                HackatonDemoResource.class.getCanonicalName());
        try {
            server.start();
            server.join();
            // CSOFF: IllegalCatch - jetty start likes exception
        } catch (final Exception e) {
            LOG.error(e);
        } finally {
            try {
                server.stop();
            } catch (final Exception e) {
                LOG.error(e);
            }
            server.destroy();
        }
    }
}

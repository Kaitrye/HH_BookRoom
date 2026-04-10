package hh.school.BookRoom;

import org.eclipse.jetty.ee11.servlet.ServletContextHandler;
import org.eclipse.jetty.ee11.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import resources.BookingResource;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler();

        ResourceConfig config = new ResourceConfig();
        config.register(BookingResource.class);
        config.register(RequestLoggingInterceptor.class);
        config.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        ServletContainer servlet = new ServletContainer(config);
        ServletHolder servletHolder = new ServletHolder(servlet);

        context.addServlet(servletHolder, "/*");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        }
        finally {
            server.destroy();
        }
    }
}

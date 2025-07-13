import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {

        Templates.parseTemplates();

        Server server = new Server(Cnfg.PORT);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(NewHandler.class, "/new");
        servletContextHandler.addServlet(IndexHandler.class, "/");
        server.setHandler(servletContextHandler);
        server.start();
        server.join();

    }
}

package server;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

public class ServerMain{
    public static final int PORT = 5004;
    public static final String URI = "https://0.0.0.0/";
    public static final String[] RESOURCE_PACKAGES = {"server.resources"};

    public static HttpServer startServer(){
        URI baseUri = UriBuilder.fromUri(URI).port(PORT).build();
        final ResourceConfig config = new ResourceConfig().packages(RESOURCE_PACKAGES);
        return GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }

    public static void main (String[] args){
        startServer();
    }
}
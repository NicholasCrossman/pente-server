package server.resources;

import com.google.gson.Gson;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.ResourceController;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class GameResourceTest {

    private static final String HOST_URI = "http://localhost:3456/";
    private static HttpServer server;
    private static Client client;
    private static ResourceController controller;
    public static final int PORT = 3456;
    public static final String URI = "https://0.0.0.0/";
    public static final String[] RESOURCE_PACKAGES = {"server.resources"};

    public static HttpServer startServer(){
        java.net.URI baseUri = UriBuilder.fromUri(URI).port(PORT).build();
        final ResourceConfig config = new ResourceConfig().packages(RESOURCE_PACKAGES);
        return GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }

    // This starts the server and creates the client object once before all tests in this class
    @BeforeClass
    public static void start() {
        server = startServer();
        client = ClientBuilder.newClient();
        controller = ResourceController.getController();
    }

    // This shuts the server down and closes the client after all tests in this class are complete
    @AfterClass
    public static void stopServer() {
        if( client != null ) client.close();
        if( server != null ) server.shutdown();
    }

    @Test
    public void testLogin() {
        //need to add username first
        // The data to send with the PUT
        Entity data = Entity.entity("{\"userName\":\"Player1\",\"password\":\"password\"}", MediaType.TEXT_PLAIN);

        Response response = client.target(HOST_URI)
                .path("user/login")
                .request(MediaType.TEXT_PLAIN)
                .put(data);

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetUser() {
        //need to add user first
        String response = client.target(HOST_URI)
                .path("user/Player1")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);

        Assert.assertEquals("\"Player1_0_0_0\"", response);
    }

    @Test
    public void testNewUser(){
        // The data to send with the PUT
        Entity data = Entity.entity("{\"userName\" : \"testUser\", \"password\" : \"testPassword\"}", MediaType.TEXT_PLAIN);

        Response response = client.target(HOST_URI)
                .path("user")
                .request(MediaType.TEXT_PLAIN)
                .post(data);

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetGames(){
        //TODO fix this test so that it doesn't require the date
        //need to make user first
        Gson gson = new Gson();
        String[] response = client.target(HOST_URI)
                .path("user/Player1/games")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class).split("\n");
        int expected= 1;
                //"\"Hammerlock_CL4P-TP_\\tgame.rules.Pente\\tCL4P-TP_MrTorgue_i\\tgame.rules.Pente\\t\"" ;
        Assert.assertEquals(expected, response.length);
    }

    @Test
    public void testGetGameState(){
        Response response = client.target(HOST_URI)
                .path("game/Player1/games/0")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void testChangeBoard(){

    }

}
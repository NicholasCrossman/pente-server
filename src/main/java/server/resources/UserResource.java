package server.resources;

import com.google.gson.Gson;
import game.rules.Pente;
import server.ResourceController;
import server.inputs.CreateGame;
import server.inputs.Login;
import server.inputs.UserInfo;
import user.User;

import javax.ws.rs.*;

@Path("user")
public class UserResource {
    private ResourceController controller;

    public UserResource() {
        controller = ResourceController.getController();
    }

    /**
     * tries to validate the input for an existing user
     * @param input - Login class as json
     * @return json of UserInfo
     * @throws 404 if user does not exist
     * @throws 401 if login credentials are bad
     */
    @PUT
    @Path("login")
    public String login(String input){
        Gson gson = new Gson();
        Login fromClient = gson.fromJson(input, Login.class);

        if (controller.getUser(fromClient.userName) == null){
            throw new WebApplicationException(404);
        }

        User current = controller.login(fromClient.userName, fromClient.password);
        if (current == null){
            throw new WebApplicationException(401);
        }
        UserInfo output = new UserInfo(current.getUserName(), current.getRecord().toData());
        gson = new Gson();
        return gson.toJson(output);
    }

    /**
     * Creates a new User for the class
     * @param input - Login class as json
     * @return json of UserInfo
     */
    @POST
    public String newUser(String input){
        Gson gson = new Gson();
        Login fromClient = gson.fromJson(input, Login.class);

        if (!controller.newUser(fromClient.userName, fromClient.password)){
            throw new WebApplicationException(401);
        }

        User current = controller.getUser(fromClient.userName);
        UserInfo output = new UserInfo(current.getUserName(), current.getRecord().toData());

        gson = new Gson();
        return gson.toJson(output);
    }

    /**
     * returns the info of a requested user
     * @param userName - name of current user
     * @return json of UserInfo
     * @throws 404 if the user does not exist
     */
    @GET
    @Path("{userName}")
    public String getUser(@PathParam("userName") String userName){
        User current = controller.getUser(userName);

        if (current == null){
            throw new WebApplicationException(404);
        }
        String user = new String(current.getUserName() +"_"+ current.getRecord().toData());

        Gson gson = new Gson();
        return gson.toJson(user);
    }

    /**
     * Returns a String listing the in-progress Games that the
     * current User can access.
     * @param userName - name of current user
     * @return list of in progress games as a json of String
     */
    @GET
    @Path("{userName}/games")
    public String getGames(@PathParam("userName") String userName){
        Gson gson = new Gson();
        String output;
        output = controller.getInProgress(userName);
        return gson.toJson(output);
    }

    /**
     * Returns a String listing the in-progress Games that the
     * current User can access as a spectator. Does not return
     * Games where the user is an active player.
     * @param userName - name of current user
     * @return list of spectatable games as a json of String
     */
    @GET
    @Path("{userName}/games/spectate")
    public String getSpectatorGames(@PathParam("userName") String userName){
        Gson gson = new Gson();
        String output;
        output = controller.getObservableGames(userName);
        return gson.toJson(output);
    }

    /**
     * Returns a String listing the completed Games that the
     * current User can access.
     * @param userName - name of current user
     * @return list of in progress games as a json of String
     */
    @GET
    @Path("{userName}/games/completed")
    public String getCompletedGames(@PathParam("userName") String userName){
        Gson gson = new Gson();
        String output;
        output = controller.getCompleted(userName);
        return gson.toJson(output);
    }

    /**
     * generates a new game
     * @param userName - name of current user
     * @param input - CreateGame as json
     * @return Save name of the new game
     * @throws 401 if a game in progress exists between the two players already
     */
    @POST
    @Path("{userName}/games")
    public String createGame(@PathParam("userName") String userName,String input){
        Gson gson=new Gson();
        CreateGame g=gson.fromJson(input,CreateGame.class);
        String output = controller.createGame(userName,g.user2,new Pente(), g.publicVisibility);
        if (output.equals("")){
            throw new WebApplicationException(401);
        }
        gson = new Gson();
        return gson.toJson(output);
    }
}

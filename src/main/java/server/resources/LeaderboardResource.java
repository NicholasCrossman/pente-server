package server.resources;

import com.google.gson.Gson;
import server.ResourceController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.awt.*;

@Path("leaderboard")
public class LeaderboardResource {
    private ResourceController controller;

    public LeaderboardResource() {
        controller = ResourceController.getController();
    }

    /**
     * requests a string with the leaderboard
     * @return leaderboard as a single json of String
     */
    @GET
    public String getLeaderBoard(){
        String output = controller.getLeaderBoard();
        Gson gson = new Gson();
        return gson.toJson(output);
    }
}

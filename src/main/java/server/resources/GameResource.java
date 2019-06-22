package server.resources;

import com.google.gson.Gson;
import game.Game;
import server.ResourceController;
import server.inputs.*;
import game.Move;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.HashMap;

@Singleton
@Path("game")
public class GameResource{
    private ResourceController controller;
    private Sse sse;
    private HashMap<String, SseBroadcaster> gameBroadcasters;

    public GameResource(@Context final Sse sse){
        controller = ResourceController.getController();
        this.sse = sse;
        this.gameBroadcasters = new HashMap<String, SseBroadcaster>();
    }

    @GET
    @Path("{gameId}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void subscribe(@Context SseEventSink eventSink, @PathParam("gameId") String gameId){
        if (!gameBroadcasters.containsKey(gameId)){
            gameBroadcasters.put(gameId, sse.newBroadcaster());
        }
        gameBroadcasters.get(gameId).register(eventSink);
    }

    /**
     *
     * @param userName - name of current user
     * @param gameId - save name of requested game
     * @return requested Game board as int[][] in json
     * @throws 404 if the id is wrong
     */
    @GET
    @Path("{userName}/games/{gameId}")
    public String getGameState(@PathParam("userName") String userName, @PathParam("gameId") String gameId){
        Game current = controller.getGameState(userName, gameId);

        if (current == null) {
            throw new WebApplicationException(404);
        }

        Gson gson = new Gson();
        return gson.toJson(current.getBoard());
    }

    /**
     *
     * @param userName name of current user
     * @param gameId id of current game
     * @param input json of ClientMove
     * @return true if user records have changed, false if just the board has changed
     * @throws 404 if the move is invalid in any way
     */
    @PUT
    @Path("{userName}/games/{gameId}")
    public String changeBoard(@PathParam("userName") String userName, @PathParam("gameId") String gameId, String input){
        Gson gson = new Gson();
        ClientMove fromClient = gson.fromJson(input, ClientMove.class);
        Move currentMove = new Move(fromClient.player, fromClient.x, fromClient.y);
        boolean output;
        int response = controller.updateGame(userName, gameId, currentMove);
        if ( response == -1){
            throw new WebApplicationException(404);
        } else if(response == 0) {
            output = false;
        }else if (response ==-2){
            throw new WebApplicationException(401);
        }else if (response ==-3){
            throw new WebApplicationException(400);
        } else {
            output = true;
        }
        if (gameBroadcasters.containsKey(gameId)){
            UpdateObject data=new UpdateObject(controller.getGameState(userName,gameId).getTurn(),controller.getGameState(userName, gameId).getBoard());
            final OutboundSseEvent event = sse.newEventBuilder()
                    .name("boardUpdate")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(String.class, gson.toJson(data))
                    .build();
            gameBroadcasters.get(gameId).broadcast(event);
        }

        gson = new Gson();
        return gson.toJson(output);
    }

}
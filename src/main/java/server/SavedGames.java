package server;

import game.Game;
import game.rules.Pente;
import user.User;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SavedGames {
    private HashMap<String, Game> gameList;

    /**
     * Constructor to create a SavedGames object with an empty gameList.
     */
    public SavedGames(){
        gameList=new HashMap<String, Game>();
    }

    /**
     * Returns a Game by its unique saveName.
     * @param name String - The saveName of the Game.
     * @return Game - The Game object with the specified saveName.
     */
    public Game loadGame(String name) {
        Game g = gameList.get(name);
        if (g != null){
            return g;
        }
        else
            return null;
    }

    /**
     * Saves a Game to the HashMap with its SaveName as its key.
     * If a Game with the same SaveName already exists, it will
     * be overwritten.
     * @param g Game - The Game to be saved.
     * @return boolean - True if successful, false if g is null.
     */
    public boolean addGame(Game g) {
        if(g == null) {
            return false;
        }
        else {
            gameNameToString(g);
            gameList.put(g.getSaveName(), g);
            return true;
        }
    }


    /**
     * Generates a unique saveName for a Game that contains the names of
     * its players. If a Game between those players already exists, it adds
     * extra characters onto the end to make sure each Game has a unique
     * saveName to be used as its key in gameList.
     * @param game Game - The Game the savename will be associated with.
     */
    private void gameNameToString(Game game) {
        StringBuilder gameNamer = new StringBuilder();
        if(game.getUsers().size() > 0) {
            for (int i = 0; i < game.getUsers().size(); i++) {
                gameNamer.append(game.getUsers().get(i));
                gameNamer.append("_");
            }
            gameNamer.delete(gameNamer.length() - 1, gameNamer.length() - 1);
        }
        else gameNamer.append("genericGameName");
        /* count the number of games that already exist between the two players */
        int count = 0;
        String sName = gameNamer.toString();
        for(String key : gameList.keySet()) {
            if(key.contains(sName)) {
                count++;
            }
        }
        if(count > 0) {
            gameNamer.append(count);
        }
        sName = gameNamer.toString();
        game.setSaveName(sName);
    }

    /**
     * Returns an ArrayList of completed Games. Does not list private games that the
     * player is not part of, as they are not visible to the player.
     * @param username String - The username of the player requesting the
     *                 list of completed Games.
     * @return ArrayList<Game> - All Games that are completed.
     */
    public ArrayList<Game> getCompletedGames( String username) {
        ArrayList<Game> completedGames = new ArrayList<Game>();
        Game g;
        for(String key : gameList.keySet()) {
            g = gameList.get(key);
            if(g.isCompleted() && (g.getUsers().contains(username) || g.getPublicVisibility())) {
                completedGames.add(g);
            }
        }
        return completedGames;
    }

    /**
     * Returns an ArrayList of Games where the player can join as a spectator.
     * The games must be publicly visible, and the player can't be one of the
     * two users in the game.
     * @param username String - The username of the player making the request.
     * @return
     */
    public ArrayList<Game> getObservableGames(String username) {
        ArrayList<Game> observableGames = new ArrayList<Game>();
        Game g;
        for(String key : gameList.keySet()) {
            g = gameList.get(key);
            if(!g.getUsers().contains(username) && g.getPublicVisibility()) {
                observableGames.add(g);
            }
        }
        return observableGames;
    }

    /**
     * Returns a list of Games that are currently in-progress, and have not been completed.
     * Only lists games the user is playing, as observable games are returned in a separate method.
     * @param username String - The username of the player requesting the
      *                 list of in-progress Games.
     * @return ArrayList<Game> - A list of Games that have not yet been completed.
     */
    public ArrayList<Game> getGamesInProgress(String username) {
        ArrayList<Game> inProgressGames = new ArrayList<Game>();
        Game g;
        for(String key : gameList.keySet()) {
            g = gameList.get(key);
            if(!g.isCompleted() && (g.getUsers().contains(username) )) {
                inProgressGames.add(g);
            }
        }
        return inProgressGames;
    }

    public HashMap<String, Game> getGameList() {
        return gameList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nGames: ");
        for(String key : gameList.keySet()) {
            Game g = gameList.get(key);
            sb.append("\n\t" + g.toString());
        }
        return sb.toString();
    }

}

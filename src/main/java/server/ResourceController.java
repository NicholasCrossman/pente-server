package server;

import game.Game;
import game.Move;
import game.rules.Rules;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceController {

    private SavedGames games;
    private UserList users;
    private static ResourceController instance;

    private ResourceController(){
        games = new SavedGames();
        users = new UserList();
    }

    public static synchronized ResourceController getController() {
        if(instance == null) {
            instance = new ResourceController();
        }
        return instance;
    }

    public String getLeaderBoard(){ return users.generateLeaderBoard(); }

    public User login(String username, String password){
        //needs to be altered to check password and return a 401

        return users.authenticateUser(username, password);
    }

    public User getUser(String userName){
        return users.getUserByName(userName);
    }

    public boolean newUser(String userName, String password){
        return users.createUser(userName, password) != null;
    }

    public String getInProgress(String userName){
        ArrayList<Game> progress = games.getGamesInProgress(userName);
        String output = "";
        for (int i = 0; i < progress.size(); i++){
            Game g = progress.get(i);
            output += g.getSaveName() + "\t";
            output += g.getRule().getClass().getName() + "\t";
            output += g.getLastMoveDate() + "\n";
        }
        return output;
    }

    public String getObservableGames(String userName) {
        ArrayList<Game> complete = games.getObservableGames(userName);
        String output = "";
        for (int i = 0; i < complete.size(); i++){
            Game g = complete.get(i);
            output += g.getSaveName() + "\t";
            output += g.getRule().getClass().getName() + "\t";
            output += g.getLastMoveDate() + "\n";
        }
        return output;
    }

    public String getCompleted(String userName){
        ArrayList<Game> complete = games.getCompletedGames(userName);
        String output = "";
        for (int i = 0; i < complete.size(); i++){
            Game g = complete.get(i);
            output += g.getSaveName() + "\t";
            output += g.getRule().getClass().getName() + "\t";
            output += g.getLastMoveDate() + "\n";
        }
        return output;
    }

    //Game to make
    //then call updateGame
    public String createGame(String userName1,String userName2, Rules rule, boolean visibility){
        ArrayList<String> u= new ArrayList<>();
        u.add(userName1);
        u.add(userName2);
        Game g= new Game(visibility,rule,u);
        games.addGame(g);
        return g.getSaveName();
    }


    public Game getGameState(String userName, String gameId){
        Game output = games.loadGame(gameId);

//        if (output == null || !output.getPublicVisibility() || !output.getUsers().contains(userName)){
//            return null;
//        }

        //need to be added
        return output;
    }

    //@return 0 on success, 1 on success with user update, -1 on 404, -2 on 401, -3 on 400
    public int updateGame(String userName, String gameId, Move fromClient){
        Game current = games.loadGame(gameId);
        if (current == null){
            return -1;
        }
        if (current.isCompleted()
                || fromClient.getPlayer() != current.getTurn()
                ||!current.getUser(fromClient.getPlayer()).equals(userName)){
            return -2;
        }
        if (!current.makeMove(fromClient.getPlayer(), fromClient.getLocationX(), fromClient.getLocationY())){
            return -3;
        }
        if (current.isCompleted()){
            if (fromClient.getPlayer() == 0){
                users.getUserByName(current.getUser(0)).getRecord().increaseWins();
                users.getUserByName(current.getUser(1)).getRecord().increaseLosses();
            } else {
                users.getUserByName(current.getUser(1)).getRecord().increaseWins();
                users.getUserByName(current.getUser(0)).getRecord().increaseLosses();
            }
            return 1;
        }
        return 0;
    }
}

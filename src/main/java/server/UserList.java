package server;

import user.User;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class UserList {
    private ArrayList<User> userList;

    /**
     * Constructor used to create a new UserList. Initializes an empty ArrayList<User>.
     */
    public UserList(){
        userList=new ArrayList<User>();
        //create default users
        createUser("Player1", "password");
        createUser("Player2", "password");
        createUser("Nicholas", "nicholas");
        createUser("Daniel", "daniel");
        createUser("Jim", "jim");
        createUser("Devin", "devin");
        createUser("Chris", "chris");
    }

    /**
     * Adds a User object to userList, making sure the name is unique.
     * Returns false if the userName already exists.
     * @param u User - The User to be added.
     * @return Boolean - True if the operation succeeds, false otherwise.
     */
    public boolean addUser(User u) {
        if (u != null) {
            for(User i : userList){
                if (i.getUserName().equals(u.getUserName())) {
                    System.err.println("User with the same name already exists.");
                    return false;
                }
            }
            userList.add(u);
            return true;
        }
        return false;
    }

    /**
     * Used to authenticate a User. Searches the userList for a User with
     * the specified name and checks if the input password is correct. Returns the
     * User if successful, null if the User doesn't exist or the password is incorrect.
     * @param username String - The username of the User logging in.
     * @param password String - The input password of the User logging in.
     * @return User - Returns the corresponding User if successful, null otherwise.
     */
    public User authenticateUser(String username, String password) {
        User r = getUserByName(username);
        if (r == null) { // User does not exist
            return null;
        }
        boolean passwordCorrect = r.authenticatePassword(password);
        if (passwordCorrect) {
            return r;
        }
        else
            return null; //incorrect password entered
    }

    /**
     * Created a new User after checking to make sure the user's name is
     * unique. Returns null if the User already exists.
     * @param username String - The new User's username.
     * @param password String - The new User's password.
     * @return User - Returns the new User object on success, null otherwise.
     */
    public User createUser(String username, String password) {
        User r = null;
        for (User u : userList) {
            if(u.getUserName().equals(username)) {
                return null; // User with that name already exists
            }
        }
        r = new User(username, password);
        addUser(r);
        return r;
    }

    /**
     * Returns a User object by name.
     * @param userName String - The userName to be matched.
     * @return User - The User object with the specified userName.
     */
    public User getUserByName(String userName) {
        User ret = null;
        if(userList != null) {
            for (User u : userList) {
                if (u.getUserName().equals(userName)) {
                    ret = u;
                }
            }
        }
        return ret;
    }

    public String generateLeaderBoard() {
        PriorityQueue<User> sorted = new PriorityQueue<User>();
        String leaderBoard = "";
        for(int i = 0; i < userList.size(); i++){
            sorted.add(userList.get(i));
        }

        int leaderBoardSize = 20;
        if(sorted.size() < 20) {
            leaderBoardSize = sorted.size();
        }
        for(int i = 0; i < leaderBoardSize; i++){
            User tempUser = sorted.poll();
            leaderBoard += tempUser.getUserName() + "\t|\t" + tempUser.getRecord().getWins() + "\t|\t" +
                    tempUser.getRecord().getLosses() + "\t|\t" + tempUser.getRecord().getTies()+"\n";
        }
        return leaderBoard;
    }

    @Override
    public String toString() {
        String s = "";
        for(User u : userList) {
            s += u.toString();
        }
        return s;
    }

}

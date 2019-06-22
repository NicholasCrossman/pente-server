package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Comparable<User>{
    private String userName;
    private byte[] password;// put through the SHA-512 hash function
    private Record record;

    /**
     * The constructor for creating a new User object.
     * @param userName The user's name, stored as a string.
     * @param password The user's password, stored as a byte array
     *                 after being put through the SHA-512 hash function
     *                 for security.
     */
    public User(String userName, String password){
        this.userName = userName;
        this.password = hashPassword(password); // private hash function used
        record = new Record();
    }

    /**
     * Constructor used to reconstitute an object from saved data.
     * @param uName String - The userName.
     * @param bytePassword byte[] - The user's hashed password.
     * @param r Record - The User's Record of wins, losses, and ties.
     */
    public User(String uName, byte [] bytePassword, Record r) {
        this.userName = uName;
        this.password = bytePassword;
        this.record = r;
    }

    /**
     * A private helper function to hash passwords for authentication.
     * @param password The user's input password as a string.
     * @return A byte array containing the hashed password.
     */
    private byte[] hashPassword(String password) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = new byte[0]; // the hashed password as a byte array
        if (md != null) {
            hashedPassword = md.digest(password.getBytes());
        }
        return hashedPassword;
    }

    public String getUserName() {
        return userName;
    }

    public byte[] getPassword() {
        return password;
    }

    /**
     * Takes an input password string and hashes it. Then compares it to the stored
     * password for the user's account.
     * @param inputPassword The password input to be compared against the stored value.
     * @return True if the passwords match, false otherwise.
     */
    public boolean authenticatePassword(String inputPassword) {
        byte[] input = hashPassword(inputPassword);
        return java.util.Arrays.equals(password, input);
    }

    /**
     * Sets the stored password to the hashed value of the input string.
     * @param inputPassword The string to be hashed into a password.
     * @return True if the operation succeeds, false otherwise.
     */
    public Boolean setPassword(String inputPassword) {
        byte[] input = hashPassword(inputPassword);
        this.password = input;
        return true;
    }

    public Record getRecord() {
        return record;
    }

    /**
     * Compares Users based on their over all win loss ratio.
     * @param otherUser The user being compared against
     * @return -1 if greater, 0 if equals, 1 if less than.
     */
    @Override
    public int compareTo(User otherUser){
        if(record.getWins() > otherUser.getRecord().getWins()) return -1;
        if(record.getWins() < otherUser.getRecord().getWins()) return 1;
        return 0;
    }

    /**
     *
     * @param other to check if it is equal
     * @return true if userName matches, false otherwise
     */
    public boolean equals(User other){
        return this.getUserName().equals(other.getUserName());
    }

    @Override
    public String toString() {
        return "\nUserName: " + userName + "\n" + getRecord().toString();
    }
}

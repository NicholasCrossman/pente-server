package server.inputs;

import game.rules.Pente;
import game.rules.Rules;

public class CreateGame {
    public String user2;
    public boolean publicVisibility;
    public CreateGame(String user2, boolean publicVisibility){
        this.user2=user2;
        this.publicVisibility = publicVisibility;
    }
}

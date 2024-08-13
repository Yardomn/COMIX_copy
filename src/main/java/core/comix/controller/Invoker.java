package core.comix.controller;

import java.io.IOException;

public abstract class Invoker {

    public Invoker() {}
    
    public abstract void executeCommand(String input) throws CommandInputException, IOException;

}

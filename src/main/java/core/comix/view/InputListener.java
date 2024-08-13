package core.comix.view;
import core.comix.controller.CommandInputException;
import core.comix.controller.Invoker;
import java.util.Scanner;
import java.util.HashMap;

public class InputListener {
    private Scanner scanner;
    private HashMap<String, Invoker> invokerMap;

    public InputListener(PTUI ui){
        this.scanner = new Scanner(System.in);
        this.invokerMap = new HashMap<>();
    }

    public void addMapping(String key, Invoker value){
        invokerMap.put(key, value);
    }

    public void getInputLoop() {
        boolean isListening = true;

        while (isListening) {
            // Get input command from user
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            // Split input on space
            String[] inputSplit = input.split(" ");

            // If command is quit, stop listening. No matter what, execute command
            if (inputSplit[0].toLowerCase().equals("quit")) {
                isListening = false;
            }
            executeCommand(input);
        }
        scanner.close();
    }

    private void executeCommand(String input) {
        // Split input on space
        String[] inputSplit = input.split(" ");

        // Input without command name
        String inputParams = "";
        if (inputSplit.length > 1) {
            inputParams = input.substring(inputSplit[0].length() + 1);
        }

        // Find the invoker that matches the command
        Invoker commandInvoker = invokerMap.get(inputSplit[0].toLowerCase());

        // Execute the command. If it doesn't exist in the map, throw error
        try {
            if (commandInvoker != null) {
                commandInvoker.executeCommand(inputParams);
            } else {
                throw new CommandInputException("Command not recognized.");
            }
        } catch (CommandInputException e) {
            System.out.println(e.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

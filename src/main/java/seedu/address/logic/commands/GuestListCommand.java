package seedu.address.logic.commands;

import seedu.address.model.Model;

public class GuestListCommand extends Command {
    public static String COMMAND_WORD = "list";
    public GuestListCommand() {

    }
    public CommandResult execute(Model model) {
        return new CommandResult("template message first");
    }
}

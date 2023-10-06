package seedu.address.logic.commands;

import seedu.address.model.Model;

public class VendorListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public VendorListCommand() {

    }
    public CommandResult execute(Model model) {
        return new CommandResult("template message first");
    }
}

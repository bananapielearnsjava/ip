package banana.command;

import banana.utils.Storage;
import banana.utils.TaskList;
import banana.utils.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) {
        Ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

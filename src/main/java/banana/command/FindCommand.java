package banana.command;

import java.io.IOException;

import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;

/**
 * Represents a command to find tasks on a specific date.
 */
public class FindCommand extends Command {
    private final String dateStr;

    public FindCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        tasks.findTasksOnDate(dateStr);
    }
}

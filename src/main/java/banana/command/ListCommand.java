package banana.command;

import java.io.IOException;

import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        tasks.listTasks();
    }
}

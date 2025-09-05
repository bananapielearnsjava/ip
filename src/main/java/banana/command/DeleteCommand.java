package banana.command;

import java.io.IOException;

import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        tasks.deleteTask(index, storage);
    }
}

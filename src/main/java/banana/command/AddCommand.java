package banana.command;

import java.io.IOException;

import banana.exceptions.BananaException;
import banana.task.Task;
import banana.utils.Storage;
import banana.utils.TaskList;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        tasks.addTask(task, storage);
    }
}

package banana.command;

import java.io.IOException;

import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;
import banana.task.Task;

/**
 * Represents a command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        Task task = tasks.getTask(index);
        if (task.isDone()) {
            tasks.unmarkTask(index);
            storage.save(tasks);
            return "Ok! I've unmarked this task as not done:\n  " + task;
        } else {
            return "This task is already unmarked as not done!";
        }
    }
}

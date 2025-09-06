package banana.command;

import java.io.IOException;

import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;
import banana.task.Task;

/**
 * Represents a command to mark a task as completed in the task list.
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        Task task = tasks.getTask(index);
        if (!task.isDone()) {
            tasks.markTask(index);
            storage.save(tasks);
            return "Nice! I've marked this task as done:\n  " + task;
        } else {
            return "This task is already marked as done!";
        }
    }
}

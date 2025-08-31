package banana;

import java.io.IOException;

/**
 * Represents a command to mark a task as completed in the task list.
 */
public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        tasks.markTask(index, ui, storage);
    }
}

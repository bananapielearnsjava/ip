package banana;

import java.io.IOException;

/**
 * Represents command that can be executed by the BananaBot.
 */
public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException;
    public boolean isExit() {
        return false;
    }
}

package banana.command;


import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;
import banana.utils.Ui;

/**
 * Represents a command to find tasks containing a specific keyword in the task list.
 */
public class FindKeywordCommand extends Command {
    private final String keyword;

    public FindKeywordCommand(String keyword, Ui ui) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws BananaException {
        tasks.findTasks(keyword);
    }
}

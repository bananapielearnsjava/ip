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
    public String execute(TaskList tasks, Storage storage) throws BananaException {
        TaskList found = tasks.findTasks(keyword);
        if (found.size() == 0) {
            return "No tasks found containing: " + keyword;
        }
        return "Here are the matching tasks:\n" + found.listTasks();
    }
}

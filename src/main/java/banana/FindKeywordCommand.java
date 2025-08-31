package banana;


/**
 * Represents a command to find tasks containing a specific keyword in the task list.
 */
public class FindKeywordCommand extends Command {
    private final String keyword;

    public FindKeywordCommand(String keyword, Ui ui) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.findTasks(keyword, ui);
    }
}

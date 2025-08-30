import java.io.IOException;

public class FindCommand extends Command {
    private final String dateStr;

    public FindCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        tasks.findTasksOnDate(dateStr, ui);
    }
}
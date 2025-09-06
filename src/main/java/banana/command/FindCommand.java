package banana.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import banana.exceptions.BananaException;
import banana.utils.Storage;
import banana.utils.TaskList;

/**
 * Represents a command to find tasks on a specific date.
 */
public class FindCommand extends Command {
    private final String dateStr;

    public FindCommand(String dateStr) {
        this.dateStr = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE).toString();
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws BananaException, IOException {
        TaskList found = tasks.findTasksOnDate(dateStr);
        if (found.size() == 0) {
            return "No tasks found on " + dateStr + ".";
        }
        return "Tasks on " + dateStr + ":\n" + found.listTasks();
    }
}

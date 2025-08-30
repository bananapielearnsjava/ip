import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

/**
 * Parses user input and executes corresponding commands.
 */

public class Parser {
    public void parse(String input, TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (input.equalsIgnoreCase("list")) {
            tasks.listTasks(ui);
        } else if (input.toLowerCase().startsWith("mark ")) {
            int taskNumber = Integer.parseInt(input.substring(5).trim()) - 1;
            tasks.markTask(taskNumber, ui, storage);
        } else if (input.toLowerCase().startsWith("unmark ")) {
            int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
            tasks.unmarkTask(taskNumber, ui, storage);
        } else if (input.toLowerCase().startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            tasks.addTask(new ToDo(description), ui, storage);
        } else if (input.toLowerCase().startsWith("deadline ")) {
            String[] parts = input.substring(9).split("/by", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new DukeException("Invalid deadline format! Use: deadline <description> /by <date>");
            }
            String description = parts[0].trim();
            String by = parts[1].trim();
            tasks.addTask(new Deadline(description, by), ui, storage);
        } else if (input.toLowerCase().startsWith("event ")) {
            String[] parts = input.substring(6).split("/from", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new DukeException("Invalid event format! Use: event <description> /from <start> /to <end>");
            }
            String description = parts[0].trim();
            String[] timeParts = parts[1].trim().split("/to");
            if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                throw new DukeException("Missing time details.");
            }
            String from = timeParts[0].trim();
            String to = timeParts[1].trim();
            tasks.addTask(new Event(description, from, to), ui, storage);
        } else if (input.toLowerCase().startsWith("delete ")) {
            int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
            tasks.deleteTask(taskNumber, ui, storage);
        } else if (input.toLowerCase().startsWith("on ")) {
            String dateStr = input.substring(3).trim();
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            tasks.findTasksOnDate(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")), ui);
        } else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
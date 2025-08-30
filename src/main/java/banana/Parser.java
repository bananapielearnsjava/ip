package banana;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

/**
 * Parses user input and executes corresponding commands.
 */

public class Parser {
    public static Command parse(String input) throws DukeException, IOException {
        if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (input.toLowerCase().startsWith("mark ")) {
            int taskNumber = Integer.parseInt(input.substring(5).trim()) - 1;
            return new MarkCommand(taskNumber);
        } else if (input.toLowerCase().startsWith("unmark ")) {
            int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
            return new UnmarkCommand(taskNumber);
        } else if (input.toLowerCase().startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            return new AddCommand(new ToDo(description));
        } else if (input.toLowerCase().startsWith("deadline ")) {
            String[] parts = input.substring(9).split("/by", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new DukeException("Invalid deadline format! Use: deadline <description> /by <date>");
            }
            String description = parts[0].trim();
            String by = parts[1].trim();
            return new AddCommand(new Deadline(description, by));
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
            return new AddCommand(new Event(description, from, to));
        } else if (input.toLowerCase().startsWith("delete ")) {
            int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
            return new DeleteCommand(taskNumber);
        } else if (input.toLowerCase().startsWith("on ")) {
            String dateStr = input.substring(3).trim();
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return new FindCommand(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
        } else if (input.equalsIgnoreCase("bye")) {
                return new ExitCommand();
        } else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
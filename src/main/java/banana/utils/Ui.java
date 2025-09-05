package banana.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import banana.task.Deadline;
import banana.task.Event;
import banana.task.Task;

/**
 * Responsible for all output to user.
 */
public class Ui {
    @SuppressWarnings({"checkstyle:OperatorWrap", "CheckStyle"})
    private static final String LOGO =
            "                  __,__\n" +
                    "        .--.  .-\"     \"-.  .--.\n" +
                    "       / .. \\/  .-. .-.  \\/ .. \\\n" +
                    "      | |  '|  /   Y   \\  |'  | |\n" +
                    "      | \\   \\  \\ 0 | 0 /  /   / |\n" +
                    "       \\ '- ,\\.-\"`` ``\"-./, -' /\n" +
                    "        `'-' /_   ^ ^   _\\ '-'`\n" +
                    "        .--'|  \\._ _ _./  |'--.\n" +
                    "      /`    \\   \\.-.  /   /    `\\\n" +
                    "     /       '._/  |-' _.'       \\\n" +
                    "    /          ;  /--~'   |       \\\n" +
                    "   /        .'\\|.-\\--.     \\       \\\n" +
                    "  /   .'-. /.-.;\\  |\\|'~'-.|\\       \\\n" +
                    "  \\       `-./`|_\\_/ `     `\\'.      \\\n" +
                    "   '.      ;     ___)        '.`;    /\n" +
                    "     '-.,_ ;     ___)          \\/   /\n" +
                    "      \\   ``'------'\\       \\   `  /\n" +
                    "       '.    \\       '.      |   ;/_\n" +
                    "...  ___>     '.       \\_ _ _/   ,  '--.\n" +
                    "   .'   '.   .-~~~~~-. /     |--'`~~-.  \\\n" +
                    "  // / .---'/  .-~~-._/ / / /---..__.'  /\n" +
                    " ((_(_/    /  /      (_(_(_(---.__    .'\n" +
                    "           | |     _              `~~`\n" +
                    "           | |     \\'.\n" +
                    "            \\ '....' |\n" +
                    "             '.,___.'";

    private Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("Hello I'm BananaBot\n" + LOGO + "\nWhat can I do for you?");
    }

    public static void showGoodbye() {
        System.out.println("\nBye. Hope to see you again soon!");
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }

    /**
     * Displays an error message to the user.
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println("Dude! " + message);
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays the list of tasks to the user.
     * @param tasks The list of tasks to display.
     */
    public static void showTaskList(List<Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i).toString());
        }
        System.out.println("You have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task has been added.
     * @param task The task that was added.
     * @param size The new size of the task list.
     */
    public static void showTaskAdded(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task has been deleted.
     * @param task The task that was deleted.
     * @param size The new size of the task list.
     */
    public static void showTaskDeleted(Task task, int size) {
        System.out.println("____________________________________________________________");
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     * @param task The task that was marked as done.
     */
    public static void showTaskMarked(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  [X] " + task.getDescription());
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task has been unmarked as not done.
     * @param task The task that was unmarked.
     */
    public static void showTaskUnmarked(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  [ ] " + task.getDescription());
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task is already marked as done.
     */
    public static void showAlreadyMarked() {
        System.out.println("____________________________________________________________");
        System.out.println("This task is already marked as done!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message indicating that a task is already unmarked as not done.
     */
    public static void showAlreadyUnmarked() {
        System.out.println("____________________________________________________________");
        System.out.println("This task is already not done!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays tasks that are scheduled on a specific date.
     * @param dateStr The date to check for tasks (formatted as "MMM dd yyyy").
     * @param tasks The list of tasks to check.
     */
    public static void showTasksOnDate(String dateStr, List<Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Tasks on " + dateStr + ":");
        boolean found = false;
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline d = (Deadline) task;
                if (d.getBy().toLocalDate().equals(LocalDate.parse(dateStr,
                        DateTimeFormatter.ofPattern("MMM dd yyyy")))) {
                    System.out.println("  " + d);
                    found = true;
                }
            } else if (task instanceof Event) {
                Event e = (Event) task;
                if (e.getFrom().toLocalDate().equals(LocalDate.parse(
                        dateStr, DateTimeFormatter.ofPattern("MMM dd yyyy")))
                        ||
                        e.getTo().toLocalDate().equals(LocalDate.parse(
                                dateStr, DateTimeFormatter.ofPattern("MMM dd yyyy")))) {
                    System.out.println("  " + e);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("\nNo task on this date.");
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays tasks that contain a specific keyword in their description.
     * @param keyword The keyword to search for.
     * @param tasks The list of tasks to search.
     */
    public static void showFoundTasks(String keyword, List<Task> tasks) {
        System.out.println("____________________________________________________________");
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tasks found containing the keyword: " + keyword);
        } else {
            boolean foundMatch = false;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    if (!foundMatch) {
                        System.out.println("Here are the matching tasks in your list:");
                        foundMatch = true;
                    }
                    System.out.println((i + 1) + "." + tasks.get(i).toString());
                }
            }
            if (!foundMatch) {
                System.out.println("No tasks found containing the keyword: " + keyword);
            }
        }
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BananaBot {
    private static final String STORAGE_DIR = "./data";
    private static final String FILE_PATH = STORAGE_DIR + "/bot.txt";
    private static List<Task> toDoList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        createStorageIfNotExists(); // Ensure data directory exists
        loadTasks();

        String logo =
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
                "jgs  ___>     '.       \\_ _ _/   ,  '--.\n" +
                "   .'   '.   .-~~~~~-. /     |--'`~~-.  \\\n" +
                "  // / .---'/  .-~~-._/ / / /---..__.'  /\n" +
                " ((_(_/    /  /      (_(_(_(---.__    .'\n" +
                "           | |     _              `~~`\n" +
                "           | |     \\'.\n" +
                "            \\ '....' |\n" +
                "             '.,___.'";


        System.out.println("Hello I'm BananaBot\n" + logo + "\nWhat can I do for you?" );
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("list")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < toDoList.size(); i++) {
                        System.out.println((i + 1) + "." + toDoList.get(i).toString());
                    }
                    System.out.println("You have " + toDoList.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } else if (input.toLowerCase().startsWith("mark ")) {
                    int taskNumber = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (taskNumber >= 0 && taskNumber < toDoList.size()) {
                        Task task = toDoList.get(taskNumber);
                        if (!task.isDone()) {
                            task.markAsDone();
                            saveTasks();
                            System.out.println("____________________________________________________________");
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("  [X] " + task.getDescription());
                            System.out.println("____________________________________________________________");
                        } else {
                            System.out.println("____________________________________________________________");
                            System.out.println("This task is already marked as done!");
                            System.out.println("____________________________________________________________");
                        }
                    } else {
                        throw new DukeException("Invalid task number!");
                    }

                } else if (input.toLowerCase().startsWith("unmark ")) {
                    int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (taskNumber >= 0 && taskNumber < toDoList.size()) {
                        Task task = toDoList.get(taskNumber);
                        if (task.isDone()) {
                            task.markAsDone();
                            saveTasks();
                            System.out.println("____________________________________________________________");
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println("  [ ] " + task.getDescription());
                            System.out.println("____________________________________________________________");
                        } else {
                            System.out.println("____________________________________________________________");
                            System.out.println("This task is already not done!");
                            System.out.println("____________________________________________________________");
                        }
                    } else {
                        throw new DukeException("Invalid task number!");
                    }

                } else if (input.toLowerCase().startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    } else {
                        Task task = new ToDo(description);
                        toDoList.add(task);
                        saveTasks();
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + task);
                        System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }

                } else if (input.toLowerCase().startsWith("deadline ")) {
                    String[] parts = input.substring(9).split("/by", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new DukeException("Invalid deadline format! Use: deadline <description> /by <date>");
                    } else {
                        String description = parts[0].trim();
                        String by = parts[1].trim();
                        Task deadline = new Deadline(description, by);
                        toDoList.add(deadline);
                        saveTasks();
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this deadline:");
                        System.out.println("  " + deadline);
                        System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }

                } else if (input.toLowerCase().startsWith("event ")) {
                    String[] parts = input.substring(6).split("/from", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new DukeException("Invalid event format! Use: event <description> /from <start> /to <end>");
                    } else {
                        String description = parts[0].trim();
                        String[] timeParts = parts[1].trim().split("/to");
                        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                            throw new DukeException("Missing time details.");
                        }
                        String from = timeParts[0].trim();
                        String to = timeParts[1].trim();
                        Task event = new Event(description, from, to);
                        toDoList.add(event);
                        saveTasks();
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this event:");
                        System.out.println("  " + event);
                        System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }

                } else if (input.toLowerCase().startsWith("delete ")) {
                    int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (taskNumber >= 0 && taskNumber < toDoList.size()) {
                        Task removedTask = toDoList.remove(taskNumber);
                        System.out.println("____________________________________________________________");
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removedTask);
                        System.out.println(" Now you have " + toDoList.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } else {
                        throw new DukeException("Invalid task number!");
                    }

                } else if (input.toLowerCase().startsWith("on ")) {
                    String dateStr = input.substring(3).trim();
                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.println("Tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
                    boolean found = false;

                    for (Task task : toDoList) {
                        if (task instanceof Deadline) {
                            Deadline d = (Deadline) task;
                            if (d.by.toLocalDate().equals(date)) {
                                System.out.println("  " + d);
                                found = true;
                            }
                        } else if (task instanceof Event) {
                            Event e = (Event) task;
                            if (e.from.toLocalDate().equals(date) || e.to.toLocalDate().equals(date)) {
                                System.out.println("  " + e);
                                found = true;
                            }
                        }
                    }

                    if (!found) {
                        System.out.println("\nNo task on this date.");
                    }

                } else if (input.equalsIgnoreCase("bye")) {
                    System.out.println("\nBye. Hope to see you again soon!");
                    break;

                } else {
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }

            } catch (DukeException | IOException e) {
                System.out.println("____________________________________________________________");
                System.out.println("Dude! " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }
    private static void createStorageIfNotExists() {
        File directory = new File(STORAGE_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory and any necessary parent directories
        }
    }
    private static void saveTasks() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : toDoList) {
                String line = "";
                if (task instanceof ToDo) {
                    line = "T | " + (task.isDone() ? 1 : 0) + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    // save in raw format
                    line = "D | " + (task.isDone() ? 1 : 0) + " | " + task.getDescription()
                            + " | " + deadline.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    // save in raw format
                    line = "E | " + (task.isDone() ? 1 : 0) + " | " + task.getDescription()
                            + " | " + event.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                            + " - " + event.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                }
                writer.println(line);
            }
        }
    }


    private static void loadTasks() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // No file to load
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                Task task = null;
                switch (type) {
                    case "T":
                        task = new ToDo(description);
                        break;
                    case "D":
                        String by = parts[3];
                        task = new Deadline(description, by);
                        break;
                    case "E":
                        String[] timeParts = parts[3].split(" - ");
                        String from = timeParts[0];
                        String to = timeParts[1];
                        task = new Event(description, from, to);
                        break;
                }
                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    toDoList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
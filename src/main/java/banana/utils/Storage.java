package banana.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import banana.task.Deadline;
import banana.task.Event;
import banana.task.Task;
import banana.task.ToDo;

/**
 * Manages the storage of tasks to and from a file.
 */
public class Storage {
    private static final String STORAGE_DIR = "data";
    private final String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        createStorageIfNotExists();
    }

    private void createStorageIfNotExists() {
        File directory = new File(STORAGE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     * @throws IOException If there is an error reading the file.
     */
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
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
                default:
                    System.out.println("Unknown task type in storage: " + type);
                }
                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If there is an error writing to the file.
     */
    public void save(List<Task> tasks) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                String line = "";
                if (task instanceof ToDo) {
                    line = "T | " + (task.isDone() ? 1 : 0) + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    line = "D | " + (task.isDone() ? 1 : 0) + " | " + task.getDescription()
                            + " | " + deadline.getBy().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    line = "E | " + (task.isDone() ? 1 : 0) + " | " + task.getDescription()
                            + " | " + event.getFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                            + " - " + event.getTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                }
                writer.println(line);
            }
        }
    }
}

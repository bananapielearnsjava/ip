package banana.utils;

import java.util.ArrayList;

import banana.exceptions.BananaException;
import banana.task.Task;

/**
 * Manages a list of tasks and provides methods to manipulate them.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    /**
     * Adds a new task to the task list.
     *
     * @param task    The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index The index of the task to delete (0-based).
     * @return
     * @throws BananaException If the index is invalid.
     */
    public Task deleteTask(int index) throws BananaException {
        if (index >= 0 && index < tasks.size()) {
            return tasks.remove(index);
        } else {
            throw new BananaException("Invalid task number!");
        }
    }
    /**
     * Lists all tasks in the task list.
     *
     * @return A string representation of all tasks.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index   The index of the task to mark as done (0-based).
     * @throws BananaException If the index is invalid.
     */
    public void markTask(int index) throws BananaException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (!task.isDone()) {
                task.markAsDone();
            }
        } else {
            throw new BananaException("Invalid task number!");
        }
    }

    /**
     * Unmarks the task at the specified index as not done.
     *
     * @param index   The index of the task to unmark (0-based).
     * @throws BananaException If the index is invalid.
     */
    public void unmarkTask(int index) throws BananaException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (task.isDone()) {
                task.markAsDone();
            }
        } else {
            throw new BananaException("Invalid task number!");
        }
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    /**
     * Finds and returns a list of tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for.
     */
    public TaskList findTasks(String keyword) {
        TaskList foundtasks = new TaskList();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                foundtasks.addTask(task);
            }
        }
        return foundtasks;
    }
    /**
     * Finds and returns a list of tasks that occur on the specified date.
     *
     * @param dateStr The date to search for in yyyy-mm-dd format.
     */
    public TaskList findTasksOnDate(String dateStr) {
        TaskList foundtasks = new TaskList();
        for (Task task : tasks) {
            if (task instanceof banana.task.Deadline) {
                banana.task.Deadline deadline = (banana.task.Deadline) task;
                if (deadline.getBy().toLocalDate().toString().equals(dateStr)) {
                    foundtasks.addTask(task);
                }
            } else if (task instanceof banana.task.Event) {
                banana.task.Event event = (banana.task.Event) task;
                if (event.getFrom().toLocalDate().toString().equals(dateStr)
                        || event.getTo().toLocalDate().toString().equals(dateStr)) {
                    foundtasks.addTask(task);
                }
            }
        }
        return foundtasks;
    }

    public int size() {
        return tasks.size();
    }
}

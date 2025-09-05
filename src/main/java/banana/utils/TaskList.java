package banana.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import banana.exceptions.BananaException;
import banana.task.Task;

/**
 * Manages a list of tasks and provides methods to manipulate them.
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }
    /**
     * Adds a new task to the task list.
     *
     * @param task    The task to add.
     * @param storage The Storage instance to save changes.
     * @throws IOException If there is an error saving to storage.
     */
    public void addTask(Task task, Storage storage) throws IOException {
        tasks.add(task);
        storage.save(tasks);
        Ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index   The index of the task to delete (0-based).
     * @param storage The Storage instance to save changes.
     * @throws BananaException If the index is invalid.
     * @throws IOException   If there is an error saving to storage.
     */
    public void deleteTask(int index, Storage storage) throws BananaException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task removedTask = tasks.remove(index);
            storage.save(tasks);
            Ui.showTaskDeleted(removedTask, tasks.size());
        } else {
            throw new BananaException("Invalid task number!");
        }
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index   The index of the task to mark as done (0-based).
     * @param storage The Storage instance to save changes.
     * @throws BananaException If the index is invalid.
     * @throws IOException   If there is an error saving to storage.
     */
    public void markTask(int index, Storage storage) throws BananaException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (!task.isDone()) {
                task.markAsDone();
                storage.save(tasks);
                Ui.showTaskMarked(task);
            } else {
                Ui.showAlreadyMarked();
            }
        } else {
            throw new BananaException("Invalid task number!");
        }
    }

    /**
     * Unmarks the task at the specified index as not done.
     *
     * @param index   The index of the task to unmark (0-based).
     * @param storage The Storage instance to save changes.
     * @throws BananaException If the index is invalid.
     * @throws IOException   If there is an error saving to storage.
     */
    public void unmarkTask(int index, Storage storage) throws BananaException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (task.isDone()) {
                task.markAsDone();
                storage.save(tasks);
                Ui.showTaskUnmarked(task);
            } else {
                Ui.showAlreadyUnmarked();
            }
        } else {
            throw new BananaException("Invalid task number!");
        }
    }

    public void listTasks() {
        Ui.showTaskList(tasks);
    }

    public void findTasksOnDate(String dateStr) {
        Ui.showTasksOnDate(dateStr, tasks);
    }

    public List<Task> getTasks() {
        return tasks;
    }
    /**
     * Finds and returns a list of tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for.
     */
    public void findTasks(String keyword) {
        Ui.showFoundTasks(keyword, tasks);
    }
}

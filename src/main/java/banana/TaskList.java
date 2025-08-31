package banana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * @param ui      The UI instance to display messages.
     * @param storage The Storage instance to save changes.
     * @throws IOException If there is an error saving to storage.
     */
    public void addTask(Task task, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index   The index of the task to delete (0-based).
     * @param ui      The UI instance to display messages.
     * @param storage The Storage instance to save changes.
     * @throws DukeException If the index is invalid.
     * @throws IOException   If there is an error saving to storage.
     */
    public void deleteTask(int index, Ui ui, Storage storage) throws DukeException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task removedTask = tasks.remove(index);
            storage.save(tasks);
            ui.showTaskDeleted(removedTask, tasks.size());
        } else {
            throw new DukeException("Invalid task number!");
        }
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index   The index of the task to mark as done (0-based).
     * @param ui      The UI instance to display messages.
     * @param storage The Storage instance to save changes.
     * @throws DukeException If the index is invalid.
     * @throws IOException   If there is an error saving to storage.
     */
    public void markTask(int index, Ui ui, Storage storage) throws DukeException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (!task.isDone()) {
                task.markAsDone();
                storage.save(tasks);
                ui.showTaskMarked(task);
            } else {
                ui.showAlreadyMarked();
            }
        } else {
            throw new DukeException("Invalid task number!");
        }
    }

    /**
     * Unmarks the task at the specified index as not done.
     *
     * @param index   The index of the task to unmark (0-based).
     * @param ui      The UI instance to display messages.
     * @param storage The Storage instance to save changes.
     * @throws DukeException If the index is invalid.
     * @throws IOException   If there is an error saving to storage.
     */
    public void unmarkTask(int index, Ui ui, Storage storage) throws DukeException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (task.isDone()) {
                task.markAsDone();
                storage.save(tasks);
                ui.showTaskUnmarked(task);
            } else {
                ui.showAlreadyUnmarked();
            }
        } else {
            throw new DukeException("Invalid task number!");
        }
    }

    public void listTasks(Ui ui) {
        ui.showTaskList(tasks);
    }

    public void findTasksOnDate(String dateStr, Ui ui) {
        ui.showTasksOnDate(dateStr, tasks);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}

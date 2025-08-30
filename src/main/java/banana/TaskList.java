package banana;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }
    public void addTask(Task task, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }

    public void deleteTask(int index, Ui ui, Storage storage) throws DukeException, IOException {
        if (index >= 0 && index < tasks.size()) {
            Task removedTask = tasks.remove(index);
            storage.save(tasks);
            ui.showTaskDeleted(removedTask, tasks.size());
        } else {
            throw new DukeException("Invalid task number!");
        }
    }

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
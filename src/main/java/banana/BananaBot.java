package banana;

import java.io.IOException;

/**
 * A personal assistant chatbot that helps users manage their tasks.
 */
public class BananaBot {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Initializes the BananaBot with the specified file path for storage.
     *
     * @param filePath The file path where tasks are stored.
     */
    public BananaBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the BananaBot, processing user commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new BananaBot("./data/bot.txt").run();
    }
}

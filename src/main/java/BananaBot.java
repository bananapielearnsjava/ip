import java.io.IOException;
import java.util.Scanner;

public class BananaBot {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

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

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("bye")) {
                    ui.showGoodbye();
                    break;
                }
                parser.parse(input, tasks, ui, storage);
            } catch (DukeException | IOException e) {
                ui.showError(e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new BananaBot("./data/bot.txt").run();
    }
}
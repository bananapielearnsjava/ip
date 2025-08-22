import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BananaBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> toDoList = new ArrayList<>();

        String logo = "                  __,__\n" +
                "        .--.  .-\"     \"-.  .--.\n" +
                "       / .. \\/  .-. .-.  \\/ .. \\\n" +
                "      | |  '|  /   Y   \\  |'  | |\n" +
                "      | \\   \\  \\ 0 | 0 /  /   / |\n" +
                "       \\ '- ,\\.-\"`` ``\"-./, -' /\n" +
                "        `'-' /_   ^ ^   _\\ '-'`\n" +
                "        .--'|  \\._ _ _./  |'--. \n" +
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
        while(true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < toDoList.size(); i++) {
                    System.out.println((i + 1) + ". " + toDoList.get(i));
                }
            }

            else if (input.equalsIgnoreCase("bye")) {
                System.out.println("\nBye. Hope to see you again soon!");
                break;
            }

            else {
                System.out.println("Added: " + input);
                toDoList.add(input);
            }
        }
    }
}
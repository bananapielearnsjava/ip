import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BananaBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> toDoList = new ArrayList<>();

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
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < toDoList.size(); i++) {
                    System.out.println((i + 1) + "." + toDoList.get(i).toString());
                }
                System.out.println("____________________________________________________________");
            } else if (input.toLowerCase().startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5).trim()) - 1;
                if (taskNumber >= 0 && taskNumber < toDoList.size()) {
                    Task task = toDoList.get(taskNumber);
                    if (!task.isDone()) {
                        task.markAsDone();
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
                    System.out.println("____________________________________________________________");
                    System.out.println("Invalid task number!");
                    System.out.println("____________________________________________________________");
                }

            } else if (input.toLowerCase().startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7).trim()) - 1;
                if (taskNumber >= 0 && taskNumber < toDoList.size()) {
                    Task task = toDoList.get(taskNumber);
                    if (task.isDone()) {
                        task.markAsDone();
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
                    System.out.println("____________________________________________________________");
                    System.out.println("Invalid task number!");
                    System.out.println("____________________________________________________________");
                }

            }else if (input.equalsIgnoreCase("bye")) {
                System.out.println("\nBye. Hope to see you again soon!");
                break;

            }else {
                Task task = new Task(input);
                toDoList.add(task);
                System.out.println("____________________________________________________________");
                System.out.println("Added: " + task);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
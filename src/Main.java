import service.TaskManager;
import storage.TaskJsonStorage;
import storage.TaskPostgresStorage;
import storage.TaskStorage;
import ui.Input;
import ui.TaskPrinter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        TaskJsonStorage storage = new TaskJsonStorage("data/tasks.json");
        TaskStorage storage = new TaskPostgresStorage("jdbc:postgresql://localhost:5432/postgres");
        TaskManager manager = new TaskManager(storage);
        Input input = new Input(new Scanner(System.in));
        TaskPrinter printer = new TaskPrinter();

        System.out.println("To Do App запущено.");

        App app = new App(manager, input, printer);
        app.run();
    }
}

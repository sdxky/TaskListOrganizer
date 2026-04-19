import service.TaskManager;
import storage.TaskJsonStorage;
import storage.TaskPostgresStorage;
import storage.TaskStorage;
import ui.Input;
import ui.TaskPrinter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskStorage storage;
        TaskStorage storageJSON = new TaskJsonStorage("data/tasks.json");
        TaskStorage storagePostgres = new TaskPostgresStorage("jdbc:postgresql://localhost:5432/postgres");
        System.out.println("Connect to " +
                "1) PostgreSQL database or JSON database");
        TaskManager manager = new TaskManager(storage);
        Input input = new Input(new Scanner(System.in));
        TaskPrinter printer = new TaskPrinter();

        System.out.println("To Do App запущено.");

        App app = new App(manager, input, printer);
        app.run();
    }
}


import model.NewState;
import model.Priority;
import model.Status;
import model.Task;
import service.TaskManager;
import ui.Input;
import ui.Menu;
import ui.TaskPrinter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class App {

    private final TaskManager manager;
    private final Input input;
    private final TaskPrinter printer;
    private final Menu menu = new Menu();

    public App(TaskManager manager, Input input, TaskPrinter printer) {
        this.manager = manager;
        this.input = input;
        this.printer = printer;
    }

    public void run() {
        while (true) {
            menu.print();
            int choice = input.readMenuChoice();

            if (choice == 0) {
                System.out.println("Exiting the program. Goodbye.");
                return;
            }

            if (choice == 1) {
                showTasks();
                continue;
            }

            if (choice == 2) {
                addTask();

                continue;
            }

            if (choice == 3) {
                selectTask();
                continue;
            }

            if (choice == 4) {
                sortMenu();
                continue;
            }

            System.out.println("Error: no such menu option.");
        }
    }

    private void showTasks() {
        List<Task> tasks = manager.getAllTasks();
        tasks.sort(Comparator.comparing(Task::getPriority).reversed());
        printer.print(tasks);
    }

    private void addTask() {
        System.out.println();
        System.out.println("Adding a new task.");

        String title = input.readNonEmpty("Enter task title (title): ");
        String description = input.readNonEmpty("Enter task description (description): ");
        Priority priority = input.readPriority();
        LocalDate completionDate = input.readDate("Enter completion date (completionDate) in format dd.MM.yyyy: ");
        LocalDate createdDate = LocalDate.now();

        Task task = new Task(title, description, completionDate, createdDate, priority, Status.NEW);
        task.setState(new NewState());
        manager.addTask(task);

        System.out.println("Task added successfully.");
        showTasks();
    }

    private void selectTask() {
        List<Task> tasks = manager.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("The task list is empty.");
            return;
        }

        printer.print(tasks);

        int index;
        while (true) {
            System.out.print("Enter task number: ");
            index = input.readMenuChoice();
            if (index >= 1 && index <= tasks.size()) break;
            System.out.println("Error: invalid number.");
        }

        Task task = tasks.get(index - 1);
        manageTask(task);
    }

    private void manageTask(Task task) {
        while (true) {
            System.out.println();
            System.out.println("Task: " + task.getTitle());
            System.out.println("Status: " + task.getStatus());

            System.out.println("1) Change status");
            System.out.println("2) Change description");
            System.out.println("3) Delete task");
            System.out.println("0) Back");

            int choice = input.readMenuChoice();

            try {
                if (choice == 1) {
                    changeStatus(task);
                } else if (choice == 2) {
                    String desc = input.readNonEmpty("Enter new description: ");
                    task.changeDescriptionByState(desc);
                    manager.save();
                    System.out.println("Description updated.");
                } else if (choice == 3) {
                    task.deleteByState(manager);
                    System.out.println("Task deleted.");
                    return;
                } else if (choice == 0) {
                    return;
                } else {
                    System.out.println("Invalid menu option.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void changeStatus(Task task) {
        System.out.println("Select new status:");
        System.out.println("1) IN_PROGRESS");
        System.out.println("2) DONE");

        int choice = input.readMenuChoice();

        if (choice == 1) {
            task.moveToInProgress(manager);
            manager.save();
            System.out.println("Status changed to IN_PROGRESS.");
        } else if (choice == 2) {
            task.moveToDone(manager);
            manager.save();
            System.out.println("Status changed to DONE.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void sortMenu() {
        while (true) {
            System.out.println();
            System.out.println("Task sorting:");
            System.out.println("1) By priority");
            System.out.println("2) By creation date");
            System.out.println("3) By description");
            System.out.println("0) Back");

            int choice = input.readMenuChoice();
            List<Task> tasks = manager.getAllTasks();

            if (tasks.isEmpty()) {
                System.out.println("The task list is empty.");
                return;
            }

            if (choice == 1) {
                tasks.sort(Comparator.comparing(Task::getPriority).reversed());
            } else if (choice == 2) {
                tasks.sort(Comparator.comparing(Task::getCreatedDate));
            } else if (choice == 3) {
                tasks.sort(Comparator.comparing(
                        Task::getDescription,
                        Comparator.nullsLast(String::compareTo)
                ));
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid menu option.");
                continue;
            }

            printer.print(tasks);
        }
    }
}


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
                System.out.println("Выход из программы. До свидания.");
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

            System.out.println("Ошибка: такого пункта меню нет.");
        }
    }

    private void showTasks() {
        List<Task> tasks = manager.getAllTasks();
        tasks.sort(Comparator.comparing(Task::getPriority).reversed());
        printer.print(tasks);
    }

    private void addTask() {
        System.out.println();
        System.out.println("Добавление новой задачи.");

        String title = input.readNonEmpty("Введите название задачи (title): ");
        String description = input.readNonEmpty("Введите описание задачи (description): ");
        Priority priority = input.readPriority();
        LocalDate completionDate = input.readDate("Введите дату завершения (completionDate) в формате dd.MM.yyyy: ");
        LocalDate createdDate = LocalDate.now();

        Task task = new Task(title, description, completionDate, createdDate, priority, Status.NEW);
        task.setState(new NewState());
        manager.addTask(task);

        System.out.println("Задача успешно добавлена.");
        showTasks();
    }

    private void selectTask() {
        List<Task> tasks = manager.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            return;
        }

        printer.print(tasks);

        int index;
        while (true) {
            System.out.print("Введите номер задачи: ");
            index = input.readMenuChoice();
            if (index >= 1 && index <= tasks.size()) break;
            System.out.println("Ошибка: неверный номер.");
        }

        Task task = tasks.get(index - 1);
        manageTask(task);
    }

    private void manageTask(Task task) {
        while (true) {
            System.out.println();
            System.out.println("Задача: " + task.getTitle());
            System.out.println("Статус: " + task.getStatus());

            System.out.println("1) Изменить статус");
            System.out.println("2) Изменить описание");
            System.out.println("3) Удалить задачу");
            System.out.println("0) Назад");

            int choice = input.readMenuChoice();

            try {
                if (choice == 1) {
                    changeStatus(task);
                } else if (choice == 2) {
                    String desc = input.readNonEmpty("Введите новое описание: ");
                    task.changeDescriptionByState(desc);
                    manager.save();
                    System.out.println("Описание изменено.");
                } else if (choice == 3) {
                    task.deleteByState(manager);
                    System.out.println("Задача удалена.");
                    return;
                } else if (choice == 0) {
                    return;
                } else {
                    System.out.println("Неверный пункт меню.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void changeStatus(Task task) {
        System.out.println("Выберите новый статус:");
        System.out.println("1) IN_PROGRESS");
        System.out.println("2) DONE");

        int choice = input.readMenuChoice();

        if (choice == 1) {
            task.moveToInProgress(manager);
            manager.save();
            System.out.println("Статус изменён на IN_PROGRESS.");
        } else if (choice == 2) {
            task.moveToDone(manager);
            manager.save();
            System.out.println("Статус изменён на DONE.");
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private void sortMenu() {
        while (true) {
            System.out.println();
            System.out.println("Сортировка задач:");
            System.out.println("1) По приоритету");
            System.out.println("2) По дате создания");
            System.out.println("3) По описанию");
            System.out.println("0) Назад");

            int choice = input.readMenuChoice();
            List<Task> tasks = manager.getAllTasks();

            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст.");
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
                System.out.println("Неверный пункт меню.");
                continue;
            }

            printer.print(tasks);
        }
    }
}

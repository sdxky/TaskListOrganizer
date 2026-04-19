package ui;

import model.Priority;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Input {

    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Input(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Ошибка: значение не может быть пустым. Повторите ввод.");
        }
    }

    public Priority readPriority() {
        while (true) {
            System.out.println("Выберите приоритет задачи:");
            System.out.println("1) низкий");
            System.out.println("2) средний");
            System.out.println("3) высокий");
            System.out.print("Введите 1/2/3 или слово (низкий/средний/высокий): ");

            String raw = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            if (raw.equals("1") || raw.equals("низкий") || raw.equals("low")) return Priority.LOW;
            if (raw.equals("2") || raw.equals("средний") || raw.equals("medium")) return Priority.MEDIUM;
            if (raw.equals("3") || raw.equals("высокий") || raw.equals("high")) return Priority.HIGH;

            System.out.println("Ошибка: неверный приоритет. Повторите ввод.");
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                return LocalDate.parse(raw, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: неверный формат даты. Нужно dd.MM.yyyy (например 25.01.2026). Повторите ввод.");
            }
        }
    }

    public int readMenuChoice() {
        while (true) {
            String raw = scanner.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.print("Ошибка: нужно ввести число. Повторите: ");
            }
        }
    }
}

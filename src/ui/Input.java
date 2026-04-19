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
            System.out.println("Select task priority:");
            System.out.println("1) Low");
            System.out.println("2) Medium");
            System.out.println("3) High");
            System.out.print("Enter 1/2/3 or word (low/medium/high): ");

            String raw = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            if (raw.equals("1") || raw.equals("low")) return Priority.LOW;
            if (raw.equals("2") || raw.equals("medium")) return Priority.MEDIUM;
            if (raw.equals("3") || raw.equals("high")) return Priority.HIGH;

            System.out.println("Error: invalid priority. Please try again.");
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                return LocalDate.parse(raw, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: invalid date format. Use dd.MM.yyyy (e.g., 25.01.2026). Please try again.");            }
        }
    }

    public int readMenuChoice() {
        while (true) {
            String raw = scanner.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.print("Error: you need to enter a number. Try again: ");
            }
        }
    }
}

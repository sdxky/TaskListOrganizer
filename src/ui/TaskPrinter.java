package ui;

import model.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskPrinter {

    public void print(List<Task> tasks) {
        System.out.println();
        if (tasks.isEmpty()) {
            System.out.println("The task list is empty. Add your first task through the menu.");            return;
        }

        System.out.println("Task list:");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-3s %-2s %-20s %-10s %-12s %-12s %-10s %-30s%n",
                "#", "*", "title", "priority", "createdDate", "completionDate", "status", "description");
        System.out.println("---------------------------------------------------------------------------------------------");

        LocalDate today = LocalDate.now();

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            boolean overdue = t.getCompletionDate().isBefore(today) && t.getStatus() != null;
            String mark = overdue ? "*" : "";
            System.out.printf("%-3d %-2s %-20s %-10s %-12s %-12s %-10s %-30s%n",
                    i + 1,
                    mark,
                    safe(t.getTitle(), 20),
                    t.getPriority(),
                    t.getCreatedDate(),
                    t.getCompletionDate(),
                    t.getStatus(),
                    safe(t.getDescription(), 30)
            );
        }

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("* — overdue task (completionDate is earlier than today's date)");
    }

    private String safe(String value, int maxLen) {
        String v = value == null ? "" : value.trim();
        if (v.length() <= maxLen) return v;
        return v.substring(0, maxLen - 3) + "...";
    }
}

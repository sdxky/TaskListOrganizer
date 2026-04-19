package storage;

import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskPostgresStorage implements TaskStorage {

    private final Connection connection;

    public TaskPostgresStorage(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    public List<Task> load() {
        String sql = "SELECT title, description, completion_date, created_date, priority, status FROM tasks";
        List<Task> tasks = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String title         = rs.getString("title");
                String description   = rs.getString("description");
                LocalDate completion = rs.getDate("completion_date").toLocalDate();
                LocalDate created    = rs.getDate("created_date").toLocalDate();
                Priority priority    = Priority.valueOf(rs.getString("priority"));
                Status status        = Status.valueOf(rs.getString("status"));

                Task task = new Task(title, description, completion, created, priority, status);
                restoreState(task);
                tasks.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка загрузки задач из БД", e);
        }

        return tasks;
    }

    public void save(List<Task> tasks) {
        try {
            // Clear and re-insert — simple approach matching your JSON storage strategy
            try (PreparedStatement del = connection.prepareStatement("DELETE FROM tasks")) {
                del.executeUpdate();
            }

            String sql = """
                INSERT INTO tasks (title, description, completion_date, created_date, priority, status)
                VALUES (?, ?, ?, ?, ?::priority_level, ?::task_status)
                """;

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (Task task : tasks) {
                    stmt.setString(1, task.getTitle());
                    stmt.setString(2, task.getDescription());
                    stmt.setDate(3, Date.valueOf(task.getCompletionDate()));
                    stmt.setDate(4, Date.valueOf(task.getCreatedDate()));
                    stmt.setString(5, task.getPriority().name());
                    stmt.setString(6, task.getStatus().name());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения задач в БД", e);
        }
    }

    private void restoreState(Task task) {
        switch (task.getStatus()) {
            case NEW         -> task.setState(new NewState());
            case IN_PROGRESS -> task.setState(new InProgressState());
            case DONE        -> task.setState(new DoneState());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия соединения: " + e.getMessage());
        }
    }
}
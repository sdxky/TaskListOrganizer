package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskJsonStorage implements TaskStorage {

    private final File file;
    private final Gson gson;

    public TaskJsonStorage(String path) {
        this.file = new File(path);
        this.gson = new GsonBuilder()
                .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    public List<Task> load() {
        if (!file.exists()) {
            System.out.println("Task file not found. A new one will be created.");
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<List<Task>>(){}.getType();
            List<Task> tasks = gson.fromJson(reader, type);

            if (tasks == null) tasks = new ArrayList<>();

            for (Task task : tasks) {
                restoreState(task);
            }

            return tasks;

        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON", e);
        }
    }

    public void save(List<Task> tasks) {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON", e);
        }
    }

    private void restoreState(Task task) {
        switch (task.getStatus()) {
            case NEW -> task.setState(new NewState());
            case IN_PROGRESS -> task.setState(new InProgressState());
            case DONE -> task.setState(new DoneState());
        }
    }
}

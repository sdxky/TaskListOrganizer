package service;

import model.Task;
import storage.TaskJsonStorage;
import storage.TaskStorage;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();
    private final TaskStorage storage;


    public TaskManager(TaskStorage storage) {
        this.storage = storage;
        tasks.addAll(storage.load());
    }


    public void addTask(Task task) {
        tasks.add(task);
        storage.save(tasks);
    }

    public void remove(Task task) {
        tasks.remove(task);
        storage.save(tasks);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    public void save() {
        storage.save(tasks);
    }
}
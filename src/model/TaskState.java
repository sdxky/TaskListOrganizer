package model;

import service.TaskManager;

public interface TaskState {

    void moveToInProgress(Task task);

    void moveToDone(Task task);

    void changeDescription(Task task, String newDescription);

    void delete(Task task, TaskManager manager);

    Status getStatus();
}

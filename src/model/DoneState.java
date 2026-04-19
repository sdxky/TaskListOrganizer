package model;


import service.TaskManager;

public class DoneState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {
        throw new IllegalStateException(
                "Нельзя изменить статус завершённой задачи"
        );
    }

    @Override
    public void moveToDone(Task task) {
        throw new IllegalStateException(
                "Cannot change the status of a completed task"
        );
    }

    @Override
    public void changeDescription(Task task, String newDescription) {
        throw new IllegalStateException(
                "Cannot change the description of a completed task"
        );
    }

    @Override
    public void delete(Task task, TaskManager manager) {
        throw new IllegalStateException(
                "Cannot delete a completed task"
        );
    }

    @Override
    public Status getStatus() {
        return Status.DONE;
    }
}

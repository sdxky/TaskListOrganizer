package model;


import service.TaskManager;

public class InProgressState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {
        throw new IllegalStateException(
                "Task is already in IN_PROGRESS status"
        );
    }

    @Override
    public void moveToDone(Task task) {
        task.setState(new DoneState());
    }

    @Override
    public void changeDescription(Task task, String newDescription) {
        throw new IllegalStateException(
                "Cannot change task description while status is IN_PROGRESS"
        );
    }

    @Override
    public void delete(Task task, TaskManager manager) {
        throw new IllegalStateException(
                "Cannot delete a task with IN_PROGRESS status"
        );
    }

    @Override
    public Status getStatus() {
        return Status.IN_PROGRESS;
    }
}

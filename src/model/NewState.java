package model;

import service.TaskManager;

public class NewState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {
        task.setState(new InProgressState());
    }

    @Override
    public void moveToDone(Task task) {
        throw new IllegalStateException(
                "Cannot change status from NEW to DONE"
        );
    }

    @Override
    public void changeDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
    }

    @Override
    public void delete(Task task, TaskManager manager) {
        manager.remove(task);
    }

    @Override
    public Status getStatus() {
        return Status.NEW;
    }
}

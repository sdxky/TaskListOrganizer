package model;


import service.TaskManager;

public class InProgressState implements TaskState {

    @Override
    public void moveToInProgress(Task task) {
        throw new IllegalStateException(
                "Задача уже находится в статусе IN_PROGRESS"
        );
    }

    @Override
    public void moveToDone(Task task) {
        task.setState(new DoneState());
    }

    @Override
    public void changeDescription(Task task, String newDescription) {
        throw new IllegalStateException(
                "Нельзя менять описание задачи со статусом IN_PROGRESS"
        );
    }

    @Override
    public void delete(Task task, TaskManager manager) {
        throw new IllegalStateException(
                "Нельзя удалить задачу со статусом IN_PROGRESS"
        );
    }

    @Override
    public Status getStatus() {
        return Status.IN_PROGRESS;
    }
}

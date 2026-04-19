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
                "Задача уже находится в статусе DONE"
        );
    }

    @Override
    public void changeDescription(Task task, String newDescription) {
        throw new IllegalStateException(
                "Нельзя менять описание завершённой задачи"
        );
    }

    @Override
    public void delete(Task task, TaskManager manager) {
        throw new IllegalStateException(
                "Нельзя удалить завершённую задачу"
        );
    }

    @Override
    public Status getStatus() {
        return Status.DONE;
    }
}

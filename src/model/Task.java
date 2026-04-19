package model;

import service.TaskManager;

import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private LocalDate completionDate;
    private LocalDate createdDate;
    private Priority priority;
    private Status status;
    private transient TaskState state;

    public Task(String title, String description, LocalDate completionDate,
                LocalDate createdDate, Priority priority, Status status) {
        this.title = title;
        this.description = description;
        this.completionDate = completionDate;
        this.createdDate = createdDate;
        this.priority = priority;
        this.status = status;
        this.state = null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void moveToInProgress(TaskManager manager) {
        if (state == null) {
            throw new IllegalStateException("Task state is not initialized");
        }
        state.moveToInProgress(this);
    }

    public void moveToDone(TaskManager manager) {
        if (state == null) {
            throw new IllegalStateException("Task state is not initialized");
        }
        state.moveToDone(this);
    }

    public void changeDescriptionByState(String description) {
        if (state == null) {
            throw new IllegalStateException("Task state is not initialized");
        }
        state.changeDescription(this, description);
    }

    public void deleteByState(TaskManager manager) {
        if (state == null) {
            throw new IllegalStateException("Task state is not initialized");
        }
        state.delete(this, manager);
    }

    public void setState(TaskState state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        this.state = state;
        this.status = state.getStatus();
    }
}

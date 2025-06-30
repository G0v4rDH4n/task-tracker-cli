package com.upskill.tasktracker;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;

@ShellComponent
@RequiredArgsConstructor
public class TaskTrackerCommands {

    private final TaskService taskService;

    @ShellMethod(key = "add", value = "Add a new task")
    public String add(String task) {
        Task taskObj = new Task(task);
        try {
            Task addedTask = taskService.addTask(taskObj);
            return "Task Added with ID: " + addedTask.getId();
        } catch (IOException e) {
            return "Operation failed. ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "update", value = "Update a task")
    public String update(int id, String task) {
        try {
            Task existingTask = taskService.updateTask(id, task);
            if (existingTask != null) {
                return "Task Updated";
            } else {
                return "Task not found";
            }
        } catch (IOException e) {
            return "Operation failed. ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "delete", value = "Delete a task")
    public String delete(int id) {
        try {
            boolean removed = taskService.deleteTask(id);
            if (removed) {
                return "Task deleted!";
            } else {
                return "No task found with the given Id";
            }
        } catch (IOException e) {
            return "Operation failed. ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "mark-in-progress", value = "Marks the status of task as in-progress")
    public String markInProgress(int id) {
        try {
            Task existingTask = taskService.updateStatus(id, "in-progress");
            if (existingTask != null) {
                return "Task Updated";
            } else {
                return "Task not found";
            }
        } catch (IOException e) {
            return "Operation failed. ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "mark-done", value = "Marks the status of task as done")
    public String markDone(int id) {
        try {
            Task existingTask = taskService.updateStatus(id, "done");
            if (existingTask != null) {
                return "Task Updated";
            } else {
                return "Task not found";
            }
        } catch (IOException e) {
            return "Operation failed. ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = "list", value = "List all tasks")
    public String listTasks(@ShellOption(defaultValue = ShellOption.NULL) String status) {
        try {
            if (status == null || status.isBlank()) {
                return taskService.listAll();
            } else {
                return taskService.listByStatus(status);
            }
        } catch (IOException e) {
            return "Operation failed. ERROR: " + e.getMessage();
        }
    }

    @ShellMethod(key = {"exit", "quit"}, value = "Exit the shell")
    public void exit(@ShellOption(defaultValue = "false", value = "d") boolean deleteTasks) {
        if (deleteTasks) {
            JsonFileHandler.deleteFile();
        }
        throw new ExitRequest();
    }

}

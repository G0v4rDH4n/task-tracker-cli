package com.upskill.tasktracker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static com.upskill.tasktracker.JsonFileHandler.getAllTasks;
import static com.upskill.tasktracker.JsonFileHandler.saveAllTasks;

/**
 * Service class to manage task operations such as adding, updating, deleting,
 * listing, and filtering tasks based on status.
 */
@Slf4j
@Service
public class TaskService {

    /**
     * Adds a new task to the task list.
     *
     * @param task The task object to be added
     * @return The added task with assigned ID
     * @throws IOException If there's an error reading from or writing to the storage
     */
    public Task addTask(Task task) throws IOException {
        List<Task> tasks = getAllTasks();
        if (tasks == null || tasks.isEmpty()) {
            tasks = new ArrayList<>();
            task.setId(1);
        } else {
            task.setId(tasks.size() + 1);
        }
        tasks.add(task);
        saveAllTasks(tasks);
        return task;
    }

    /**
     * Updates the description of an existing task.
     *
     * @param id          The ID of the task to update
     * @param description The new description for the task
     * @return The updated task or null if task not found
     * @throws IOException If there's an error reading from or writing to the storage
     */
    public Task updateTask(int id, String description) throws IOException {
        List<Task> tasks = getAllTasks();
        Task existingTask = findById(tasks, id);
        if (existingTask == null) {
            return null;
        }
        existingTask.setDescription(description);
        existingTask.setUpdatedAt(LocalDateTime.now());
        tasks.set(tasks.indexOf(existingTask), existingTask);
        saveAllTasks(tasks);
        return existingTask;
    }

    /**
     * Deletes a task with the specified ID.
     *
     * @param id The ID of the task to delete
     * @return true if task was deleted, false if task was not found
     * @throws IOException If there's an error reading from or writing to the storage
     */
    public boolean deleteTask(int id) throws IOException {
        List<Task> tasks = getAllTasks();
        boolean deleted = tasks.removeIf(task -> task.getId() == id);
        saveAllTasks(tasks);
        return deleted;
    }

    /**
     * Lists all tasks in a formatted string.
     *
     * @return A formatted string containing all tasks
     * @throws IOException If there's an error reading from the storage
     */
    public String listAll() throws IOException {
        return printableTasksString(getAllTasks());
    }

    private Task findById(List<Task> tasks, int id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    private String printableTasksString(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "No tasks found!";
        }
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Tasks found: " + tasks.size());
        for (Task task : tasks) {
            joiner.add(task.getId() + " - " + task.getDescription());
        }
        return joiner.toString();
    }

    /**
     * Lists all tasks filtered by the specified status.
     *
     * @param status The status to filter tasks by (todo, done, or in-progress)
     * @return A formatted string containing filtered tasks
     * @throws IOException If there's an error reading from the storage
     */
    public String listByStatus(String status) throws IOException {
        List<Task> tasks = getAllTasks();
        return switch (status) {
            case "todo", "done", "in-progress" -> printableTasksString(filterTasksByStatus(tasks, status));
            default -> "ERROR: Invalid task status!";
        };
    }

    private List<Task> filterTasksByStatus(List<Task> tasks, String status) {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return tasks.stream().filter(task -> task.getStatus().equals(status)).toList();
    }

    /**
     * Updates the status of an existing task.
     *
     * @param id     The ID of the task to update
     * @param status The new status for the task
     * @return The updated task or null if task not found
     * @throws IOException If there's an error reading from or writing to the storage
     */
    public Task updateStatus(int id, String status) throws IOException {
        List<Task> tasks = getAllTasks();
        Task task = findById(tasks, id);
        if (task == null) {
            return null;
        }
        task.setStatus(status);
        saveAllTasks(tasks);
        return task;
    }
}

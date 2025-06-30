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

@Slf4j
@Service
public class TaskService {

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

    public boolean deleteTask(int id) throws IOException {
        List<Task> tasks = getAllTasks();
        boolean deleted = tasks.removeIf(task -> task.getId() == id);
        saveAllTasks(tasks);
        return deleted;
    }

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

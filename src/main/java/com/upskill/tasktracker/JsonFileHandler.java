package com.upskill.tasktracker;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JsonFileHandler {

    private static final Path JSON_FILE_PATH = Path.of("./tasks.json");

    private JsonFileHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void createJsonFile() throws IOException {
        File jsonFile = JSON_FILE_PATH.toFile();
        if (!jsonFile.exists()) {
            boolean created = jsonFile.createNewFile();
            if (created) {
                log.info("JSON file created at: {}", jsonFile.getAbsolutePath());
            } else {
                log.error("Failed to create JSON file at: {}", jsonFile.getAbsolutePath());
            }
        }
    }

    public static JSONArray readJsonFile() throws IOException {
        if (JSON_FILE_PATH.toFile().exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(JSON_FILE_PATH.toFile()))) {
                String content = br.lines().collect(Collectors.joining("\n"));
                if (content.isBlank()) {
                    return new JSONArray();
                }
                return new JSONArray(content);
            } catch (IOException e) {
                log.error(e.getMessage());
                return null;
            }
        } else {
            createJsonFile();
            return new JSONArray();
        }
    }

    public static void writeJsonFile(JSONArray jsonArray) throws IOException {
        createJsonFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSON_FILE_PATH.toFile()))) {
            bw.write(jsonArray.toString(2));
        } catch (IOException e) {
            log.error("Failed to write to the file", e);
        }
    }

    public static void deleteFile() {
        try {
            Files.deleteIfExists(JSON_FILE_PATH);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static List<Task> getAllTasks() throws IOException {
        JSONArray tasksJson = readJsonFile();
        if (tasksJson == null || tasksJson.isEmpty()) {
            return Collections.emptyList();
        }
        return jsonArrayToTasks(tasksJson);
    }

    public static void saveAllTasks(List<Task> tasks) throws IOException {
        if (tasks == null || tasks.isEmpty()) {
            return;
        }
        writeJsonFile(tasksToJSONArray(tasks));
    }

    public static Task jsonObjectToTask(JSONObject jsonObject) {
        return Task.builder().id(jsonObject.optIntegerObject("id"))
                .description(jsonObject.optString("description"))
                .status(jsonObject.optString("status"))
                .createdAt(stringToLocalDateTime(jsonObject.optString("createdAt")))
                .updatedAt(stringToLocalDateTime(jsonObject.optString("updatedAt")))
                .build();
    }

    public static LocalDateTime stringToLocalDateTime(String s) {
        if (s == null) {
            return null;
        }
        try {
            return LocalDateTime.parse(s);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static List<Task> jsonArrayToTasks(JSONArray tasksJson) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < tasksJson.length(); i++) {
            JSONObject taskJson = tasksJson.getJSONObject(i);
            tasks.add(jsonObjectToTask(taskJson));
        }
        return tasks;
    }

    public static JSONArray tasksToJSONArray(List<Task> tasks) {
        JSONArray jsonArray = new JSONArray();
        tasks.stream().map(Task::toJSONObject).forEach(jsonArray::put);
        return jsonArray;
    }
}

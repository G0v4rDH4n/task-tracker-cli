package com.upskill.tasktracker;

import java.time.LocalDateTime;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Task {

    private Integer id;

    private String description;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Task(String task) {
        description = task;
        status = "todo";
        createdAt = LocalDateTime.now();
    }

    public JSONObject toJSONObject() {
        JSONObject taskJson = new JSONObject();
        taskJson.put("id", this.id);
        taskJson.put("description", this.description);
        taskJson.put("status", this.status);
        taskJson.put("createdAt", this.createdAt);
        taskJson.put("updatedAt", this.updatedAt);
        return taskJson;
    }

}

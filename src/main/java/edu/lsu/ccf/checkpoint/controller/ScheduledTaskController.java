package edu.lsu.ccf.checkpoint.controller;

import edu.lsu.ccf.checkpoint.model.ScheduledTask;
import edu.lsu.ccf.checkpoint.service.ScheduledTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/scheduledTask")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ScheduledTaskController {
    private final ScheduledTaskService scheduledTaskService;

    @GetMapping("/")
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.ok(scheduledTaskService.getAllTasks());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        try {
            ScheduledTask task = scheduledTaskService.getTaskById(id);
            if (Objects.nonNull(task)) {
                return ResponseEntity.ok(task);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> startTask(@RequestBody ScheduledTask task) {
        log.info("Starting scheduled task: {}", task);
        try {
            task = scheduledTaskService.createTask(task);
            scheduledTaskService.startTask(task);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/stop/{id}")
    public ResponseEntity<?> stopTask(@PathVariable String id) {
        log.info("Stopping scheduled task: {}", id);
        try {
            scheduledTaskService.stopTask(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        try {
            scheduledTaskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

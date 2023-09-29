package edu.lsu.ccf.checkpoint.controller;

import edu.lsu.ccf.checkpoint.model.CheckpointModel;
import edu.lsu.ccf.checkpoint.service.CPSService;
import edu.lsu.ccf.checkpoint.service.CheckpointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/checkpoint")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CheckpointController {
    private final CheckpointService checkpointService;
    private final CPSService cpsService;

    @PostMapping
    public ResponseEntity<CheckpointModel> createCheckpoint(@RequestBody CheckpointModel checkpoint) {
        log.info("Creating checkpoint: {}", checkpoint);
        return ResponseEntity.ok(checkpointService.createCheckpoint(checkpoint));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckpointModel> getCheckpoint(@PathVariable String id) {
        log.info("Getting checkpoint with id: {}", id);
        CheckpointModel checkpoint = checkpointService.getCheckpoint(id);
        if (checkpoint != null) {
            return ResponseEntity.ok(checkpoint);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<CheckpointModel> updateCheckpoint(@RequestBody CheckpointModel checkpoint) {
        log.info("Updating checkpoint: {}", checkpoint);
        return ResponseEntity.ok(checkpointService.updateCheckpoint(checkpoint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckpoint(@PathVariable String id) {
        log.info("Deleting checkpoint with id: {}", id);
        checkpointService.deleteCheckpoint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<CheckpointModel> getAllCheckpoints() {
        log.info("Getting all checkpoints");
        try {
            List<CheckpointModel> checkpoints = checkpointService.getAllCheckpoints();
            log.info("Found {} checkpoints", checkpoints.size());
            return checkpoints;
        } catch (Exception e) {
            return List.of();
        }
    }
    @PostMapping("/create-docker-checkpoint")
    public ResponseEntity<?> createDockerCheckpoint(@RequestBody Map<String, String> options) {
        log.info("createDockerCheckpoint: {}", options);
        try {
            cpsService.createDockerCheckpoint(options);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/create-k8s-checkpoint")
    public ResponseEntity<?> createK8SCheckpoint(@RequestBody Map<String, String> options) {
        log.info("createK8SCheckpoint: {}", options);
        try {
            cpsService.createK8sCheckpoint(options);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/create-podman-checkpoint")
    public ResponseEntity<?> createPodmanCheckpoint(@RequestBody Map<String, String> options) {
        log.info("createPodmanCheckpoint: {}", options);
        try {
            cpsService.createPodmanCheckpoint(options);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}


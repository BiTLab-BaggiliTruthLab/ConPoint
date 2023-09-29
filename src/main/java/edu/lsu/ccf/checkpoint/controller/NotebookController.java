package edu.lsu.ccf.checkpoint.controller;

import edu.lsu.ccf.checkpoint.model.NotebookModel;
import edu.lsu.ccf.checkpoint.service.NotebookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notebook")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NotebookController {
    private NotebookService notebookService;

    @PostMapping
    public NotebookModel createNotebook(@RequestBody NotebookModel notebook) {
        log.info("Creating notebook: {}", notebook);
        return notebookService.createNotebook(notebook);
    }

    @GetMapping("/{id}")
    public NotebookModel getNotebook(@PathVariable String id) {
        log.info("Getting notebook with id: {}", id);
        return notebookService.getNotebook(id);
    }

    @PutMapping
    public NotebookModel updateNotebook(@RequestBody NotebookModel notebook) {
        log.info("Updating notebook: {}", notebook);
        return notebookService.updateNotebook(notebook);
    }

    @DeleteMapping("/{id}")
    public void deleteNotebook(@PathVariable String id) {
        log.info("Deleting notebook with id: {}", id);
        notebookService.deleteNotebook(id);
    }

    @GetMapping("/container/{containerId}")
    public List<NotebookModel> getNotebooksByContainerId(@PathVariable String containerId) {
        log.info("Getting notebooks with containerId: {}", containerId);
        try {
            List<NotebookModel> notebooks = notebookService.getNotebooksByContainerId(containerId);
            log.info("Found {} notebooks", notebooks.size());
            return notebooks;
        }catch (Exception e) {
            return List.of();
        }
    }
    @GetMapping("/checkpoint/{checkpointId}")
    public List<NotebookModel> getNotebooksByCheckpointId(@PathVariable String checkpointId) {
        log.info("Getting notebooks with checkpointId: {}", checkpointId);
        try {
            List<NotebookModel> notebooks = notebookService.getNotebooksByCheckpointId(checkpointId);
            log.info("Found {} notebooks", notebooks.size());
            return notebooks;
        }catch (Exception e) {
            return List.of();
        }
    }
    @GetMapping
    public List<NotebookModel> getAllNotebooks() {
        log.info("Getting all notebooks");
        try {
            List<NotebookModel> notebooks = notebookService.getAllNotebooks();
            log.info("Found {} notebooks", notebooks.size());
            return notebooks;
        } catch (Exception e) {
            return List.of();
        }
    }
}


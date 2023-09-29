package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.model.NotebookModel;
import edu.lsu.ccf.checkpoint.repository.NotebookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotebookService {
    private NotebookRepository notebookRepository;

    public NotebookModel createNotebook(NotebookModel notebook) {
        notebook.setCreatedAt(new Date());
        notebook.setUpdatedAt(new Date());
        return notebookRepository.save(notebook);
    }

    public NotebookModel getNotebook(String id) {
        return notebookRepository.findById(id).orElse(null);
    }
    public List<NotebookModel> getAllNotebooks() {
        return notebookRepository.findAll();
    }

    public NotebookModel updateNotebook(NotebookModel notebook) {
        notebook.setUpdatedAt(new Date());
        return notebookRepository.save(notebook);
    }

    public void deleteNotebook(String id) {
        notebookRepository.deleteById(id);
    }

    public List<NotebookModel> getNotebooksByContainerId(String containerId) {
        return notebookRepository.findByContainerId(containerId);
    }

    public List<NotebookModel> getNotebooksByCheckpointId(String checkpointId) {
        return notebookRepository.findByCheckpointId(checkpointId);
    }
}


package edu.lsu.ccf.checkpoint.repository;

import edu.lsu.ccf.checkpoint.model.NotebookModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotebookRepository extends MongoRepository<NotebookModel, String> {
    List<NotebookModel> findByContainerId(String containerId);

    List<NotebookModel> findByCheckpointId(String checkpointId);
}


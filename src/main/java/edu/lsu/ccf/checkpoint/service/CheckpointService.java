package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.model.CheckpointModel;
import edu.lsu.ccf.checkpoint.repository.CheckpointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CheckpointService {
    private final CheckpointRepository checkpointRepository;
    public CheckpointModel createCheckpoint(CheckpointModel checkpoint) {
        return checkpointRepository.save(checkpoint);
    }
    public CheckpointModel getCheckpoint(String id) {
        return checkpointRepository.findById(id).orElse(null);
    }
    public CheckpointModel updateCheckpoint(CheckpointModel checkpoint) {
        return checkpointRepository.save(checkpoint);
    }
    public void deleteCheckpoint(String id) {
        checkpointRepository.deleteById(id);
    }
    public List<CheckpointModel> getAllCheckpoints() {
        return checkpointRepository.findAll();
    }
}

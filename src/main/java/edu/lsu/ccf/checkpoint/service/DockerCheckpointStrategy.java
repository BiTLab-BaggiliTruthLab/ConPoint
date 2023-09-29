package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.interfaces.CheckpointStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class DockerCheckpointStrategy implements CheckpointStrategy {
    private final CPSService cpsService;

    @Override
    public void createCheckpoint(Map<String, String> options) {
        cpsService.createDockerCheckpoint(options);
    }
}


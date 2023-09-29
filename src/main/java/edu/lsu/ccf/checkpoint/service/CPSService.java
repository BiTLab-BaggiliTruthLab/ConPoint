package edu.lsu.ccf.checkpoint.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@Service
@Slf4j
public class CPSService {
    private final CPSDocker cpsDocker;
    private final CPSSchedulerImpl cpsScheduler;
    private final CPSK8s cpsK8s;
    private final CPSPodman cpsPodman;
    public void createK8sCheckpoint(Map<String, String> options) {
        cpsK8s.createCheckpoint(options);
    }
    public void createDockerCheckpoint(Map<String, String> options) {
        cpsDocker.createCheckpoint(options);
    }
    public void createPodmanCheckpoint(Map<String, String> options) {
        cpsPodman.createCheckpoint(options);
    }
}

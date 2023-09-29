package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.interfaces.CPSScheduler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CPSSchedulerImpl implements CPSScheduler {
    @Override
    public void scheduleCheckpoint(String path, int interval, String containerId) {

    }
}

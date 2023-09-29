package edu.lsu.ccf.checkpoint.interfaces;

import java.util.Map;

@FunctionalInterface
public interface CheckpointStrategy {
    void createCheckpoint(Map<String, String> options);
}


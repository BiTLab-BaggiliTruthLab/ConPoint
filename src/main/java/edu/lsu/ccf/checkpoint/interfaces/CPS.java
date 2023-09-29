package edu.lsu.ccf.checkpoint.interfaces;

import java.nio.file.Path;
import java.util.Map;

/**
 * create checkpoints
 * restore checkpoints
 * delete checkpoints
 * list checkpoints
 */
/**
 * scheduler or the checkpoint service
 * migration maybe in future
 * handle collection
 * maybe sharding?
 * doesn't do any processing
 */
public interface CPS {
    /**
     * create a checkpoint
     * @param path the path to the checkpoint file
     */
    void createCheckpoint(Map<String, String> options);
    /**
     * restore a checkpoint
     * @param path the path to the checkpoint file
     */
    void restoreCheckpoint(Map<String, String> options);
    /**
     * delete a checkpoint
     * @param path the path to the checkpoint file
     */
    void deleteCheckpoint(Map<String, String> options);
    /**
     * list all checkpoints
     * @param path the path to the checkpoint directory
     */
    void listCheckpoints(Map<String, String> options);
}

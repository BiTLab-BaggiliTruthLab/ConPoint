package edu.lsu.ccf.checkpoint.interfaces;

public interface CPSScheduler {
    /**
     * schedule the checkpoint
     * @param path the path to the checkpoint file
     * @param interval the interval to checkpoint
     * @param containerId the container id to checkpoint
     */
    void scheduleCheckpoint(String path, int interval, String containerId);
}

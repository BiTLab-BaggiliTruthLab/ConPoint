package edu.lsu.ccf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
// enable cors
@CrossOrigin(origins = "*")
public class CcfApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcfApplication.class, args);
    }
    /**
     * the ability to schudule a checkpoint
     * the ability to schudule a restore
     * the ability to schudule a migration
     * the ability to schudule a migration with a checkpoint
     * show all checkpoints, search with id or labels or tags
     * when selecting a checkpoint, show all the files
     * two options of showing the files crit or hex view for criu files
     * the other files show the content and the path
     * the ability to show multiple views as tabs
     * The ability to schedule a checkpoint based on a specific event, such as a CPU spike or a memory leak.
     * The ability to schedule a restore to a specific point in time.
     * The ability to migrate a checkpoint to a different host or cluster.
     * The ability to migrate a checkpoint with a live application running on it.
     * The ability to create a snapshot of a checkpoint.
     * The ability to restore a snapshot to a specific point in time.
     * The ability to compare two checkpoints.
     * The ability to export a checkpoint to a format that can be used by other tools.
     * The ability to import a checkpoint from a format that can be used by other tools.
     */
    /**
     * requirements
     * install criu, depending on cri it should have the required dependencies
     * for k8s, enable cri-o and install criu
     * for podman, install criu
     * for docker, install criu
     */

}

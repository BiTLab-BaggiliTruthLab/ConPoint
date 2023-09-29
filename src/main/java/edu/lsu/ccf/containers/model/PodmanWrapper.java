package edu.lsu.ccf.containers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PodmanWrapper {
    private boolean autoRemove;
    private String command;
    private String created;
    private String createdAt;
    private boolean exited;
    private long exitedAt;
    private int exitCode;
    private String id;
    private String image;
    private String imageID;
    private boolean isInfra;
    private Map<String, String> labels;
    private List<String> mounts;
    private List<String> names;
    private Map<String, String> namespaces;
    private List<String> networks;
    private int pid;
    private String pod;
    private String podName;
    private List<String> ports;
    private String size;
    private long startedAt;
    private String state;
    private String status;
}

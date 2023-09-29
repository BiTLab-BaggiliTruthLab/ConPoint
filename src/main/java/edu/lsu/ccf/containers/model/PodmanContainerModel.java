package edu.lsu.ccf.containers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class PodmanContainerModel {
    private boolean AutoRemove;
    private String Command;
    private String Created;
    private String CreatedAt;
    private boolean Exited;
    private long ExitedAt;
    private int ExitCode;
    private String Id;
    private String Image;
    private String ImageID;
    private boolean IsInfra;
    private Map<String, String> Labels;
    private List<String> Mounts;
    private List<String> Names;
    private Map<String, String> Namespaces;
    private List<String> Networks;
    private int Pid;
    private String Pod;
    private String PodName;
    private List<String> Ports;
    private String Size;
    private long StartedAt;
    private String State;
    private String Status;
}

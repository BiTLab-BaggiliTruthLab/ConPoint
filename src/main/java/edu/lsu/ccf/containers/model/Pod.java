package edu.lsu.ccf.containers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pod {
    private String name;
    private String creationTimestamp;
    private PodStatus status;
    private List<ContainerStatus> containerStatuses;
    private Metadata metadata;
}

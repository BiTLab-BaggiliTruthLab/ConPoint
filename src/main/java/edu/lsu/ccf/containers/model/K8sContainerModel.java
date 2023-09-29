package edu.lsu.ccf.containers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class K8sContainerModel {
    private String apiVersion;
    private PodList items;
}


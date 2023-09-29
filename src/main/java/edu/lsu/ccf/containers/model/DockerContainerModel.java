package edu.lsu.ccf.containers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.dockerjava.api.model.Container;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DockerContainerModel {
    private Container container;

    public static List<DockerContainerModel> from(List<Container> containers) {
        return containers.stream().map(c-> DockerContainerModel.builder().container(c).build()).toList();
    }
}

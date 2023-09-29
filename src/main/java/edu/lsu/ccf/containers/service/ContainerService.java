package edu.lsu.ccf.containers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import edu.lsu.ccf.containers.model.DockerContainerModel;
import edu.lsu.ccf.containers.model.PodList;
import edu.lsu.ccf.containers.model.PodmanContainerModel;
import edu.lsu.ccf.utility.UtilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ContainerService {
    private final DockerClient dockerClient;
    private final ObjectMapper objectMapper;
    private final UtilityService utilityService;
    /**
     * TODO: Support multiple clients, docker, kubernetes, podman.
     */

    public List<DockerContainerModel> getAllDockerContainers() {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        if(Objects.isNull(containers) || containers.isEmpty()) {
            return DockerContainerModel.from(List.of());
        }
        return DockerContainerModel.from(containers);
    }
    public List<PodmanContainerModel> getAllPodmanContainers() {
        try {
         ProcessBuilder processBuilder = new ProcessBuilder();
         // sudo curl --unix-socket /tmp/podman.sock  -H content-type:application/json  http://d/v4.0.0/libpod/containers/json
            Process process = processBuilder.command("sudo", "curl", "--unix-socket", "/tmp/podman.sock", "-H", "content-type:application/json", "http://d/v4.0.0/libpod/containers/json").start();
            InputStream inputStream = process.getInputStream();
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }
            int exitCode = process.waitFor();
            if (exitCode==0){
                log.info("Successfully executed podman command");
                String jsonOutput = output.toString();
                log.info("JSON output: {}", jsonOutput);
                // List<Po
                List<PodmanContainerModel> podmanContainerModel = objectMapper.readValue(jsonOutput, List.class);
                return podmanContainerModel;
            }
            else {
                log.info("Failed to execute podman command");
            }

        } catch (Exception e) {
            log.error("Error executing podman command");
            e.printStackTrace();
        }
        log.info("Returning empty list");
        return List.of();
    }
    public PodList getAllKubernetesContainers() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            // curl -sk http://localhost:8001/api/v1/pods?allNamespaces=true
            Process process = processBuilder.command("curl", "-sk", "http://localhost:8001/api/v1/pods?allNamespaces=true").start();
            InputStream inputStream = process.getInputStream();
            // print errors
            InputStream errorStream = process.getErrorStream();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                String jsonOutput = output.toString();
                log.info("JSON output: {}", jsonOutput);
                log.info("Successfully executed kubectl get pods command");
                PodList podList = objectMapper.readValue(jsonOutput, PodList.class);
                return podList;
            } else {
                log.error("Error executing kubectl get pods command");
                String errors = utilityService.readInputStreamToString(errorStream);
                log.error("Error: {}", errors);
            }
        } catch (Exception e) {
            log.error("Error executing kubectl get pods command");
            e.printStackTrace();
        }
        log.info("Returning empty podlist");
        return PodList.builder().build();
    }
}

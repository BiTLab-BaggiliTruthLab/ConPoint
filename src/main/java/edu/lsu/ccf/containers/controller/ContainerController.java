package edu.lsu.ccf.containers.controller;

import edu.lsu.ccf.containers.model.PodList;
import edu.lsu.ccf.containers.service.ContainerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/container")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class ContainerController {
    private final ContainerService containerService;

    @GetMapping("/docker")
    public ResponseEntity<?> getAllDockerContainers() {
        try {
            return ResponseEntity.ok(containerService.getAllDockerContainers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/podman")
    public ResponseEntity<?> getAllPodmanContainers() {
        try {
            return ResponseEntity.ok(containerService.getAllPodmanContainers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/kubernetes")
    public ResponseEntity<?> getAllK8sContainers() {
        try {
            PodList podList = containerService.getAllKubernetesContainers();
            log.info("PodList size: {}", podList.getItems().size());
            return ResponseEntity.ok(podList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

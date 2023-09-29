package edu.lsu.ccf.checkpoint.enums;

public enum ContainerType {
    PODMAN("podman"),
    DOCKER("docker"),
    K8S("k8s");
    public final String label;
    ContainerType(String label) {
        this.label = label;
    }
}

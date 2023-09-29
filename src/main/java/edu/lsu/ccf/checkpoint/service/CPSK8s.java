package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.interfaces.CPS;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.http.client.HttpClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class CPSK8s implements CPS {
    private static RestTemplate restTemplate;
    @Override
    public void createCheckpoint(Map<String, String> options) {
        try {
            String url = options.get("url");
            String port = options.get("port");
            String nameSpace = options.get("nameSpace");
            String podName = options.get("podName");
            String containerName = options.get("containerName");
            String checkpointPath = options.get("checkpointPath");
            String keyPath = options.get("keyPath");
            String certPath = options.get("certPath");
            String caPath = options.get("caPath");
            // sudo curl -sk -X POST  "https://localhost:10250/checkpoint/NAMESPACE/PODNAME/CONTAINERNAME"   --key /etc/kubernetes/pki/apiserver-kubelet-client.key   --cacert /etc/kubernetes/pki/ca.crt   --cert /etc/kubernetes/pki/apiserver-kubelet-client.crt
            String command = "curl -sk -X POST  \"https://" + url + ":" + port + "/checkpoint/" + nameSpace + "/" + podName + "/" + containerName + "\"   --key " + keyPath + "   --cacert " + caPath + "   --cert " + certPath;
            log.info("command: {}", command);
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("sh", "-c", command);

            Process process = processBuilder.start();
            BufferedOutputStream outputStream = new BufferedOutputStream(process.getOutputStream());
            BufferedInputStream errorStream = new BufferedInputStream(process.getErrorStream());
            int checkPointExitVal = process.waitFor();
            log.info("checkPointExitVal: {}", checkPointExitVal);
            if(checkPointExitVal != 0){
                log.error("error in createCheckpoint");
                log.error("errorStream: {}", errorStream);
                return;
            }
            // default checkpoints path is /var/lib/kubelet/checkpoints, move  the checkpoints to the specified path
            // use regex move *.tar.gz to the specified path
            String moveCommand = "mv /var/lib/kubelet/checkpoints/*.tar " + checkpointPath;
            log.info("moveCommand: {}", moveCommand);
            ProcessBuilder moveProcessBuilder = new ProcessBuilder();
            moveProcessBuilder.command("sh", "-c", moveCommand);
            Process moveProcess = moveProcessBuilder.start();
            int moveExitVal = moveProcess.waitFor();
            log.info("moveExitVal: {}", moveExitVal);
    }catch (Exception e){
            log.error("error in createCheckpoint", e);
        }
    }
    @Override
    public void restoreCheckpoint(Map<String, String> options) {

    }

    @Override
    public void deleteCheckpoint(Map<String, String> options) {

    }

    @Override
    public void listCheckpoints(Map<String, String> options) {

    }
}

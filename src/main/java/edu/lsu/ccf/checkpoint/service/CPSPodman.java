package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.interfaces.CPS;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CPSPodman implements CPS {
    @Override
    public void createCheckpoint(Map<String, String> options) {
        // sudo curl -XPOST --unix-socket /tmp/podman.sock -H \"content-type:application/json\" \"http://d/v4.0.0/libpod/containers/{CONTAINER_ID}/checkpoint?export=true&keep=true&leaveRunning=true&printStats=true\" --output ./PATH/checkpoint {CONTAINER_ID} "java time instant time" .tar
        // the errror in logs:
        // Executing command: sudo curl -XPOST --unix-socket /tmp/podman.sock -H " content-type:application/json " " http://d/v4.0.0/libpod/containers/bcb4e9708f69/checkpoint?export=true&keep=true&leaveRunning=true&printStats=true" --output /tmp/check//checkpoint+ bcb4e9708f69 2023-06-06T00:25:54.299436924Z.tar
        String command = "sudo curl -XPOST --unix-socket " + options.get("socket") + " -H \"content-type:application/json\" \"http://d/v4.0.0/libpod/containers/" + options.get("container_id") + "/checkpoint?export=true&keep=true&leaveRunning=true&printStats=true\" --output " + options.get("path") + "checkpoint_podman" + options.get("container_id") + Instant.now() + ".tar";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            log.info("Executing command: {}", command);
            Process process = processBuilder.start();
            BufferedInputStream errorStream = new BufferedInputStream(process.getErrorStream());
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Command execution success!, exit code is: " + exitCode);
            } else {
                log.error("Command execution failed!, exit code is: " + exitCode);
                String errorOutput = readInputStreamToString(errorStream);
                log.error("Error: " + errorOutput);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String readInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
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

package edu.lsu.ccf.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.lsu.ccf.criu.dtos.FileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
@Slf4j
@AllArgsConstructor
public class UtilityService {
    /**
     * unpack .tar file using path and use tar -xvf to unpack
     */
    public void unpackTar(String path, String unpackPath) throws Exception {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("tar", "-xvf", path, "-C", unpackPath);
            log.info("unpackTar: {}", processBuilder.command());
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error unpacking tar file");
        }
    }

    /**
     * Lists the files in a directory and returns the list of file names as strings.
     * @param directoryPath the path to the directory
     */
    public FileResponse listFilesInDirectory(String directoryPath) {
        log.info("listFilesInDirectory: {}", directoryPath);
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        FileResponse fileResponse = FileResponse.builder()
                .files(new ArrayList<>())
                .directories(new ArrayList<>())
                .build();
        if (Objects.nonNull(files)) {
            for (File file : files) {
                if (file.isFile()) {
                    fileResponse.getFiles().add(file.getName());
                } else if (file.isDirectory()) {
                    fileResponse.getDirectories().add(file.getName());
                }
            }
        }
        return fileResponse;
    }
    public List<String> getGDBCommands(){
        return List.of(
                ""
        );
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    public String readInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }

}

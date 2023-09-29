package edu.lsu.ccf.criu.service;

import edu.lsu.ccf.criu.dtos.FileContentDto;
import edu.lsu.ccf.criu.dtos.FileResponse;
import edu.lsu.ccf.utility.UtilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CRIUService {
    private final UtilityService utilityService;
    /**
     * to display the content of the files.
     * @param path the path to the file
     */
    public FileContentDto getJsonStringOfTheFile(Path path) {
        log.info("getJsonStringOfTheFile: {}", path);
        FileContentDto fileContentDto = FileContentDto.builder().build();
        try {
            String command = "crit";
            String showCommand = "show";
            String filePath = path.toString();
            String unableToProcessFlag = "bad magic";

            ProcessBuilder processBuilder = new ProcessBuilder(command, showCommand, filePath);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder output = new StringBuilder();
            String line;
            while (Objects.nonNull((line = reader.readLine()))) {
                output.append(line);
            }
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Successfully executed crit show command");
                String result = output.toString();
                if (result.contains(unableToProcessFlag)) {
                    log.error("The current version of crit can't read the content of this file {}", path);
                }
                fileContentDto.setJsonString(result);
            }
            String rawContent = new String(Files.readAllBytes(path));
            fileContentDto.setRawContent(rawContent);
            return fileContentDto;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing crit show command: " + e.getMessage());
        }
    }

    public Optional<FileResponse> listFilesInDirectory(String directoryPath) {
        log.info("listFilesInDirectory: {}", directoryPath);
        try {
            return Optional.of(utilityService.listFilesInDirectory(directoryPath));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public boolean isRootsFolderUnpacked(String path) throws Exception {
        try {
            Optional<FileResponse> fileResponse = listFilesInDirectory(path);
            // search for "root" folder, if doesn't exists then create the folder then move "rootfs-diff.tar" there then call unpack "rootfs-diff.tar"
            if (fileResponse.isPresent()) {
                FileResponse fileResponse1 = fileResponse.get();
                List<String> directories = fileResponse1.getDirectories();
                if (directories.contains("root")) {
                    log.info("root folder exists");
                } else {
                    log.info("root folder doesn't exists");
                    File rootFolder = new File(path + "/root");
                    rootFolder.mkdir();
                    log.info("root folder created");
                    log.info("rootfs-diff.tar moved to root folder");
                    // unpack rootfs-diff.tar
                    utilityService.unpackTar(path + "/rootfs-diff.tar", path + "/root");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error in isRootsFolderUnpacked: " + e.getMessage());
        }
    }
    public String getRawBytesOfTheFile(Path path) {
        log.info("getRawBytesOfTheFile: {}", path);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in getRawBytesOfTheFile: " + e.getMessage());
        }
    }
    public String getRAMSearchUsingGrep(String path, String searchString, String grepOptions) {
        log.info("getRAMSearchUsingGrep: {}", path);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", "grep " + grepOptions + " " + searchString + " " + path + "/pages-*");
            log.info("grep command: {}", processBuilder.command());
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // log the errors
            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder output = new StringBuilder();
            String line;
            while (Objects.nonNull((line = reader.readLine()))) {
                output.append(line).append("<br>"); // Append HTML line break
            }
            while (Objects.nonNull((line = errorReader.readLine()))) {
                log.error(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Successfully executed grep command");
                return output.toString();
            }
            log.error("exitCode: {}", exitCode);
            return "Couldn't find the string in the RAM";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in getRAMSearchUsingGrep: " + e.getMessage());
        }
    }

}

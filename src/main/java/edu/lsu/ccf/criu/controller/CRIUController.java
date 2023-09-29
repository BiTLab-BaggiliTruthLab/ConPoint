package edu.lsu.ccf.criu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.lsu.ccf.criu.dtos.FileContentDto;
import edu.lsu.ccf.criu.dtos.FileRequestDtos;
import edu.lsu.ccf.criu.dtos.FileResponse;
import edu.lsu.ccf.criu.dtos.GrepSearchRequest;
import edu.lsu.ccf.criu.service.CRIUService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/criu")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CRIUController {
    private final CRIUService criuService;
    private final ObjectMapper objectMapper;
    @PostMapping("/create-read-file")
    public ResponseEntity<?> createReadFile(@RequestBody FileRequestDtos fileRequestDtos) {
        log.info("createReadFile: {}", fileRequestDtos);
        try {
            FileContentDto result = criuService.getJsonStringOfTheFile(Path.of(fileRequestDtos.getPath()));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/read-file")
    public ResponseEntity<?> readFile(@RequestBody FileRequestDtos fileRequestDtos) {
        log.info("readFile: {}", fileRequestDtos);
        try {
            String result = criuService.getRawBytesOfTheFile(Path.of(fileRequestDtos.getPath()));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/list-files")
    public FileResponse listFiles(@RequestBody FileRequestDtos fileRequestDtos) {
        log.info("listFiles: {}", fileRequestDtos);
        Optional<FileResponse> result = criuService.listFilesInDirectory(fileRequestDtos.getPath());
        try {
            return result.orElseThrow(() -> new RuntimeException("Error listing files"));
        } catch (Exception e) {
            e.printStackTrace();
            return FileResponse.builder().build();
        }
    }
    @PostMapping("/roots-files")
    public boolean isRootsFilesUnpacked(@RequestBody FileRequestDtos fileRequestDtos) {
        log.info("isRootsFilesUnpacked: {}", fileRequestDtos);
        try {
            return criuService.isRootsFolderUnpacked(fileRequestDtos.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @PostMapping("/grep-search")
    public String grepSearch(@RequestBody GrepSearchRequest grepSearchRequest){
        log.info("grepSearch: {}", grepSearchRequest);
        try {
            return criuService.getRAMSearchUsingGrep(grepSearchRequest.getPath(), grepSearchRequest.getSearchString(), grepSearchRequest.getGrepOptions());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

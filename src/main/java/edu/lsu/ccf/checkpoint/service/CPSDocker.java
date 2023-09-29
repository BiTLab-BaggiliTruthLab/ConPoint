package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.interfaces.CPS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Map;

@Service
@Slf4j
public class CPSDocker implements CPS {
    @Override
    public void createCheckpoint(Map<String, String> options) {

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

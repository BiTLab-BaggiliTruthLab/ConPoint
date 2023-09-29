package edu.lsu.ccf.checkpoint.service;

import edu.lsu.ccf.checkpoint.interfaces.CheckpointStrategy;
import edu.lsu.ccf.checkpoint.interfaces.ScheduledTaskRepository;
import edu.lsu.ccf.checkpoint.model.ScheduledTask;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduledTaskService {
    private final TaskScheduler taskScheduler;
    private final CPSService cpsService;
    private final ScheduledTaskRepository taskRepo;


    private final Map<String, ScheduledFuture<?>> tasks = new HashMap<>();

    public void startTask(ScheduledTask task) {
        CheckpointStrategy strategy;
        if (task.getContainerType().label.compareTo("docker") == 0) {
            log.info("Creating docker checkpoint strategy");
            strategy = new DockerCheckpointStrategy(cpsService);
        } else if (task.getContainerType().label.compareTo("k8s") == 0) {
            log.info("Creating k8s checkpoint strategy");
            strategy = new K8sCheckpointStrategy(cpsService);
        } else if (task.getContainerType().label.compareTo("podman") == 0) {
            log.info("Creating podman checkpoint strategy");
            strategy = new PodmanCheckpointStrategy(cpsService);
        } else {
            log.error("Invalid container type");
            throw new IllegalArgumentException("Invalid container type");
        }
        log.info("Creating checkpoint task");
        Runnable runnableTask = () -> strategy.createCheckpoint(task.getOptions());
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(runnableTask, new PeriodicTrigger(task.getInterval(), TimeUnit.MILLISECONDS));
        log.info("Adding task to tasks map");
        tasks.put(task.getId(), scheduledTask);
    }

    /*@PostConstruct
    public void init() {
        List<ScheduledTask> tasksFromDb = taskRepo.findAll();
        for (ScheduledTask task : tasksFromDb) {
            startTask(task);
        }
    }*/

    public void stopTask(String taskId) {
        ScheduledFuture<?> scheduledTask = tasks.get(taskId);
        if (Objects.nonNull(scheduledTask)) {
            scheduledTask.cancel(true);
            tasks.remove(taskId);
        }
    }

    public List<ScheduledTask> getAllTasks() {
        return taskRepo.findAll();
    }

    public ScheduledTask getTaskById(String id) {
        return taskRepo.findById(id).orElse(null);
    }

    public ScheduledTask createTask(ScheduledTask task) {
        return taskRepo.save(task);
    }

    public void deleteTask(String id) {
        stopTask(id);
        taskRepo.deleteById(id);
    }
}

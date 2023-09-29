package edu.lsu.ccf.checkpoint.interfaces;

import edu.lsu.ccf.checkpoint.model.ScheduledTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledTaskRepository extends MongoRepository<ScheduledTask, String> {
}


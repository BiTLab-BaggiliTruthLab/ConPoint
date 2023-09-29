package edu.lsu.ccf.checkpoint.repository;

import edu.lsu.ccf.checkpoint.model.CheckpointModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepository extends MongoRepository<CheckpointModel, String> {

}

package edu.lsu.ccf.checkpoint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document (collection = "checkpoints")
public class CheckpointModel {
    @Id
    private String id;
    private String path;
    private int interval;
    private String containerId;
    private Date createdAt;
    private List<NotebookModel> notebooks;
}

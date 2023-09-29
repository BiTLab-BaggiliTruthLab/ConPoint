package edu.lsu.ccf.checkpoint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document (collection = "notebooks")
public class NotebookModel {
    @Id
    private String id;
    private String name;
    private String containerId;
    private String checkpointPath;
    private String checkpointId;
    private Date createdAt;
    private Date updatedAt;
    private String fileContent;
}

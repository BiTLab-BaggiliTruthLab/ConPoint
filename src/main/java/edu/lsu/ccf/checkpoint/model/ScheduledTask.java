package edu.lsu.ccf.checkpoint.model;

import edu.lsu.ccf.checkpoint.enums.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "scheduled_tasks")
public class ScheduledTask {

    @Id
    private String id;
    private ContainerType containerType;
    private Map<String, String> options;
    private long interval;
}

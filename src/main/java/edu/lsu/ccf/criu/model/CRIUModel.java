package edu.lsu.ccf.criu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "criu")
/**
 * CRIUModel
 *
 */
public class CRIUModel {
    private String id;
}

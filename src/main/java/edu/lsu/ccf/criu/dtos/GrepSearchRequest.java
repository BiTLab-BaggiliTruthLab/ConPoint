package edu.lsu.ccf.criu.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GrepSearchRequest {
    private String path;
    private String searchString;
    private String grepOptions;
}

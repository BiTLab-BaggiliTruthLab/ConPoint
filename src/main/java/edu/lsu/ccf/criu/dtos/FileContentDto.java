package edu.lsu.ccf.criu.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileContentDto {
    private String jsonString;
    private String rawContent;
}

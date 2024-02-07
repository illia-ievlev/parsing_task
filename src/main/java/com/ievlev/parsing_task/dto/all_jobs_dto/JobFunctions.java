package com.ievlev.parsing_task.dto.all_jobs_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobFunctions {
    private String value;
}

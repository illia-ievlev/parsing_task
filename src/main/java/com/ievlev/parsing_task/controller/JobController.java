package com.ievlev.parsing_task.controller;

import com.ievlev.parsing_task.dto.RequestDto;
import com.ievlev.parsing_task.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping("api/v1/jobs")
    public void getJobs(@RequestBody RequestDto requestDto) {
        jobService.processUserRequest(requestDto);
    }
}

package com.ievlev.parsing_task.service;

import com.ievlev.parsing_task.dto.RequestDto;
import com.ievlev.parsing_task.dto.all_jobs_dto.JobHit;
import com.ievlev.parsing_task.dto.all_jobs_dto.SearchResultsWrapper;
import com.ievlev.parsing_task.mapper.JobHitToJobMapper;
import com.ievlev.parsing_task.model.Job;
import com.ievlev.parsing_task.api_client.TechstarsApiClient;
import com.ievlev.parsing_task.repository.JobRepository;
import com.ievlev.parsing_task.utils.JobUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class JobService {
    private final JobHitToJobMapper jobHitToJobMapper;
    private final TechstarsApiClient techstarsApiClient;
    private final JobRepository jobRepository;
    private final GoogleSheetApiService googleSheetApiService;

    private int lastRowInSheets;

    public void processUserRequest(RequestDto requestDto) {
        JobUtil.validateRequestDto(requestDto);
        SearchResultsWrapper firstSearchResultsWrapper = techstarsApiClient.getJobsFromExternalApi(requestDto, 0);
        ConcurrentLinkedQueue<Job> jobListToWrite = process(firstSearchResultsWrapper, requestDto);
//        int numberOfPages = firstSearchResultsWrapper.getResults().get(0).getNbPages();
//        if (numberOfPages > 1) {
//            for (int i = 1; i < numberOfPages; i++) {
//                SearchResultsWrapper searchResultsWrapper = getResultsFromExternalApi(requestDto, i);
//                jobListToWrite.addAll(process(searchResultsWrapper, requestDto));
//            }
//        }
        for (Job job : jobListToWrite) {
            lastRowInSheets++;
            saveJobToDatabaseAndSheets(job, lastRowInSheets);
        }
    }


    private ConcurrentLinkedQueue<Job> process(SearchResultsWrapper searchResultsWrapper, RequestDto requestDto) {
        List<JobHit> hitsSortedByTime = JobUtil.sortJobHitByDatePosted(requestDto, searchResultsWrapper.getResults().get(0).getHits());
        List<JobHit> hitsCheckedForRepetition = checkJobHitByIdInDB(hitsSortedByTime);
//        List<Job> processedJobsReadyToBeWritten = new ArrayList<>();
//        for (JobHit jobHit : hitsCheckedForRepetition) {
//            processedJobsReadyToBeWritten.add(createJobFromHit(jobHit));
//        }
        ConcurrentLinkedQueue<Job> processedJobsReadyToBeWritten = new ConcurrentLinkedQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (JobHit jobHit : hitsCheckedForRepetition) {
            executor.execute(() -> processedJobsReadyToBeWritten.add(jobHitToJobMapper.createJobFromHit(jobHit)));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                System.err.println("Some tasks did not finish before the timeout.");
                // Handle the case where tasks did not finish
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return processedJobsReadyToBeWritten;
    }


    private List<JobHit> checkJobHitByIdInDB(List<JobHit> jobList) {
        List<JobHit> checkedJobHits = new ArrayList<>();
        for (JobHit job : jobList) {
            if (jobRepository.findById(Long.parseLong(job.getObjectID())).isEmpty()) {
                checkedJobHits.add(job);
            }
        }
        return checkedJobHits;
    }


    private void saveJobToDatabaseAndSheets(Job job, int numberOfRawToSave) {
        jobRepository.save(job);
        googleSheetApiService.addJobToSheet(job, numberOfRawToSave);
    }
}

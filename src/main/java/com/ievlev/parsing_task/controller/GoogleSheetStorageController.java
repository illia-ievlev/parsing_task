package com.ievlev.parsing_task.controller;

import com.ievlev.parsing_task.service.GoogleSheetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GoogleSheetStorageController {
    private final GoogleSheetService googleSheetService;

    @GetMapping("api/v1/sheet")
    public String createOrGetGoogleSheet() {
        return googleSheetService.getOrCreateGoogleSheetDto().getUrlToGoogleSheet();
    }

//    @GetMapping("api/v1/sheet")
//    public String getGoogleSheetUrl() {
//        return googleSheetApiService.getGoogleSheetUrl();
//    }

}

package com.ievlev.parsing_task.service;

import com.ievlev.parsing_task.model.GoogleSheet;
import com.ievlev.parsing_task.dto.GoogleSheetDto;
import com.ievlev.parsing_task.repository.GoogleSheetRepository;
import com.ievlev.parsing_task.utils.GoogleSheetConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleSheetService {
    private GoogleSheetRepository googleSheetRepository;
    private GoogleSheetApiService googleSheetApiService;
    private GoogleSheetDto googleSheetDto;

    public String getGoogleSheetUrl() {
        if (googleSheetDto != null) {
            return googleSheetDto.getUrlToGoogleSheet();
        }
        return "table not found. you can create a new one or specify an existing one";
    }

    public GoogleSheetDto getOrCreateGoogleSheetDto() {
        if (googleSheetDto != null) {
            return googleSheetDto;
        }
        Optional<GoogleSheet> googleSheetOptional = googleSheetRepository.findById(1L);
        if (googleSheetOptional.isPresent()) {
            GoogleSheet googleSheet = googleSheetOptional.get();
            return new GoogleSheetDto(googleSheet.getGoogleSheetUrl(),
                    googleSheet.getGoogleSheetId(),
                    googleSheetApiService.getSheetService());
        } else {
            GoogleSheetDto googleSheetDto = googleSheetApiService.createGoogleSheet();
            this.googleSheetDto = googleSheetDto;
            googleSheetRepository.save(GoogleSheetConvertor.convertGoogleSheetDtoToGoogleSheet(googleSheetDto));
            return googleSheetDto;
        }

    }
}

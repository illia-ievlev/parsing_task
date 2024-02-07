package com.ievlev.parsing_task.utils;

import com.ievlev.parsing_task.model.GoogleSheet;
import com.ievlev.parsing_task.dto.GoogleSheetDto;

public class GoogleSheetConvertor {
    public static GoogleSheet convertGoogleSheetDtoToGoogleSheet(GoogleSheetDto googleSheetDto){
        return new GoogleSheet(googleSheetDto.getSpreadsheetId(), googleSheetDto.getUrlToGoogleSheet());
    }
}

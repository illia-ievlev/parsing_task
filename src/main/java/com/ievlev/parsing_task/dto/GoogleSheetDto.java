package com.ievlev.parsing_task.dto;

import com.google.api.services.sheets.v4.Sheets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleSheetDto {
    private String urlToGoogleSheet;
    private String spreadsheetId;
    private Sheets service;

}

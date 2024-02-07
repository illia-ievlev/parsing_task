package com.ievlev.parsing_task.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "google_sheet")
public class GoogleSheet {
    @Id
    @Min(1)
    @Column(name="db_id")
    @NotNull
    private Long bdId;

    @Column(name = "google_sheet_id")
    private String googleSheetId;

    @Column(name = "google_sheet_url")
    private String googleSheetUrl;

    public GoogleSheet(Long bdId, String googleSheetId, String googleSheetUrl) {
        this.bdId = bdId;
        this.googleSheetId = googleSheetId;
        this.googleSheetUrl = googleSheetUrl;
    }

    public GoogleSheet(String googleSheetId, String googleSheetUrl) {
        this.googleSheetId = googleSheetId;
        this.googleSheetUrl = googleSheetUrl;
    }

    public GoogleSheet() {
    }
}

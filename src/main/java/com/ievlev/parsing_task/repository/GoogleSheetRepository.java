package com.ievlev.parsing_task.repository;

import com.ievlev.parsing_task.model.GoogleSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleSheetRepository extends JpaRepository<GoogleSheet, Long> {
}

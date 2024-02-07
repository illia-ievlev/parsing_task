package com.ievlev.parsing_task.dto;

public class AppErrorStatusDto {
    private int status;
    private String message;

    public AppErrorStatusDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

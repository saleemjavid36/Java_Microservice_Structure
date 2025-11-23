package com.pm.patientservice.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResponse <T>{
    private List<T> data;
    private long totalRecords;
    private int page;
    private int size;
    public PagedResponse(Page<T> pageResult) {
        this.data = pageResult.getContent();
        this.totalRecords = pageResult.getTotalElements();
        this.page = pageResult.getNumber(); // page number (0-indexed)
        this.size = pageResult.getSize();
    }
}

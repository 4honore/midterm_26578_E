package com.medilink.medilink.HMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ApiResponse - Standardized API response format
 * 
 * EXPLANATION:
 * - Provides consistent response structure across all endpoints
 * - Includes metadata like timestamp, status, and pagination info
 * - Makes API responses more user-friendly and predictable
 * - Separates data from pagination metadata
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<T> data;
    private PaginationInfo pagination;
    
    // Constructor for successful responses with data
    public ApiResponse(String message, List<T> data) {
        this.timestamp = LocalDateTime.now();
        this.status = 200;
        this.message = message;
        this.data = data;
    }
    
    // Constructor for successful responses with pagination
    public ApiResponse(String message, List<T> data, PaginationInfo pagination) {
        this.timestamp = LocalDateTime.now();
        this.status = 200;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaginationInfo {
        private int currentPage;
        private int totalPages;
        private long totalElements;
        private int pageSize;
        private boolean hasNext;
        private boolean hasPrevious;
    }
}
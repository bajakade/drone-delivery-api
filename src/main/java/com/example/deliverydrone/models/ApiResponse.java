/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 *
 * @author bibrahim
 */

/**
 * Generic version of the ApiResponse class.
 * @param <T> the type of the value being boxed
 */
public class ApiResponse<T> {
    
    private T data;
    private String message;

    //@JsonSerialize(using = AppResponseStatusSerializer.class)
    private Status status;

    public ApiResponse data(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse(Status status) {
        this.status = status;
    }

    public ApiResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

//    public AppResponse page(Page<?> page) {
//        this.data = page.getContent();
//
//        this.firstPage = page.isFirst();
//        this.lastPage = page.isLast();
//        this.hasNext = page.hasNext();
//        this.hasPrevious = page.hasPrevious();
//
//        this.numberOfRecords = page.getTotalElements();
//        this.totalPages = page.getTotalPages();
//        this.pageSize = page.getSize();
//
//        return this;
//    }

    @JsonIgnore
    public boolean isSuccess() {
        return status != null && status == Status.SUCCESS;
    }

//    public static ApiResponse success() {
//        return new ApiResponse(Status.SUCCESS);
//    }
//
//    public static ApiResponse notFound() {
//        return new ApiResponse(Status.NOT_FOUND, "Record not found");
//    }
//
//    public static ApiResponse error() {
//        return new ApiResponse(Status.ERROR);
//    }
//
//    public static ApiResponse invalidParameters() {
//        return new ApiResponse(Status.INVALID_PARAMETERS);
//    }
//
//    public static ApiResponse duplicateFound() {
//        return new ApiResponse(Status.DUPLICATE_FOUND);
//    }

    public Status getStatus() {
        return this.status;
    }
    
    public String getMessage(){
     return this.message;
    }
    
    public ApiResponse setMessage(String message) {
    this.message = message;

    return this;
    }
    
    public T getData(){
        return this.data;
    }
    
    public void setData(T data){
        this.data = data;
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        SUCCESS(200),
        DUPLICATE_FOUND(302),
        INVALID_PARAMETERS(400),
        FORBIDDEN(403),
        NOT_FOUND(404),
        ERROR(500);

        private final int id;
        
//        private Status(int id) {
//            this.id = id;
//        }
    }
}

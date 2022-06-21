/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.exceptions;

import com.example.deliverydrone.models.ApiError;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author bibrahim
 */

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class DroneApiExceptionHandler extends ResponseEntityExceptionHandler{

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ApiError error = new ApiError("Server Error", details);
    return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
 
  @ExceptionHandler(InvalidDroneStateException.class)
  public final ResponseEntity<Object> handleInvalideDroneStateException(InvalidDroneStateException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ApiError error = new ApiError("Invalid Drone Status", details);
    return new ResponseEntity(error, HttpStatus.FORBIDDEN);
  }
  
  @ExceptionHandler(EntityNotFoundException.class)
  public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ApiError error = new ApiError("Unknown Entity", details);
    return new ResponseEntity(error, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(DuplicateRegistrationException.class)
  public final ResponseEntity<Object> handleDuplicateRegistrationException(DuplicateRegistrationException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ApiError error = new ApiError("Registration collision", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }
 
 
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> details = new ArrayList<>();
    for(ObjectError error : ex.getBindingResult().getAllErrors()) {
      details.add(error.getDefaultMessage());
    }
    details.add(ex.getLocalizedMessage());
    ApiError error = new ApiError("Validation Failed", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }
}

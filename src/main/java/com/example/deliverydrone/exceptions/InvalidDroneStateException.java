/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author bibrahim
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDroneStateException extends RuntimeException {
    public InvalidDroneStateException(String exception) {
      super(exception);
    }   
}

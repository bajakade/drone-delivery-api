/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.models;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author bibrahim
 */


@Setter
@Getter
@XmlRootElement(name = "error")
public class ApiError {
    public ApiError(String message, List<String> errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

  private String message;
  private List<String> errors;
}

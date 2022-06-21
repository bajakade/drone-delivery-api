/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.models;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import com.example.deliverydrone.models.enums.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 *
 * @author bibrahim
 */
@Getter
@Setter
@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
@Table(name="drones")
public class Drone implements Serializable {
    
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO )
    private Long id;
    
    @Column(unique = true)
    @NotBlank(message = "Serial number is mandatory")
    @Size(max = 100, message = "Serial number must be less than 100 characters")
    private String serialNumber;
    
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    
    @Min(value=0, message="Battery charge cannot be less than 0%")  
    @Max(value=100, message="Battery charge can not be more than 100%")  
    private int battery;
    
//    @Min(value=1, message="Drone weight cannot be less than zero(1)")  
//    @Max(value=500, message="Drone weight can not be more than 500") 
    private double weight;
    
    @ManyToMany
    @JoinTable(
       name = "drone_load",
       joinColumns = @JoinColumn(name = "drone_id"),
       inverseJoinColumns = @JoinColumn(name = "medication_code")
    )
    private List<Medication> medications;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State droneStatus;
    
    @Override
    public String toString(){
        return "[ Serial No: " + getSerialNumber() + 
                ", model=" + getModel() + 
                ", Status =" + getDroneStatus() +
                ", Battery="  + 
                getBattery() + 
            "%]";
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.sql.Blob;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author bibrahim
 */
@Setter
@Getter
@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Medication implements Serializable{
    
    public Medication(){}
    public Medication(String name, String code, int weight){
        this.name = name;
        this.code = code;
        this.weight = weight;
    }
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Pattern(regexp = "[0-9A-Za-z-_]+")
    private String name;
    
    @NotNull
    private int weight;
    
    @Pattern(regexp = "[0-9A-Z-_]+")
    private String code;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "medications")
    private List<Drone> drones;
    
    //private Blob image;
    
    @Override
    public String toString(){
        return "[ name=" + name + ", code=" + code + ", weight=" + weight + "]";
    }
}

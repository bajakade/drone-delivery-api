/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import com.example.deliverydrone.exceptions.DuplicateRegistrationException;
import com.example.deliverydrone.exceptions.EntityNotFoundException;
import com.example.deliverydrone.exceptions.InvalidDroneStateException;
import com.example.deliverydrone.models.ApiResponse;
import com.example.deliverydrone.models.ApiResponse.Status;
import com.example.deliverydrone.models.Drone;
import com.example.deliverydrone.models.Medication;
import com.example.deliverydrone.services.DroneService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bibrahim
 */

@RestController
@ControllerAdvice
@ComponentScan("com.example.deliverydrone.services")
@RequestMapping("/api/drones")
public class DispatchController {
    
    @Autowired
    public DroneService droneService;
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Drone> findDrone(@Valid @PathVariable Long id){
        
        Drone drone = droneService.findDrone(id);
        if(drone == null){
            throw new EntityNotFoundException("Invalid Drone ID");
        }
        return new ResponseEntity<>(drone, HttpStatus.OK);
    }
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<ApiResponse> registerDrone(@Valid @RequestBody Drone drone){
        ApiResponse serviceResponse = droneService.registerDrone(drone);
        Status status = serviceResponse.getStatus();
        
        switch(status){
            case FORBIDDEN -> throw new InvalidDroneStateException(serviceResponse.getMessage());
            case DUPLICATE_FOUND -> throw new DuplicateRegistrationException(serviceResponse.getMessage());
        }
        return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
    }
    
    @GetMapping("/available")
    @ResponseBody
    public ResponseEntity<Drone> findAvailableDrones(){
        List drones = droneService.getAvailableDrones();
         
        return new ResponseEntity(drones, HttpStatus.OK);
    }
    
    @GetMapping("/{id}/battery")
    public ResponseEntity<ApiResponse> batteryStatus(@Valid @PathVariable Long id){
        ApiResponse serviceResponse = droneService.batteryStatus(id);
        
        if(serviceResponse.getStatus() == ApiResponse.Status.NOT_FOUND){
            throw new EntityNotFoundException(serviceResponse.getMessage());
        }
        return ResponseEntity.ok(serviceResponse); 
    }
    
    
    @PatchMapping("/{id}/load")
    @ResponseBody
    public ResponseEntity<ApiResponse> loadDrone(@Valid @PathVariable Long id, @Valid @RequestBody List<Medication> medications){
        ApiResponse serviceResponse = droneService.loadDrone(id, medications);
        
        Status storeStatus = serviceResponse.getStatus();
        
        switch(storeStatus){
            case FORBIDDEN -> throw new InvalidDroneStateException(serviceResponse.getMessage());
            case NOT_FOUND -> throw new EntityNotFoundException(serviceResponse.getMessage());
        }
        
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }
    
    @GetMapping("/{id}/load")
    public ResponseEntity<ApiResponse> checkMedications(@Valid @PathVariable Long id){
        ApiResponse serviceResponse = droneService.getLoadedMedications(id);
        
        if(serviceResponse.getStatus()== ApiResponse.Status.NOT_FOUND){
            throw new EntityNotFoundException(serviceResponse.getMessage());
        }
        
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.services;

import com.example.deliverydrone.models.ApiResponse;
import com.example.deliverydrone.models.Drone;
import com.example.deliverydrone.models.Medication;
import com.example.deliverydrone.models.enums.State;
import com.example.deliverydrone.repositories.DroneRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author bibrahim
 */
@Service
public class DroneService {
    @Autowired
    public DroneRepository droneRepository;
    @Autowired public MedicationService medicationService;
    
    public Drone findDrone(Long id){
        Drone drone = droneRepository.findById(id).orElse(null);
            
        return drone;
    }
    
    public List<Drone> getAvailableDrones(){
        return droneRepository.findAll()
                .stream()
                .filter(drone -> drone.getDroneStatus() == State.IDLE)
                .collect(Collectors.toList());
    }
    
    public ApiResponse registerDrone(Drone drone){
        Optional<Drone> droneFound = droneRepository.findBySerialNumber(drone.getSerialNumber());
        
        if(!droneFound.isEmpty()){
            return new ApiResponse(ApiResponse.Status.DUPLICATE_FOUND, "Drone Already Registered");
        }
        
        if(drone.getDroneStatus()!=State.IDLE){
            return new ApiResponse(ApiResponse.Status.FORBIDDEN, "New Drone Must Be Idle");
        }
        
        switch(drone.getModel()){ // Capacity is determine by model
                case HEAVYWEIGHT -> drone.setWeight(500);
                case LIGHTWEIGHT -> drone.setWeight(100);
                case MIDDLEWEIGHT -> drone.setWeight(200);
                case CRUISEWEIGHT -> drone.setWeight(300);

            }
        
        droneRepository.save(drone);
        
        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS, "Drone Registered Successfully");
        response.data(drone);
        return response;
    }

    public ApiResponse loadDrone(Long id, List<Medication> medications){
        Drone drone = droneRepository.findById(id).orElse(null);
       
        if(drone == null){
            return new ApiResponse(ApiResponse.Status.NOT_FOUND, "Invalid Drone ID");
        }
        
//        if(drone.getDroneStatus() == State.IDLE){
//            return new ApiResponse(ApiResponse.Status.FORBIDDEN, "Drone Is Not Registered");
//        }
        if(drone.getDroneStatus() != State.IDLE){
            
            return new ApiResponse(ApiResponse.Status.FORBIDDEN, "Drone Not Available");
        }
        if(medications.isEmpty()){
            return new ApiResponse(ApiResponse.Status.NOT_FOUND, "Empty Medication(s)");
        }
        
        if(drone.getBattery() < 25){
            return new ApiResponse(ApiResponse.Status.FORBIDDEN, "Drone Battery Level Below 25%");
        }
        
        List<Medication> load = new ArrayList();
        
        for(Medication medication: medications){
            Medication foundMedication = medicationService.medication(medication.getCode());
            if(foundMedication != null){
                load.add(foundMedication);
            }
        }
        
        int grossWeight = load.stream()
                .map(l -> l.getWeight())
                .reduce(0, Integer::sum);
        
        if(grossWeight > drone.getWeight()){
            return new ApiResponse(ApiResponse.Status.FORBIDDEN, "Maximum Allowed " + drone.getWeight()+ " Weight Exceeded");
        }else{
            drone.setMedications(load);
        }

        drone.setDroneStatus(State.LOADED);
        droneRepository.save(drone);
        
        return new ApiResponse(ApiResponse.Status.SUCCESS, "Drone Loaded Successfully");
    }
    
    public ApiResponse batteryStatus(Long id){
        Drone drone  = droneRepository.findById(id).orElse(null);
        if(drone ==  null){
           return new ApiResponse(ApiResponse.Status.NOT_FOUND, "Invalid Drone ID");
        }
        
        return new ApiResponse(ApiResponse.Status.SUCCESS, "Drone Battery Is " + drone.getBattery() +"%");
    }
    
    public ApiResponse getLoadedMedications(Long id){
        Optional<Drone> drone = droneRepository.findById(id);
        
        if(drone.isEmpty()){
            return new ApiResponse(ApiResponse.Status.NOT_FOUND, "Invalid Drone ID");
        }
        
        List<Medication> medications = drone.get().getMedications();
        
        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS);
        if(medications.isEmpty()){
            switch(drone.get().getDroneStatus()){
                case IDLE -> response.setMessage( "Drone " + drone.get().getId() + " Is `IDLE` And Not Loaded");
                case DELIVERED -> response.setMessage( "Drone " + drone.get().getId() + " Has Just Made A Delivery");
                case RETURNING -> response.setMessage( "Drone " + drone.get().getId() + " Has Just Made A Delivery And Returning");
            }
            
        }else{
            response.setMessage( "Drone " + drone.get().getId() + " Is " + drone.get().getDroneStatus());
        }
        response.data(medications);
        
        return response;
    }
   
}

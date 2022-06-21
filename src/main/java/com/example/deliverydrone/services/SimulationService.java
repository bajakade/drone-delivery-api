/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.services;

import com.example.deliverydrone.models.Drone;
import com.example.deliverydrone.models.enums.State;
import static com.example.deliverydrone.models.enums.State.*;
import com.example.deliverydrone.repositories.DroneRepository;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bibrahim
 */

@Service
public class SimulationService {
    
    @Autowired private DroneRepository dr; 
    private static final Logger LOG = LoggerFactory.getLogger(SimulationService.class.getName());
    
    public void audit(){
            LOG.info("Current time: {}", new Date());
            List<Drone> drones = dr.findAll();
            if(drones.isEmpty()){
                LOG.info("No Registered Drones");
            }else{
                LOG.info("Drones");
                
                 StringBuilder audit = new StringBuilder();
                drones.forEach(d -> {
                    //LOG.info("{}", d.toString());
                    audit.append(d.toString()).append("\n");
                    updateDroneStatus(d);
                });
                LOG.info("{}", audit);
            }
            
    }
    
    public void updateDroneStatus(Drone drone){
        
        State status = drone.getDroneStatus();
        int batteryLevel = drone.getBattery();
        
        if(status != IDLE){
            switch(status){
            case LOADING -> drone.setDroneStatus(LOADED);
            case LOADED -> drone.setDroneStatus(DELIVERING);
            case DELIVERING -> drone.setDroneStatus(DELIVERED);
            case DELIVERED -> {
                    drone.setDroneStatus(IDLE);
                    drone.setMedications(null);
                }
            }
            if(batteryLevel - 5 >= 0)
                drone.setBattery( batteryLevel - 5);
            
            dr.save(drone);
        }else{
            if(batteryLevel - 1 < 0){
            } else {
                drone.setBattery(batteryLevel - 1);
                dr.save(drone);
            }
                
        }
        
        
        
    }
    
}

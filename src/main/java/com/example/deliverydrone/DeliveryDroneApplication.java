package com.example.deliverydrone;

import com.example.deliverydrone.models.Medication;
import com.example.deliverydrone.repositories.DroneRepository;
import com.example.deliverydrone.repositories.MedicationRepository;
import controllers.DispatchController;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import scheduler.MyTaskExecutor;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackageClasses = {DispatchController.class, MyTaskExecutor.class})
public class DeliveryDroneApplication implements CommandLineRunner {
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DeliveryDroneApplication.class.getName());
    
    @Autowired protected MedicationRepository mr;
    @Autowired protected  DroneRepository dr;
    
    public static void main(String[] args) {
            SpringApplication.run(DeliveryDroneApplication.class, args);
    }
        
    @Override
    public void run(String... args) {
        
        String medicineNames [] = {"Amoxicilin","Paracetamol", "Isotretinoin", "Labetamine"};
 
        Random rand = new Random();
        for(int i=0; i<medicineNames.length; i++){
            Medication m = new Medication(medicineNames[i],"ABC"+i, rand.nextInt(10, 100));
            mr.save(m);
            LOG.info(m.toString());
        }
        
//        for(int index=0; index<10; index++){
//            char id = (char) ('A' + index);
//            Drone drone = new Drone();
//            drone.setModel(DroneModel.values()[rand.nextInt(DroneModel.values().length)]);
//            drone.setSerialNumber("DRONE_"+id);
//            drone.setBattery(100);
//            switch(drone.getModel()){ // Capacity is determine by model
//                case HEAVYWEIGHT -> drone.setWeight(500);
//                case LIGHTWEIGHT -> drone.setWeight(100);
//                case MIDDLEWEIGHT -> drone.setWeight(200);
//                case CRUISEWEIGHT -> drone.setWeight(300);
//
//            }
//            
//            drone.setDroneStatus(State.IDLE);
//            dr.save(drone);
//            LOG.info(drone.toString());
//        }
    }

}

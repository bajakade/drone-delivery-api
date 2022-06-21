/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scheduler;


import java.util.logging.Logger;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author bibrahim
 */
public class TaskExecutor {

//    @Autowired
//    Servicename dfsdfs;
    private static final Logger LOG = Logger.getLogger(TaskExecutor.class.getName());
    
    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
//        LOG.log("Fixed Rate Task :: Execution Time");
        System.out.println("Fixed Rate Task :: Execution Time");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scheduler;


import com.example.deliverydrone.services.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author bibrahim
 */
@Component
public class MyTaskExecutor {

    @Autowired private SimulationService ss;
    
//    private static final Logger LOG = Logger.getLogger(MyTaskExecutor.class.getName());
    
//    @Scheduled(fixedDelay = 2000, initialDelay=1000)
//    public void scheduleTaskWithFixedRate() {
//        LOG.info("Fixed rate execution");
//        System.out.println("Fixed Rate Task :: Execution Time");
//    }
    
    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}", initialDelay = 5000)
    public void simulateDroneDispatch(){
        ss.audit();
    }
    
}

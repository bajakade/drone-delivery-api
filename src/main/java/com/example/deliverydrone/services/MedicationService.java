/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.services;

import com.example.deliverydrone.models.Medication;
import com.example.deliverydrone.repositories.MedicationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bibrahim
 */
@Service
public class MedicationService {
    
    @Autowired
    private MedicationRepository medicationRepository;
//    
    public List<Medication> findAll(){
        return medicationRepository.findAll();
    }
    
    public Medication medication(String code){
        return medicationRepository.findByCode(code).orElse(null);
    }
}

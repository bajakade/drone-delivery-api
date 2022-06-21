/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.deliverydrone.repositories;

import com.example.deliverydrone.models.Drone;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bibrahim
 */
public interface DroneRepository extends JpaRepository<Drone, Long> {
        public Optional<Drone> findBySerialNumber(String sn);
}

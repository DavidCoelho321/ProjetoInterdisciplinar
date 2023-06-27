package com.scaapi.model.repository;


import com.scaapi.model.entity.Robo;
import com.scaapi.model.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RoboRepository extends JpaRepository<Robo, Long> {

}

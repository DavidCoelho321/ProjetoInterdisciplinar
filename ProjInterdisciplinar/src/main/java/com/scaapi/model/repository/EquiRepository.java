package com.scaapi.model.repository;

import com.scaapi.model.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquiRepository extends JpaRepository<Equipe, Long> {
}

package com.scaapi.model.repository;

import com.scaapi.model.entity.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotoRepository extends JpaRepository<Piloto, Long> {
}

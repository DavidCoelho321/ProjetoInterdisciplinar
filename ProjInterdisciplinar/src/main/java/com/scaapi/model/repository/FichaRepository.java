package com.scaapi.model.repository;

import com.scaapi.model.entity.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaRepository extends JpaRepository<Ficha, Long> {
}

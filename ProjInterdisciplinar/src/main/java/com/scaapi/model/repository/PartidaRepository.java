package com.scaapi.model.repository;

import com.scaapi.model.entity.Campeonato;
import com.scaapi.model.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByCampeonato(Optional<Campeonato> campeonato);
}

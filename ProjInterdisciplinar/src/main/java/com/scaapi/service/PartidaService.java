package com.scaapi.service;

import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Campeonato;
import com.scaapi.model.entity.Partida;
import com.scaapi.model.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PartidaService {

    private PartidaRepository repository;

    public PartidaService(PartidaRepository repository) {
        this.repository = repository;
    }

    public List<Partida> getPartidas() {
        return repository.findAll();
    }

    public Optional<Partida> getPartidaById(Long id) {
        return repository.findById(id);
    }

    public List<Partida> getPartidasByCampeonato(Optional<Campeonato> campeonato) {
        return repository.findByCampeonato(campeonato);
    }

    @Transactional
    public Partida salvar(Partida partida) {
        validar(partida);
        return repository.save(partida);
    }

    @Transactional
    public void excluir(Partida partida) {
        Objects.requireNonNull(partida.getId());
        repository.delete(partida);
    }

    public void validar(Partida partida) {

        if (partida.getNome() == null || partida.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (partida.getCampeonato() == null || partida.getCampeonato().getId() == null || partida.getCampeonato().getId() == 0) {
            throw new RegraNegocioException("Campeonato inválido");
        }
    }
}

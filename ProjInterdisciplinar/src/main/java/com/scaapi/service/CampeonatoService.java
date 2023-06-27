package com.scaapi.service;


import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Campeonato;
import com.scaapi.model.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CampeonatoService {


    private CampeonatoRepository repository;

    public CampeonatoService(CampeonatoRepository repository) {
        this.repository = repository;
    }

    public List<Campeonato> getCampeonatos() {
        return repository.findAll();
    }

    public Optional<Campeonato> getCampeonatoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Campeonato salvar(Campeonato campeonato) {
        validar(campeonato);
        return repository.save(campeonato);
    }

    @Transactional
    public void excluir(Campeonato campeonato) {
        Objects.requireNonNull(campeonato.getId());
        repository.delete(campeonato);
    }

    public void validar(Campeonato campeonato) {
        if (campeonato.getNome() == null || campeonato.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}

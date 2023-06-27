package com.scaapi.service;


import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Equipe;
import com.scaapi.model.repository.EquipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EquipeService {

    private EquipeRepository repository;

    public EquipeService(EquipeRepository repository) {
        this.repository = repository;
    }

    public List<Equipe> getEquipes() {
        return repository.findAll();
    }

    public Optional<Equipe> getEquipeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Equipe salvar(Equipe equipe) {
        validar(equipe);
        return repository.save(equipe);
    }

    @Transactional
    public void excluir(Equipe equipe) {
        Objects.requireNonNull(equipe.getId());
        repository.delete(equipe);
    }

    public void validar(Equipe equipe) {
        if (equipe.getAno() == null || equipe.getAno() == 0) {
            throw new RegraNegocioException("Ano inválido");
        }
        if (equipe.getSemestre() == null || equipe.getSemestre() == 0) {
            throw new RegraNegocioException("Semestre inválido");
        }
        if (equipe.getNome() == null || equipe.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (equipe.getDisciplina() == null || equipe.getDisciplina().getId() == null || equipe.getDisciplina().getId() == 0) {
            throw new RegraNegocioException("Disciplina inválida");
        }
    }

}

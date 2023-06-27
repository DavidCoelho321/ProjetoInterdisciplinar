package com.scaapi.service;

import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.*;
import com.scaapi.model.repository.RoboRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoboService {

    private RoboRepository repository;

    public RoboService(RoboRepository repository) {
        this.repository = repository;
    }

    public List<Robo> getRobos() {
        return repository.findAll();
    }

    public Optional<Robo> getRoboById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Robo salvar(Robo robo) {
        validar(robo);
        return repository.save(robo);
    }

    @Transactional
    public void excluir(Robo robo) {
        Objects.requireNonNull(robo.getId());
        repository.delete(robo);
    }

    public void validar(Robo robo) {
        if (robo.getMatricula() == null || robo.getMatricula() == 0) {
            throw new RegraNegocioException("Matrícula inválida");
        }
        if (robo.getNome() == null || robo.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (robo.getCurso() == null || robo.getCurso().getId() == null || robo.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}

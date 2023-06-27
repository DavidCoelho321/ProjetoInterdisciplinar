package com.scaapi.service;

import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Piloto;
import com.scaapi.model.entity.Turma;
import com.scaapi.model.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PilotoService {


    private PilotoRepository repository;

    private final TurmaService turmaService;

    public PilotoService(PilotoRepository repository, TurmaService turmaService) {
        this.repository = repository;
        this.turmaService = turmaService;
    }

    public List<Piloto> getPilotos() {
        return repository.findAll();
    }

    public Optional<Piloto> getPilotoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Piloto salvar(Piloto piloto) {
        validar(piloto);
        return repository.save(piloto);
    }

    @Transactional
    public void excluir(Piloto piloto) {
        Objects.requireNonNull(piloto.getId());
        for (Turma turma : piloto.getTurmas()) {
            turma.setPiloto(null);
            turmaService.salvar(turma);
        }
        repository.delete(piloto);
    }

    public void validar(Piloto piloto) {
        if (piloto.getMatricula() == null || piloto.getMatricula() == 0) {
            throw new RegraNegocioException("Matrícula inválida");
        }
        if (piloto.getNome() == null || piloto.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (piloto.getCurso() == null || piloto.getCurso().getId() == null || piloto.getCurso().getId() == 0) {
            throw new RegraNegocioException("Curso inválido");
        }
    }
}

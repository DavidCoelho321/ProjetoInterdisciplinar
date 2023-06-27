package com.scaapi.service;

import com.scaapi.model.entity.Ficha;
import com.scaapi.model.repository.FichaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FichaService {

    private FichaRepository repository;

    public FichaService(FichaRepository repository) {
        this.repository = repository;
    }

    public List<Ficha> getFichas() {
        return repository.findAll();
    }

    public Optional<Ficha> getFichaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Ficha salvar(Ficha ficha) {
        return repository.save(ficha);
    }
}
package com.scaapi.api.controller;


import com.scaapi.api.dto.RoboDTO;
import com.scaapi.api.dto.EquipeDTO;
import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.*;
import com.scaapi.service.PartidaService;
import com.scaapi.service.PilotoService;
import com.scaapi.service.EquipeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/equipes")
@RequiredArgsConstructor
public class EquipeController {


    private final EquipeService service;
    private final PartidaService partidaService;
    private final PilotoService pilotoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Equipe> equipes = service.getEquipes();
        return ResponseEntity.ok(equipes.stream().map(EquipeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(equipe.map(EquipeDTO::create));
    }

    @GetMapping("{id}/robos")
    public ResponseEntity getPartidas(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(equipe.get().getRobos().stream().map(RoboDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(EquipeDTO dto) {
        try {
            Equipe equipe = converter(dto);
            equipe = service.salvar(equipe);
            return new ResponseEntity(equipe, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, EquipeDTO dto) {
        if (!service.getEquipeById(id).isPresent()) {
            return new ResponseEntity("equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Equipe equipe = converter(dto);
            equipe.setId(id);
            service.salvar(equipe);
            return ResponseEntity.ok(equipe);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Equipe> equipe = service.getEquipeById(id);
        if (!equipe.isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(equipe.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Equipe converter(EquipeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Equipe equipe = modelMapper.map(dto, Equipe.class);
        if (dto.getIdPartida() != null) {
            Optional<Partida> partida = partidaService.getPartidaById(dto.getIdPartida());
            if (!partida.isPresent()) {
                equipe.setPartida(null);
            } else {
                equipe.setPartida(partida.get());
            }
        }
        if (dto.getIdPiloto() != null) {
            Optional<Piloto> piloto = pilotoService.getPilotoById(dto.getIdPiloto());
            if (!piloto.isPresent()) {
                equipe.setPiloto(null);
            } else {
                equipe.setPiloto(piloto.get());
            }
        }
        return equipe;
    }
}

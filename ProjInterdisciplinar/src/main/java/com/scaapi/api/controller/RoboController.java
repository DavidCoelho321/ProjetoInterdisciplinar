package com.scaapi.api.controller;

import com.scaapi.api.dto.RoboDTO;

import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Robo;
import com.scaapi.model.entity.Campeonato;
import com.scaapi.model.entity.Ficha;
import com.scaapi.service.RoboService;
import com.scaapi.service.CampeonatoService;
import com.scaapi.service.FichaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/robos")
@RequiredArgsConstructor

public class RoboController {

    private final RoboService service;
    private final CampeonatoService campeonatoService;
    private final FichaService fichaService;

    @GetMapping()
    public ResponseEntity get() {
        List<Robo> robos = service.getRobos();
        return ResponseEntity.ok(robos.stream().map(RoboDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Robo> robo = service.getRoboById(id);
        if (!robo.isPresent()) {
            return new ResponseEntity("robo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(robo.map(RoboDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(RoboDTO dto) {
        try {
            Robo robo = converter(dto);
            Ficha ficha = fichaService.salvar(robo.getFicha());
            robo.setFicha(ficha);
            robo = service.salvar(robo);
            return new ResponseEntity(robo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, RoboDTO dto) {
        if (!service.getRoboById(id).isPresent()) {
            return new ResponseEntity("Robo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Robo robo = converter(dto);
            robo.setId(id);
            Ficha ficha = fichaService.salvar(robo.getFicha());
            robo.setFicha(ficha);
            service.salvar(robo);
            return ResponseEntity.ok(robo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Robo> robo = service.getRoboById(id);
        if (!robo.isPresent()) {
            return new ResponseEntity("Robo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(robo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Robo converter(RoboDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Robo robo = modelMapper.map(dto, Robo.class);
        Ficha ficha = modelMapper.map(dto, Ficha.class);
        robo.setFicha(ficha);
        if (dto.getIdCampeonato() != null) {
            Optional<Campeonato> campeonato = campeonatoService.getCampeonatoById(dto.getIdCampeonato());
            if (!campeonato.isPresent()) {
                robo.setCampeonato(null);
            } else {
                robo.setCampeonato(campeonato.get());
            }
        }
        return robo;
    }
}

package com.scaapi.api.controller;

import com.scaapi.api.dto.PilotoDTO;
import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Robo;
import com.scaapi.model.entity.Campeonato;
import com.scaapi.model.entity.Fixa;
import com.scaapi.model.entity.Piloto;
import com.scaapi.service.CampeonatoService;
import com.scaapi.service.FixaService;
import com.scaapi.service.PilotoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pilotos")
@RequiredArgsConstructor
public class PilotoController {


    private final PilotoService service;
    private final CampeonatoService campeonatoService;
    private final FixaService fixaService;

    @GetMapping()
    public ResponseEntity get() {
        List<Piloto> pilotos = service.getPilotoes();
        return ResponseEntity.ok(pilotos.stream().map(PilotoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Piloto> piloto = service.getPilotoById(id);
        if (!piloto.isPresent()) {
            return new ResponseEntity("piloto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(piloto.map(PilotoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(PilotoDTO dto) {
        try {
            Piloto piloto = converter(dto);
            Fixa fixa = fixaService.salvar(piloto.getFixa());
            piloto.setFixa(fixa);
            piloto = service.salvar(piloto);
            return new ResponseEntity(piloto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, PilotoDTO dto) {
        if (!service.getPilotoById(id).isPresent()) {
            return new ResponseEntity("piloto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Piloto piloto = converter(dto);
            piloto.setId(id);
            Fixa fixa = fixaService.salvar(piloto.getFixa());
            piloto.setFixa(fixa);
            service.salvar(piloto);
            return ResponseEntity.ok(piloto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Piloto> piloto = service.getPilotoById(id);
        if (!piloto.isPresent()) {
            return new ResponseEntity("piloto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(piloto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Piloto converter(PilotoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Piloto piloto = modelMapper.map(dto, Piloto.class);
        Fixa fixa = modelMapper.map(dto, Fixa.class);
        piloto.setFixa(fixa);
        if (dto.getIdCampeonato() != null) {
            Optional<Campeonato> campeonato = CampeonatoService.getCampeonatoById(dto.getIdCampeonato());
            if (!campeonato.isPresent()) {
                piloto.setCampeonato(null);
            } else {
                piloto.setCampeonato(campeonato.get());
            }
        }
        return piloto;
    }
}

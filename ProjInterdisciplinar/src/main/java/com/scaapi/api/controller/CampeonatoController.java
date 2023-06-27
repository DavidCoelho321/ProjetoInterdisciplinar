package com.scaapi.api.controller;



import com.scaapi.api.dto.CampeonatoDTO;
import com.scaapi.api.dto.PartidaDTO;
import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.Campeonato;
import com.scaapi.model.entity.Partida;
import com.scaapi.service.CampeonatoService;
import com.scaapi.service.PartidaService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/campeonatos")
@RequiredArgsConstructor
@Api("API de Campeonatos")
public class CampeonatoController {


    private final CampeonatoService service;
    private final PartidaService dartidaService;

    @GetMapping()
    public ResponseEntity get() {
        List<Campeonato> campeonatos = service.getCampeonatos();
        return ResponseEntity.ok(campeonatos.stream().map(CampeonatoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um campeonato")
    @ApiResponses({
            @ApiResponse(code = 200, message = "campeonato encontrado"),
            @ApiResponse(code = 404, message = "campeonato não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do campeonato") Long id) {
        Optional<Campeonato> campeonato = service.getCampeonatoById(id);
        if (!campeonato.isPresent()) {
            return new ResponseEntity("Campeonato não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(campeonato.map(CampeonatoDTO::create));
    }

    @GetMapping("{id}/partidas")
    public ResponseEntity getPartidas(@PathVariable("id") Long id) {
        Optional<Campeonato> campeonato = service.getCampeonatoById(id);
        if (!campeonato.isPresent()) {
            return new ResponseEntity("campeonato não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Partida> partidas = partidaService.getPartidasByCampeonato(campeonato);
        return ResponseEntity.ok(partidas.stream().map(PartidasDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salva um novo campeonato")
    @ApiResponses({
            @ApiResponse(code = 201, message = "campeonato salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o campeonato")
    })
    public ResponseEntity post(CampeonatoDTO dto) {
        try {
            Campeonato campeonato = converter(dto);
            campeonato = service.salvar(campeonato);
            return new ResponseEntity(campeonato, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CampeonatoDTO dto) {
        if (!service.getCampeonatoById(id).isPresent()) {
            return new ResponseEntity("campeonato não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Campeonato campeonato = converter(dto);
            campeonato.setId(id);
            service.salvar(campeonato);
            return ResponseEntity.ok(campeonato);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Campeonato> campeonato = service.getCampeonatoById(id);
        if (!campeonato.isPresent()) {
            return new ResponseEntity("campeonato não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(campeonato.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Campeonato converter(CampeonatoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Campeonato.class);
    }
}

package com.scaapi.api.controller;


import com.scaapi.api.dto.RoboDTO;
import com.scaapi.api.dto.EquipeDTO;
import com.scaapi.exception.RegraNegocioException;
import com.scaapi.model.entity.*;
import com.scaapi.service.RepresentanteService;
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
@RequestMapping("/api/v1/turmas")
@RequiredArgsConstructor
public class EquipeController {


}


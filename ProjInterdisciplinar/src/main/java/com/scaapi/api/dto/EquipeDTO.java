package com.scaapi.api.dto;

import com.scaapi.model.entity.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EquipeController {

    private Long id;

    private Integer ano;
    private Integer categoria;
    private String nome;

    public static EquipeDTO create(Equipe equipe) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(equipe, EquipeDTO.class);
    }
}


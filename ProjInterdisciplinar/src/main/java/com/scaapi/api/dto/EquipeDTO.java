package com.scaapi.api.dto;

import org.modelmapper.ModelMapper;


import com.scaapi.model.entity.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {


    private Long id;
    private Integer ano;
    private Integer categoria;
    private String nome;
    private Long idPartida;
    private Long idPiloto;

    public static EquipeDTO create(Equipe turma) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(turma, EquipeDTO.class);
    }
}

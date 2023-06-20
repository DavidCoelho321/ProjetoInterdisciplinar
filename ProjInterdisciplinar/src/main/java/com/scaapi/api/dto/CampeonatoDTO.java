package com.scaapi.api.dto;


import com.scaapi.model.entity.Campeonato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CampeonatoDTO {
    private Long id;
    private String nome;

    public static CampeonatoDTO create(Campeonato campeonato) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(campeonato, CampeonatoDTO.class);
    }

}

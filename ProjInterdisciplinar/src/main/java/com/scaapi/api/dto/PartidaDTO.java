package com.scaapi.api.dto;

import com.scaapi.model.entity.Partida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO {

    private String nome;
    private String horario;
    private String resultado;

    public static PartidaDTO create(Partida partida) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(partida, PartidaDTO.class);
    }

}

package com.scaapi.api.dto;

import com.scaapi.model.entity.Robo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoboDTO {

    private String nome;
    private Integer numero;
    private String altura;
    private String peso;
    private String categoria;

    public static RoboDTO create(Robo robo) {
        ModelMapper modelMapper = new ModelMapper();
        RoboDTO dto = modelMapper.map(robo, RoboDTO.class);
        dto.nome = robo.getFicha().getNome();
        dto.numero = robo.getFicha().getNumero();
        dto.altura = robo.getFicha().getAltura();
        dto.peso = robo.getFicha().getPeso();
        dto.categoria = robo.getFicha().getCategoria();
        return dto;
    }
}

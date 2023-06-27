package com.scaapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Piloto extends Especificacao {

    private Integer cadastro;

    @ManyToOne
    private Campeonato campeonato;

    @JsonIgnore
    @ManyToMany(mappedBy = "Robos")
    private List<Equipe> Equipes;
}

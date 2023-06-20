package com.scaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Robo extends Piloto {

    private Integer cadastro;

    @ManyToOne
    private Campeonato campeonato;

    @ManyToMany(mappedBy = "Robos")
    private List<Equipe> Equipes;
}

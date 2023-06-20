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

public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ano;
    private Integer categoria;
    private String nome;

    @ManyToOne
    private Campeonato campeonato;

    @ManyToOne
    private Representante Representante;

    @ManyToMany
    @JoinTable(name = "equipe_robo",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "robo_id"))
    private List<Robo> Robos;
}

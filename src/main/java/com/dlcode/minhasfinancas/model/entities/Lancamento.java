package com.dlcode.minhasfinancas.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "lancamento", schema = "financas")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

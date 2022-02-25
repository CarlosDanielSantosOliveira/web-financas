package com.dlcode.minhasfinancas.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table( name ="usuario" , schema = "financas") //Aqui estamos mapeando onde a tabela sera criada, que nesse caso é nas financas que estao no schema
@NoArgsConstructor //Utilizando essa notation, o programa vai criar um contrutor vazio
@AllArgsConstructor //E com esse, um construtor com todos os argumentos.
public class Usuario {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
}

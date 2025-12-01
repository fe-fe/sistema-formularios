package com.sistemaformulario.entities.acesso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "perfis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    public Perfil(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Perfil [id=" + id + ", nome=" + nome + "]";
    }
}
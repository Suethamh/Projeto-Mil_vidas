package iftm.suetham.mil_vidas.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Livro {
    private int cod_livro;
    private String titulo;
    private String escritor;
    private String editora;
    private String genero;
    private int num_paginas;
    private double avaliacao;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cod_livro;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        if (cod_livro != other.cod_livro)
            return false;
        return true;
    }
    
}

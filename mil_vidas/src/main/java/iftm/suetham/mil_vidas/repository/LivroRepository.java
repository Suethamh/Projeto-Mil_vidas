package iftm.suetham.mil_vidas.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import iftm.suetham.mil_vidas.domain.Livro;

@Component
public class LivroRepository {
    
    private JdbcTemplate conexaoBanco;
    
    public LivroRepository(JdbcTemplate conexaoBanco){
        this.conexaoBanco = conexaoBanco;
    }

    public List<Livro> getLivros(){
        String sql = """
                select cod_livro as cod_livro, liv_titulo as titulo, liv_escritor as escritor, liv_editora as editora, liv_genero as genero, liv_num_paginas as num_paginas, liv_avaliacao as avaliacao
                from tb_livro
                """;
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Livro.class));
    }


    public List<Livro> buscaLivroPorTitulo(String titulo) {
        String sql = """
                select cod_livro as cod_livro, liv_titulo as titulo, liv_escritor as escritor, liv_editora as editora, liv_genero as genero, liv_num_paginas as num_paginas, liv_avaliacao as avaliacao
                from tb_livro
                where lower(liv_titulo) like ?
                """;
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Livro.class), "%"+titulo+"%");
    }

    public Livro buscaLivroPorCodigo(int codigo) {
        String sql = """
                select cod_livro as cod_livro, liv_titulo as titulo, liv_escritor as escritor, liv_editora as editora, liv_genero as genero, liv_num_paginas as num_paginas, liv_avaliacao as avaliacao
                from tb_livro
                where cod_livro = ?
                """;
        return conexaoBanco.queryForObject(sql, new BeanPropertyRowMapper<>(Livro.class), codigo);
    }

    public void novoLivro(Livro livro){
        String sql = """
                insert into tb_livro (liv_titulo, liv_escritor, liv_editora, liv_genero, liv_num_paginas, liv_avaliacao) values (?,?,?,?,?,?)
                """;
        conexaoBanco.update(sql,
                            livro.getTitulo(),
                            livro.getEscritor(),
                            livro.getEditora(),
                            livro.getGenero(),
                            livro.getNum_paginas(),
                            livro.getAvaliacao());
    }

    public boolean delete(int codigo){
        String sql = """
                delete from tb_livro
                where cod_livro = ?
                """;
        return conexaoBanco.update(sql, codigo) > 0;
    }

    public boolean update(Livro livro){
        String sql = """
                update tb_livro
                set liv_titulo = ?, liv_escritor= ?, liv_editora = ?, liv_genero= ?, liv_num_paginas = ?, liv_avaliacao = ?
                where cod_livro = ?
                """;
        return conexaoBanco.update(sql,
                            livro.getTitulo(),
                            livro.getEscritor(),
                            livro.getEditora(),
                            livro.getGenero(),
                            livro.getNum_paginas(),
                            livro.getAvaliacao(),
                            livro.getCod_livro()) > 0;
    }
}
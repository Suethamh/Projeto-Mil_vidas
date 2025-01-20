package iftm.suetham.mil_vidas.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.RowMapper;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import iftm.suetham.mil_vidas.domain.Cliente;
import iftm.suetham.mil_vidas.domain.Livro;
import iftm.suetham.mil_vidas.domain.ListaDesejo;

@Component
public class ListaDesejoRepository {

    private JdbcTemplate conexaoBanco;

    public ListaDesejoRepository(JdbcTemplate conexaoBanco) {
        this.conexaoBanco = conexaoBanco;    
    }

    public List<ListaDesejo> getListaDesejos() {
        String sql = """ 
                select ld.cod_ld, c.cli_nome, l.liv_titulo, ld.dt_inclusao 
                from tb_cliente c, tb_listaDesejo ld, tb_livro l
                where c.cli_login = ld.cli_login and l.cod_livro = ld.cod_livro;
                """;
        return conexaoBanco.query(sql, (rs, rowNum) -> setListaDesejo(rs));
    }

    public List<ListaDesejo> buscaListaDesejosPorCliente(String clienteNome) {
        String sql = """ 
                select ld.cod_ld, c.cli_nome, l.liv_titulo, ld.dt_inclusao 
                from tb_cliente c, tb_listaDesejo ld, tb_livro l
                where c.cli_login = ld.cli_login and l.cod_livro = ld.cod_livro 
                and lower(c.cli_nome) like ?;
                """;
        return conexaoBanco.query(sql, (rs, rowNum) -> setListaDesejo(rs), "%"+clienteNome+"%");
    }

    public ListaDesejo buscaListaDesejoPorCodigo(int cod_wl) {
        String sql = """
                select ld.cod_ld, c.cli_nome, l.liv_titulo, ld.dt_inclusao 
                from tb_cliente c, tb_listaDesejo ld, tb_livro l
                where c.cli_login = ld.cli_login and l.cod_livro = ld.cod_livro 
                and ld.cod_ld = ?;
                """;
        return conexaoBanco.queryForObject(sql, (rs, rowNum) -> setListaDesejo(rs), cod_wl);
    }

    public ListaDesejo setListaDesejo(ResultSet rs) throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setLogin(rs.getString("cli_nome"));
        
        Livro livro = new Livro();
        livro.setTitulo(rs.getString("liv_titulo"));

        ListaDesejo listaDesejo = new ListaDesejo();

        listaDesejo.setCod_wl(rs.getInt("cod_ld"));
        listaDesejo.setCliente(cliente);
        listaDesejo.setLivro(livro);
        listaDesejo.setData_inclusao(rs.getString("dt_inclusao"));
        return listaDesejo;
    }

    public void novaListaDesejo(ListaDesejo listaDesejo) {
        String sql = """
                insert into tb_listaDesejo (cli_login, cod_livro, dt_inclusao) values (?,?,?)
                """;
        conexaoBanco.update(sql,
                            listaDesejo.getCliente(),
                            listaDesejo.getLivro(),
                            listaDesejo.getData_inclusao());
    }

    public boolean delete(int cod_wl) {
        String sql = """
                delete from tb_listaDesejo
                where cod_ld = ?
                """;
        return conexaoBanco.update(sql, cod_wl) > 0;
    }

    public boolean update(ListaDesejo listaDesejo) {
        String sql = """
                update tb_listaDesejo
                set cli_login = (select cli_login from tb_cliente where lower(cli_nome) like ?), 
                cod_livro = (select cod_livro from tb_livro where lower(liv_titulo) like ?), 
                dt_inclusao = ?
                where cod_ld = ?
                """;
        return conexaoBanco.update(sql,
                                   "%"+listaDesejo.getCliente().getNome().toLowerCase()+"%",
                                   "%"+listaDesejo.getLivro().getTitulo().toLowerCase()+"%",
                                   listaDesejo.getData_inclusao(),
                                   listaDesejo.getCod_wl()) > 0;
    }
}

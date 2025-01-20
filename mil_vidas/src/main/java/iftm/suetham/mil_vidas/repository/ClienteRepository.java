package iftm.suetham.mil_vidas.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import iftm.suetham.mil_vidas.domain.Cliente;

@Component
public class ClienteRepository {
    
    private JdbcTemplate conexaoBanco;
    
    public ClienteRepository(JdbcTemplate conexaoBanco){
            this.conexaoBanco = conexaoBanco;
    }

    public List<Cliente> getClientes(){
        String sql = """
                select cli_login as login, cli_senha as senha, cli_nome as nome, cli_nick as nickname
                from tb_cliente
                """;
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    }


    public List<Cliente> buscaClientePorNome(String nome) {
        String sql = """
                select cli_login as login, cli_senha as senha, cli_nome as nome, cli_nick as nickname
                from tb_cliente
                where lower(cli_nome) like ?
                """;
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Cliente.class), "%"+nome+"%");
    }

    public Cliente buscaClientePorLogin(String login) {
        String sql = """
                select cli_login as login, cli_senha as senha, cli_nome as nome, cli_nick as nickname
                from tb_cliente
                where cli_login = ?
                """;
        return conexaoBanco.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), login);
    }

    public void novoCliente(Cliente cliente){
        String sql = """
                insert into tb_cliente(cli_login, cli_senha, cli_nome, cli_nick) values (?,?,?,?)
                """;
        conexaoBanco.update(sql, 
                            cliente.getLogin(),
                            cliente.getSenha(),
                            cliente.getNome(),
                            cliente.getNickname());
    }

    public boolean delete(String login){
        String sql = """
                delete from tb_cliente 
                where cli_login = ?
                """;
        return conexaoBanco.update(sql, login) > 0;
    }

    public boolean update(Cliente cliente){
        String sql = """
                update tb_cliente
                set cli_senha = ?, cli_nome = ?, cli_nick = ?
                where cli_login = ?
                """;
        return conexaoBanco.update(sql,
                            cliente.getSenha(),
                            cliente.getNome(),
                            cliente.getNickname(),
                            cliente.getLogin()) > 0;
    }
}
